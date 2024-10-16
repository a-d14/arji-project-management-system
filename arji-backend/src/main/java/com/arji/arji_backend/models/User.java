package com.arji.arji_backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    // TODO: Add User Role

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // TODO: Change later to Set

    @ManyToMany(mappedBy = "personnelReadOnly")
    @JsonIgnore
    private List<Project> projectsReadOnly;

    @ManyToMany(mappedBy = "personnelEditAccess")
    @JsonIgnore
    private List<Project> projectsEditAccess;

    @ManyToMany(mappedBy = "assignedUsers")
    private List<Ticket> tickets;

}
