package com.arji.arji_backend.services;

import com.arji.arji_backend.models.Project;
import com.arji.arji_backend.models.User;
import com.arji.arji_backend.payload.ProjectDTO;
import com.arji.arji_backend.payload.ProjectResponse;
import com.arji.arji_backend.repositories.ProjectRepository;
import com.arji.arji_backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{

    ModelMapper modelMapper;

    UserRepository userRepository;
    ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ModelMapper modelMapper, UserRepository userRepository, ProjectRepository projectRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<ProjectResponse> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream().map(project -> modelMapper.map(project, ProjectResponse.class)).toList();
    }

    @Transactional
    @Override
    public ProjectResponse createProject(ProjectDTO projectDTO) throws Exception {
        Project project = modelMapper.map(projectDTO, Project.class);

        // TODO: Handle case where user is not present

        User manager = userRepository.findById(projectDTO.getManagerId()).orElseThrow(() -> new Exception("User Not Found"));
        project.setManager(manager);

        List<User> personnelEditAccess = projectDTO.getPersonnelEditAccess().stream().map(id -> userRepository.findById(id).get()).toList();
        List<User> personnelReadOnly = projectDTO.getPersonnelReadOnly().stream().map(id -> userRepository.findById(id).get()).toList();

        project.setPersonnelEditAccess(personnelEditAccess);
        project.setPersonnelReadOnly(personnelReadOnly);

        project.setCreatedAt(LocalDateTime.now());

//        System.out.println("Project ID before save: " + project.getProjectId());
        Project savedProject = projectRepository.save(project);

        personnelEditAccess.forEach(user -> {
            user.getProjectsEditAccess().add(project);
            userRepository.save(user);
        });

        personnelReadOnly.forEach(user -> {
            user.getProjectsReadOnly().add(project);
            userRepository.save(user);
        });

        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setId(project.getProjectId());
        projectResponse.setTitle(project.getTitle());
        projectResponse.setDescription(project.getDescription());
        projectResponse.setCreatedAt(project.getCreatedAt());
        projectResponse.setManager(project.getManager());
        projectResponse.setPersonnelReadOnly(project.getPersonnelReadOnly());
        projectResponse.setPersonnelEditAccess(project.getPersonnelEditAccess());

        return projectResponse;
    }

    @Override
    public ProjectResponse editProject(ProjectDTO projectDTO) {

    }
}
