package com.arji.arji_backend.services;

import com.arji.arji_backend.exceptions.APIException;
import com.arji.arji_backend.exceptions.ResourceNotFoundException;
import com.arji.arji_backend.models.Project;
import com.arji.arji_backend.models.User;
import com.arji.arji_backend.payload.project.ProjectDTO;
import com.arji.arji_backend.payload.project.ProjectDetailsView;
import com.arji.arji_backend.payload.project.ProjectListDTO;
import com.arji.arji_backend.payload.project.ProjectListView;
import com.arji.arji_backend.repositories.ProjectRepository;
import com.arji.arji_backend.repositories.UserRepository;
import com.arji.arji_backend.util.UserDetails;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        modelMapper.typeMap(Project.class, ProjectDetailsView.class).addMappings(mapper -> {
            mapper.skip(ProjectDetailsView::setPersonnelReadOnly);
            mapper.skip(ProjectDetailsView::setPersonnelEditAccess);
        });
    }

    @Override
    public ProjectListDTO getAllProjects(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Project> projectPage = projectRepository.findAll(pageable);

        List<Project> allProjects = projectPage.getContent();

        List<ProjectListView> projects = allProjects.stream().map((project) -> {
            ProjectListView projectListView = modelMapper.map(project, ProjectListView.class);
            projectListView.setManagerDetails(new UserDetails(project.getManager().getId(), project.getManager().getFirstName() + " " + project.getManager().getLastName()));
            return projectListView;
        }).toList();

        ProjectListDTO projectListDTO = new ProjectListDTO();
        projectListDTO.setProjects(projects);
        projectListDTO.setPageNumber(projectPage.getNumber());
        projectListDTO.setPageSize(projectPage.getSize());
        projectListDTO.setTotalElements(projectPage.getTotalElements());
        projectListDTO.setTotalPages(projectPage.getTotalPages());
        projectListDTO.setLast(projectPage.isLast());

        return projectListDTO;
    }

    @Override
    public ProjectDetailsView getProject(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));
        ProjectDetailsView projectDetailsView = modelMapper.map(project, ProjectDetailsView.class);
        projectDetailsView.setManager(
                new UserDetails(project.getManager().getId(), project.getManager().getFirstName() + " " + project.getManager().getLastName())
        );
        projectDetailsView.setPersonnelReadOnly(
                project.getPersonnelReadOnly().stream().map(user -> new UserDetails(user.getId(), user.getFirstName() + " " + user.getLastName())).toList()
        );
        projectDetailsView.setPersonnelEditAccess(
                project.getPersonnelEditAccess().stream().map(user -> new UserDetails(user.getId(), user.getFirstName() + " " + user.getLastName())).toList()
        );
        return projectDetailsView;
    }

    @Transactional
    @Override
    public ProjectDetailsView createProject(ProjectDTO projectDTO) {
        Project project = modelMapper.map(projectDTO, Project.class);

        // TODO: Handle case where user does not have the manager role

        User manager = userRepository.findById(projectDTO.getManagerId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", projectDTO.getManagerId()));
        project.setManager(manager);

        List<User> personnelEditAccess = projectDTO.getPersonnelEditAccess().stream().map(userId -> {

            if(projectDTO.getPersonnelReadOnly().contains(userId)) {
                throw new APIException(String.format("Cannot put same userId:%s in personnelEditAccess and personnelReadOnly", userId));
            }

            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

            if(user.getId().equals(manager.getId())) {
                throw new APIException("Manager cannot be in the personnelEditAccess list");
            }

            return user;
        }).toList();

        List<User> personnelReadOnly = projectDTO.getPersonnelReadOnly().stream().map(userId -> {
            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

            if(user.getId().equals(manager.getId())) {
                throw new APIException("Manager cannot be in the personnelReadOnly list");
            }

            return user;
        }).toList();

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

        ProjectDetailsView projectDetailsView = modelMapper.map(savedProject, ProjectDetailsView.class);
        projectDetailsView.setManager(
                new UserDetails(project.getManager().getId(), project.getManager().getFirstName() + " " + project.getManager().getLastName())
        );
        projectDetailsView.setPersonnelReadOnly(
                project.getPersonnelReadOnly().stream().map(user -> new UserDetails(user.getId(), user.getFirstName() + " " + user.getLastName())).toList()
        );
        projectDetailsView.setPersonnelEditAccess(
                project.getPersonnelEditAccess().stream().map(user -> new UserDetails(user.getId(), user.getFirstName() + " " + user.getLastName())).toList()
        );
        return projectDetailsView;
    }

    @Transactional
    @Override
    public ProjectDetailsView editProject(ProjectDTO projectDTO, Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));

        project.setTitle(projectDTO.getTitle());
        project.setDescription(projectDTO.getDescription());

        Long managerId = projectDTO.getManagerId();

        if(managerId == null) {
            project.setManager(null);
        } else {
            project.setManager(userRepository.findById(projectDTO.getManagerId()).orElse(null));
        }

        project.setUpdatedAt(LocalDateTime.now());

        List<User> personnelEditAccess = project.getPersonnelEditAccess();
        project.setPersonnelEditAccess(projectDTO.getPersonnelEditAccess().stream().map(
                userId -> {
                    if(projectDTO.getPersonnelReadOnly().contains(userId)) {
                        throw new APIException(String.format("Cannot put same userId:%s in personnelEditAccess and personnelReadOnly", userId));
                    }
                    return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
                }
        ).toList());

        List<User> personnelReadOnly = project.getPersonnelReadOnly();
        project.setPersonnelReadOnly(projectDTO.getPersonnelReadOnly().stream().map(
                userId -> userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId))
        ).toList());

        projectRepository.save(project);
        return modelMapper.map(project, ProjectDetailsView.class);
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}
