package com.arji.arji_backend.controllers;

import com.arji.arji_backend.models.Ticket;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketController {

    @PostMapping("/auth/{projectId}/ticket")
    public ResponseEntity<Ticket> createTicket(@PathVariable("projectId") Long projectId) {
        return null;
    }

    @GetMapping("/auth/{projectId}/ticket")
    public ResponseEntity<List<Ticket>> getAllTicketsForProject(@PathVariable("projectId") Long projectId) {
        return null;
    }

    @GetMapping("/admin/ticket")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return null;
    }

}
