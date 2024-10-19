package com.arji.arji_backend.services;

import com.arji.arji_backend.payload.project.ProjectDTO;
import com.arji.arji_backend.payload.ProjectResponse;
import com.arji.arji_backend.payload.project.ProjectListDTO;

public interface ProjectService {
    ProjectListDTO getAllProjects(int pageNumber, int pageSize);
    ProjectResponse getProject(Long projectId);
    ProjectResponse createProject(ProjectDTO projectDTO);
    ProjectResponse editProject(ProjectDTO projectDTO, Long projectId);
    void deleteProject(Long projectId);
}
