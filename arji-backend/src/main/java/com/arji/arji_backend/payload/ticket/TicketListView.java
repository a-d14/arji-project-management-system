package com.arji.arji_backend.payload.ticket;

import com.arji.arji_backend.util.ProjectDetails;
import com.arji.arji_backend.util.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketListView {
    private Long ticketId;
    private String title;
    private ProjectDetails projectDetails;
    private LocalDateTime deadline;
    private UserDetails assignedUser;

}
