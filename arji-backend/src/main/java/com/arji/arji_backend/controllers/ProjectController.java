package com.arji.arji_backend.controllers;
import com.arji.arji_backend.payload.project.ProjectDTO;
import com.arji.arji_backend.payload.ProjectResponse;
import com.arji.arji_backend.payload.project.ProjectListDTO;
import com.arji.arji_backend.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectController {

    ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/auth/project")
    public ResponseEntity<ProjectListDTO> getAllProjects(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "2") int pageSize
    ) {
        return new ResponseEntity<>(projectService.getAllProjects(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("/auth/project/{projectId}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Long projectId) {
        return new ResponseEntity<>(projectService.getProject(projectId), HttpStatus.OK);
    }

    @PostMapping("/auth/project")
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectDTO projectDTO) {
        ProjectResponse projectResponse = projectService.createProject(projectDTO);
        return new ResponseEntity<>(projectResponse, HttpStatus.CREATED);
    }

    @PutMapping("/auth/project/{projectId}")
    public ResponseEntity<ProjectResponse> editProject(@RequestBody ProjectDTO projectDTO, @PathVariable Long projectId) {
        ProjectResponse projectResponse = projectService.editProject(projectDTO, projectId);
        return new ResponseEntity<>(projectResponse, HttpStatus.OK);
    }

    @DeleteMapping("/auth/project/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>("Project Deleted Successfully", HttpStatus.NO_CONTENT);
    }

}
