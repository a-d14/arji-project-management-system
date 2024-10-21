package com.arji.arji_backend.payload.project;

import com.arji.arji_backend.util.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDetailsView {
    private Long projectId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDetails manager;
    private List<UserDetails> personnelReadOnly;
    private List<UserDetails> personnelEditAccess;
}
