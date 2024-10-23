package com.arji.arji_backend.services;

import com.arji.arji_backend.exceptions.APIException;
import com.arji.arji_backend.exceptions.ResourceNotFoundException;
import com.arji.arji_backend.models.Project;
import com.arji.arji_backend.models.Ticket;
import com.arji.arji_backend.models.User;
import com.arji.arji_backend.payload.ticket.TicketDTO;
import com.arji.arji_backend.payload.ticket.TicketDetailsView;
import com.arji.arji_backend.repositories.ProjectRepository;
import com.arji.arji_backend.repositories.TicketRepository;
import com.arji.arji_backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private TicketRepository ticketRepository;

    private ModelMapper modelMapper;

    @Autowired
    public TicketServiceImpl(UserRepository userRepository, ProjectRepository projectRepository, TicketRepository ticketRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public void createTicket(TicketDTO ticketDTO) {

        Ticket ticket = modelMapper.map(ticketDTO, Ticket.class);
        System.out.println(ticket);

        User reporter = userRepository.findById(ticketDTO.getReporterId()).orElseThrow(() -> new ResourceNotFoundException("User", "userId", ticketDTO.getReporterId()));
        User assignedUser = userRepository.findById(ticketDTO.getAssignedUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "userId", ticketDTO.getReporterId()));
        ticket.setReporter(reporter);
        assignedUser.addTicket(ticket);

        if(ticketDTO.getProjectId() == null) throw new APIException("Project ID must be included");

        Project project = projectRepository.findById(ticketDTO.getProjectId()).orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", ticketDTO.getProjectId()));
        project.addTicket(ticket);

        List<Ticket> allTickets = ticketRepository.findAll();
        System.out.println("PRINTING ALL TICKETS");
        allTickets.forEach((t) -> System.out.println(t.getTitle()));

        Ticket parent = ticketDTO.getParentTicketId() == null ? null : ticketRepository.findById(ticketDTO.getParentTicketId()).orElseThrow(
                            () -> new ResourceNotFoundException("Ticket", "ticketId", ticketDTO.getParentTicketId())
                        );

        if(parent != null && !parent.getProject().equals(project)) {
            throw new APIException("projectId should be same as the projectId of parent ticket");
        }

        System.out.println("TICKET ID: "  + ticket.getTicketId());

        if(parent != null)
            parent.addChild(ticket);

    }

    @Override
    public List<TicketDetailsView> getAllTickets() {
        List<Ticket> allTickets = ticketRepository.findAll();
        return null;
    }
}
