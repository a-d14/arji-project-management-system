package com.arji.arji_backend.controllers;

import com.arji.arji_backend.payload.ProjectDTO;
import com.arji.arji_backend.payload.ProjectResponse;
import com.arji.arji_backend.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProjectController {

    ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/auth/project")
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectDTO projectDTO) throws Exception {
        ProjectResponse projectResponse = projectService.createProject(projectDTO);
        return new ResponseEntity<>(projectResponse, HttpStatus.OK);
    }

}
