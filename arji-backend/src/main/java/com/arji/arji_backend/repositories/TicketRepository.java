package com.arji.arji_backend.repositories;

import com.arji.arji_backend.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
