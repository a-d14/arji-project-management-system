package com.arji.arji_backend.payload.user;

import com.arji.arji_backend.util.ProjectDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsView {
    private Long userId;
    private String fullName;
    private String email;
    private String username;
    private List<ProjectDetails> projectsReadOnly;
    private List<ProjectDetails> projectsEditAccess;
}
