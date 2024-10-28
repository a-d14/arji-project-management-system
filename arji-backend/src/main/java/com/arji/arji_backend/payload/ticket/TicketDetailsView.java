package com.arji.arji_backend.payload.ticket;

import com.arji.arji_backend.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDetailsView {
    private Long ticketId;
    private String title;
    private String description;
    TicketDetails parent;
    private List<TicketDetails> children;
    private TicketLabel ticketLabel;
    private TicketPriority ticketPriority;
    private TicketProgress ticketProgress;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
    private ProjectDetails project;
    private UserDetails reporter;
    private UserDetails assignedUser;
}
