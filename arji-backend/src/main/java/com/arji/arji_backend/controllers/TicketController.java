package com.arji.arji_backend.controllers;

import com.arji.arji_backend.models.Ticket;
import com.arji.arji_backend.payload.ticket.TicketDTO;
import com.arji.arji_backend.payload.ticket.TicketDetailsView;
import com.arji.arji_backend.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/auth/ticket")
    public ResponseEntity<String> createTicket(@RequestBody TicketDTO ticketDTO) {
        ticketService.createTicket(ticketDTO);
        return new ResponseEntity<>("Ticket Created", HttpStatus.OK);
    }

    @GetMapping("/auth/ticket")
    public ResponseEntity<List<TicketDetailsView>> getAllTickets() {
        return new ResponseEntity<>(ticketService.getAllTickets(), HttpStatus.OK);
    }

    @GetMapping("/auth/ticket/{ticketId}")
    public ResponseEntity<TicketDetailsView> getTicket(@PathVariable Long ticketId) {
        return new ResponseEntity<>(ticketService.getTicket(ticketId), HttpStatus.OK);
    }

    @PostMapping("/auth/{projectId}/ticket")
    public ResponseEntity<String> addNewTicketToProject(@PathVariable("projectId") Long projectId, @RequestBody TicketDTO ticketDTO) {
        ticketDTO.setProjectId(projectId);
        ticketService.createTicket(ticketDTO);
        return new ResponseEntity<>("Ticket Created", HttpStatus.OK);
    }

    @GetMapping("/auth/{projectId}/ticket")
    public ResponseEntity<List<Ticket>> getAllTicketsForProject(@PathVariable("projectId") Long projectId) {
        ticketService.findTicketsByProject(projectId);
        return null;
    }

}
