package com.arji.arji_backend.payload;

import com.arji.arji_backend.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private User manager;
    private List<User> personnelReadOnly;
    private List<User> personnelEditAccess;

}
