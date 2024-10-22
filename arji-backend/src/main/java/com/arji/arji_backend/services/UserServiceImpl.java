package com.arji.arji_backend.services;

import com.arji.arji_backend.exceptions.ResourceNotFoundException;
import com.arji.arji_backend.models.Project;
import com.arji.arji_backend.models.User;
import com.arji.arji_backend.payload.project.ProjectDetailsView;
import com.arji.arji_backend.payload.user.UserDTO;
import com.arji.arji_backend.payload.user.UserDetailsView;
import com.arji.arji_backend.repositories.UserRepository;
import com.arji.arji_backend.util.ProjectDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;

        modelMapper.typeMap(User.class, UserDetailsView.class).addMappings(mapper -> {
            mapper.skip(UserDetailsView::setProjectsEditAccess);
            mapper.skip(UserDetailsView::setProjectsReadOnly);
        });
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map((user) -> modelMapper.map(user, UserDTO.class)).toList();
    }

    @Override
    public UserDetailsView getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        UserDetailsView userDetailsView = modelMapper.map(user, UserDetailsView.class);
        userDetailsView.setProjectsEditAccess(
                user.getProjectsEditAccess().stream().map(project -> new ProjectDetails(project.getProjectId(), project.getTitle())).toList()
        );
        userDetailsView.setProjectsReadOnly(
                user.getProjectsReadOnly().stream().map(project -> new ProjectDetails(project.getProjectId(), project.getTitle())).toList()
        );
        return userDetailsView;
    }

    @Override
    public void createUser(UserDTO userDTO) {

        // TODO: Check if user with given username already exists

        User user = modelMapper.map(userDTO, User.class);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
