package com.arji.arji_backend.services;

import com.arji.arji_backend.payload.ProjectDTO;
import com.arji.arji_backend.payload.ProjectResponse;

public interface ProjectService {
    ProjectResponse createProject(ProjectDTO projectDTO) throws Exception;
}
