package com.arji.arji_backend.payload.ticket;

import com.arji.arji_backend.util.TicketLabel;
import com.arji.arji_backend.util.TicketPriority;
import com.arji.arji_backend.util.TicketProgress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private Long ticketId;
    private String title;
    private String description;
    private Long parentTicketId;
    private TicketLabel ticketLabel;
    private TicketPriority ticketPriority;
    private TicketProgress ticketProgress;
    private String deadline;
    private Long reporterId;
    private Long assignedUserId;
    private Long projectId;
}
