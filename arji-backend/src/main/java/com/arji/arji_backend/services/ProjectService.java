package com.arji.arji_backend.services;

import com.arji.arji_backend.payload.project.ProjectDTO;
import com.arji.arji_backend.payload.ProjectResponse;

import java.util.List;

public interface ProjectService {
    List<ProjectResponse> getAllProjects();
    ProjectResponse getProject(Long projectId);
    ProjectResponse createProject(ProjectDTO projectDTO) throws Exception;
    ProjectResponse editProject(ProjectDTO projectDTO, Long projectId);

    void deleteProject(Long projectId);
}
