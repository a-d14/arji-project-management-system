package com.arji.arji_backend.payload.project;

import com.arji.arji_backend.models.User;

import java.time.LocalDateTime;

public class ProjectListView {
    private Long projectId;
    private String projectTitle;
    private User manager;
    private LocalDateTime createdOn;
    private Integer openIssues;
    private Integer closedIssues;
    private Integer assignedToCurrentUser;
}
