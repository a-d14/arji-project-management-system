package com.arji.arji_backend.services;

import com.arji.arji_backend.models.User;
import com.arji.arji_backend.payload.UserDTO;
import com.arji.arji_backend.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map((user) -> modelMapper.map(user, UserDTO.class)).toList();
    }

    @Override
    public void createUser(UserDTO userDTO) {

        // TODO: Check if user with given id already exists

        User user = modelMapper.map(userDTO, User.class);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
