package com.arji.arji_backend.services;

import com.arji.arji_backend.payload.ticket.TicketDTO;

public interface TicketService {
    void createTicket(TicketDTO ticketDTO);
}
