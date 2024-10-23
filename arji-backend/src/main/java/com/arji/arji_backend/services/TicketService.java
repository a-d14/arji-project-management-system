package com.arji.arji_backend.services;

import com.arji.arji_backend.payload.ticket.TicketDTO;
import com.arji.arji_backend.payload.ticket.TicketDetailsView;

import java.util.List;

public interface TicketService {
    void createTicket(TicketDTO ticketDTO);
    List<TicketDetailsView> getAllTickets();
}
