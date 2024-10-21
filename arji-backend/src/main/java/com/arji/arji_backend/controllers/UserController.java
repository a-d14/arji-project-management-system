package com.arji.arji_backend.controllers;

import com.arji.arji_backend.models.User;
import com.arji.arji_backend.payload.user.UserDTO;
import com.arji.arji_backend.payload.user.UserDetailsView;
import com.arji.arji_backend.services.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    UserService userService;
    ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDetailsView> getUser(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    // TODO: Need to add endpoint to get current user after logging in
    @GetMapping("/user/current")
    public ResponseEntity<User> getCurrentUser() {
        return null;
    }

    // TODO: Make sure email and username of user are unique
    @PostMapping("/user")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return new ResponseEntity<>("User created successfully", HttpStatus.OK);
    }

}
