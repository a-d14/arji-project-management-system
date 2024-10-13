package com.arji.arji_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Column(name = "project_name")
    private String title;

    @Column(name = "project_description")
    private String description;

    @Column(name = "project_tickets")
    private List<Ticket> tickets;

    @Column(name = "project_manager")
    private User manager;

    @Column(name = "project_personnel")
    private List<User> personnel;

}
