package com.arji.arji_backend.services;

import com.arji.arji_backend.payload.ticket.TicketDTO;
import com.arji.arji_backend.payload.ticket.TicketDetailsView;
import com.arji.arji_backend.payload.ticket.TicketListDTO;
import com.arji.arji_backend.payload.ticket.TicketListView;
import com.arji.arji_backend.util.TicketDetails;

import java.util.List;

public interface TicketService {
    void createTicket(TicketDTO ticketDTO);
    List<TicketDetailsView> getAllTickets();
    TicketDetailsView getTicket(Long ticketId);
    TicketListDTO findTicketsByProject(Long projectId);
    TicketDetailsView editTicket(Long ticketId, TicketDTO ticketDTO);
}
