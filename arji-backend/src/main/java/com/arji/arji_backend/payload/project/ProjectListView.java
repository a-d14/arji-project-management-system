package com.arji.arji_backend.payload.project;

import com.arji.arji_backend.util.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectListView {
    private Long projectId;
    private String projectTitle;
    private UserInfo managerDetails;
    private LocalDateTime createdOn;
    private Integer openIssues;
    private Integer closedIssues;
    private Integer assignedToCurrentUser;
}