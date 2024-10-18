package com.arji.arji_backend.payload.project;

import java.util.List;

public class ProjectListDTO {
    private List<ProjectListView> projects;
    private int size;
    private int totalElements;
    private int totalPages;
    private int pageNumber;
    private boolean isLast;
}
