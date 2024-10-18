package com.arji.arji_backend.payload.project;

import com.arji.arji_backend.models.Ticket;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private Long projectId;
    private String title;
    private String description;
    private Long managerId;
    private List<Long> personnelReadOnly;
    private List<Long> personnelEditAccess;
    private List<Ticket> tickets;
}
