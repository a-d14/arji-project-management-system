package com.arji.arji_backend.services;

import com.arji.arji_backend.models.User;
import com.arji.arji_backend.payload.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    void createUser(UserDTO userDTO);
}
