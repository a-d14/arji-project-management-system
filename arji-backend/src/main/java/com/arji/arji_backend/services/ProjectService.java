package com.arji.arji_backend.services;

import com.arji.arji_backend.payload.project.ProjectDTO;
import com.arji.arji_backend.payload.project.ProjectDetailsView;
import com.arji.arji_backend.payload.project.ProjectListDTO;

public interface ProjectService {
    ProjectListDTO getAllProjects(int pageNumber, int pageSize);
    ProjectDetailsView getProject(Long projectId);
    ProjectDetailsView createProject(ProjectDTO projectDTO);
    ProjectDetailsView editProject(ProjectDTO projectDTO, Long projectId);
    void deleteProject(Long projectId);
}
