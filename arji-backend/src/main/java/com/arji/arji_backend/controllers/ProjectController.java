package com.arji.arji_backend.controllers;

import com.arji.arji_backend.payload.project.ProjectDTO;
import com.arji.arji_backend.payload.ProjectResponse;
import com.arji.arji_backend.services.ProjectService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @PostMapping("/auth/project")
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectDTO projectDTO) throws Exception {
        ProjectResponse projectResponse = projectService.createProject(projectDTO);
        return new ResponseEntity<>(projectResponse, HttpStatus.OK);
    }

}
