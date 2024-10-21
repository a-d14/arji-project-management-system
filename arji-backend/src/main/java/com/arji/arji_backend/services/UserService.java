package com.arji.arji_backend.services;

import com.arji.arji_backend.payload.user.UserDTO;
import com.arji.arji_backend.payload.user.UserDetailsView;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDetailsView getUser(Long userId);
    void createUser(UserDTO userDTO);
}
