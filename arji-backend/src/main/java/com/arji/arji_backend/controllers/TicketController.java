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

    @Autowired
    private TicketService ticketService;

    @PostMapping("/auth/ticket")
    public ResponseEntity<String> createTicket(@RequestBody TicketDTO ticketDTO) {
        ticketService.createTicket(ticketDTO);
        return new ResponseEntity<>("Ticket Created", HttpStatus.OK);
    }

    @GetMapping("/auth/ticket")
    public ResponseEntity<List<TicketDetailsView>> getAllTickets() {
        return new ResponseEntity<>(ticketService.getAllTickets(), HttpStatus.OK);
    }

    @PostMapping("/auth/{projectId}/ticket")
    public ResponseEntity<Ticket> createTicketForProject(@PathVariable("projectId") Long projectId) {
        return null;
    }

    @GetMapping("/auth/{projectId}/ticket")
    public ResponseEntity<List<Ticket>> getAllTicketsForProject(@PathVariable("projectId") Long projectId) {
        return null;
    }

}
