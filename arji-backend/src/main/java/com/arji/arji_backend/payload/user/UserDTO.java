package com.arji.arji_backend.payload.user;

import com.arji.arji_backend.models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long userId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 4, max = 10)
    private String username;

    @NotBlank
    @Size(min = 3)
    private String password;

    private List<Role> authorities;

}
