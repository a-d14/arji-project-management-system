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
import com.arji.arji_backend.util.TicketDetails;
import com.arji.arji_backend.util.UserInfo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            mapper.skip(ProjectDetailsView::setTickets);
        });
    }

    @Override
    public ProjectListDTO getAllProjects(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Project> projectPage = projectRepository.findAll(pageable);

        List<Project> allProjects = projectPage.getContent();

        List<ProjectListView> projects = allProjects.stream().map((project) -> {
            ProjectListView projectListView = modelMapper.map(project, ProjectListView.class);
            projectListView.setManagerDetails(new UserInfo(project.getManager().getId(), project.getManager().getFirstName() + " " + project.getManager().getLastName()));
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
                project.getManager() == null ? null : new UserInfo(project.getManager().getId(), project.getManager().getFirstName() + " " + project.getManager().getLastName())
        );
        projectDetailsView.setPersonnelReadOnly(
                project.getPersonnelReadOnly().stream().map(user -> new UserInfo(user.getId(), user.getFirstName() + " " + user.getLastName())).toList()
        );
        projectDetailsView.setPersonnelEditAccess(
                project.getPersonnelEditAccess().stream().map(user -> new UserInfo(user.getId(), user.getFirstName() + " " + user.getLastName())).toList()
        );
        projectDetailsView.setTickets(
                project.getTickets().stream().map(ticket -> new TicketDetails(ticket.getTicketId(), ticket.getTitle())).toList()
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

        Set<User> personnelEditAccess = projectDTO.getPersonnelEditAccess().stream().map(userId -> {

            if(projectDTO.getPersonnelReadOnly().contains(userId)) {
                throw new APIException(String.format("Cannot put same userId:%s in personnelEditAccess and personnelReadOnly", userId));
            }

            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

            if(user.getId().equals(manager.getId())) {
                throw new APIException("Manager cannot be in the personnelEditAccess list");
            }

            return user;
        }).collect(Collectors.toSet());

        Set<User> personnelReadOnly = projectDTO.getPersonnelReadOnly().stream().map(userId -> {
            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

            if(user.getId().equals(manager.getId())) {
                throw new APIException("Manager cannot be in the personnelReadOnly list");
            }

            return user;
        }).collect(Collectors.toSet());

        project.setPersonnelEditAccess(personnelEditAccess);
        project.setPersonnelReadOnly(personnelReadOnly);

        project.setCreatedAt(LocalDateTime.now());

        projectRepository.save(project);

        ProjectDetailsView projectDetailsView = modelMapper.map(project, ProjectDetailsView.class);
        projectDetailsView.setManager(
                new UserInfo(project.getManager().getId(), project.getManager().getFirstName() + " " + project.getManager().getLastName())
        );
        projectDetailsView.setPersonnelReadOnly(
                project.getPersonnelReadOnly().stream().map(user -> new UserInfo(user.getId(), user.getFirstName() + " " + user.getLastName())).toList()
        );
        projectDetailsView.setPersonnelEditAccess(
                project.getPersonnelEditAccess().stream().map(user -> new UserInfo(user.getId(), user.getFirstName() + " " + user.getLastName())).toList()
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

        Set<User> personnelEditAccess = project.getPersonnelEditAccess();
        project.setPersonnelEditAccess(projectDTO.getPersonnelEditAccess().stream().map(
                userId -> {
                    if(projectDTO.getPersonnelReadOnly().contains(userId)) {
                        throw new APIException(String.format("Cannot put same userId:%s in personnelEditAccess and personnelReadOnly", userId));
                    }
                    return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
                }
        ).collect(Collectors.toSet()));

        Set<User> personnelReadOnly = project.getPersonnelReadOnly();
        project.setPersonnelReadOnly(projectDTO.getPersonnelReadOnly().stream().map(
                userId -> userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId))
        ).collect(Collectors.toSet()));

        Set<User> personnelEditAccessNew = project.getPersonnelEditAccess();
        Set<User> personnelReadOnlyNew = project.getPersonnelReadOnly();

        personnelEditAccess.removeAll(personnelEditAccessNew);
        personnelReadOnly.removeAll(personnelReadOnlyNew);

        ProjectDetailsView projectDetailsView = modelMapper.map(project, ProjectDetailsView.class);
        projectDetailsView.setPersonnelEditAccess(personnelEditAccessNew.stream()
                .map(user -> new UserInfo(user.getId(), user.getFirstName() + " " + user.getLastName())).toList());
        projectDetailsView.setPersonnelReadOnly(personnelReadOnlyNew.stream()
                .map(user -> new UserInfo(user.getId(), user.getFirstName() + " " + user.getLastName())).toList());

        return projectDetailsView;
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}
