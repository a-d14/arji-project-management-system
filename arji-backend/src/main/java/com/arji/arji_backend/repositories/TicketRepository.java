package com.arji.arji_backend.repositories;

import com.arji.arji_backend.models.Project;
import com.arji.arji_backend.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
