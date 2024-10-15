package com.arji.arji_backend.payload;

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

    @NotBlank
    private String title;

    @NotBlank
    @Size(max = 500)
    private String description;

    @NotNull
    private Long managerId;

    // TODO: Make sure managerId is not included in either list
    // TODO: Same id should not be present in both lists
    private List<Long> personnelReadOnly;
    private List<Long> personnelEditAccess;

}
