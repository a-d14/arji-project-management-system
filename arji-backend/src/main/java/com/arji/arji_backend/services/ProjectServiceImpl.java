package com.arji.arji_backend.services;

import com.arji.arji_backend.models.Project;
import com.arji.arji_backend.models.User;
import com.arji.arji_backend.payload.project.ProjectDTO;
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

    @Override
    public ProjectResponse getProject(Long projectId) {

        // TODO: Check if project with given id exists

        return modelMapper.map(projectRepository.findById(projectId).get(), ProjectResponse.class);
    }

    @Transactional
    @Override
    public ProjectResponse createProject(ProjectDTO projectDTO) throws Exception {
        Project project = modelMapper.map(projectDTO, Project.class);

        // TODO: Handle case where user is not present or does not have the manager role

        User manager = userRepository.findById(projectDTO.getManagerId()).orElseThrow(() -> new Exception("User Not Found"));
        project.setManager(manager);

        // TODO: Make sure user exists and the manager is not in this list. Make sure same id not in both lists.
        List<User> personnelEditAccess = projectDTO.getPersonnelEditAccess().stream().map(id -> userRepository.findById(id).get()).toList();
        List<User> personnelReadOnly = projectDTO.getPersonnelReadOnly().stream().map(id -> userRepository.findById(id).get()).toList();

        project.setPersonnelEditAccess(personnelEditAccess);
        project.setPersonnelReadOnly(personnelReadOnly);

        project.setCreatedAt(LocalDateTime.now());

        Project savedProject = projectRepository.save(project);

        personnelEditAccess.forEach(user -> {
            user.getProjectsEditAccess().add(project);
            userRepository.save(user);
        });

        personnelReadOnly.forEach(user -> {
            user.getProjectsReadOnly().add(project);
            userRepository.save(user);
        });

        return modelMapper.map(savedProject, ProjectResponse.class);
    }

    @Override
    public ProjectResponse editProject(ProjectDTO projectDTO, Long projectId) {
        // TODO: Make sure project exists.
        Project project = projectRepository.findById(projectId).get();
        project.setTitle(projectDTO.getTitle());
        project.setDescription(projectDTO.getDescription());
        project.setManager(userRepository.findById(projectDTO.getManagerId()).get());
        project.setUpdatedAt(LocalDateTime.now());
        projectRepository.save(project);
        return modelMapper.map(project, ProjectResponse.class);
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}
