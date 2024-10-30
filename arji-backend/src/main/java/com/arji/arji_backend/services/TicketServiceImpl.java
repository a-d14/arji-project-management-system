package com.arji.arji_backend.services;

import com.arji.arji_backend.exceptions.APIException;
import com.arji.arji_backend.exceptions.ResourceNotFoundException;
import com.arji.arji_backend.models.Project;
import com.arji.arji_backend.models.Ticket;
import com.arji.arji_backend.models.User;
import com.arji.arji_backend.payload.ticket.TicketDTO;
import com.arji.arji_backend.payload.ticket.TicketDetailsView;
import com.arji.arji_backend.payload.ticket.TicketListDTO;
import com.arji.arji_backend.repositories.ProjectRepository;
import com.arji.arji_backend.repositories.TicketRepository;
import com.arji.arji_backend.repositories.UserRepository;
import com.arji.arji_backend.util.ProjectDetails;
import com.arji.arji_backend.util.TicketDetails;
import com.arji.arji_backend.util.UserDetails;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        modelMapper.typeMap(Ticket.class, TicketDetailsView.class).addMappings(mapper -> {
            mapper.skip(TicketDetailsView::setParent);
            mapper.skip(TicketDetailsView::setChildren);
        });
    }

    @Transactional
    @Override
    public void createTicket(TicketDTO ticketDTO) {

        Ticket ticket = modelMapper.map(ticketDTO, Ticket.class);
        System.out.println(ticket);

        User reporter = userRepository.findById(ticketDTO.getReporterId()).orElseThrow(() -> new ResourceNotFoundException("User", "userId", ticketDTO.getReporterId()));
        User assignedUser = userRepository.findById(ticketDTO.getAssignedUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "userId", ticketDTO.getReporterId()));
        ticket.setReporter(reporter);
        ticket.setCreatedAt(LocalDateTime.now());

        if(LocalDateTime.parse(ticketDTO.getDeadline()).isBefore(LocalDateTime.now()))
            throw new APIException("Deadline cannot be before the current date");

        ticket.setDeadline(LocalDateTime.parse(ticketDTO.getDeadline()));
        assignedUser.addTicket(ticket);

        if(ticketDTO.getProjectId() == null) throw new APIException("Project ID must be included");

        Project project = projectRepository.findById(ticketDTO.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", ticketDTO.getProjectId()));

        Ticket parent = ticketDTO.getParentTicketId() == null ? null : ticketRepository.findById(ticketDTO.getParentTicketId()).orElseThrow(
                () -> new ResourceNotFoundException("Parent", "ticketId", ticketDTO.getParentTicketId())
        );

        if(parent != null && !parent.getProject().equals(project)) {
            throw new APIException("projectId should be same as the projectId of parent ticket");
        }

        project.addTicket(ticket);

        if(parent != null)
            parent.addChild(ticket);

    }

    @Override
    public List<TicketDetailsView> getAllTickets() {
        List<Ticket> allTickets = ticketRepository.findAll();
        return null;
    }

    @Override
    public TicketDetailsView getTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", "ticketId", ticketId));
        TicketDetailsView ticketDetailsView = modelMapper.map(ticket, TicketDetailsView.class);
        ticketDetailsView.setParent(ticket.getParent() != null ? new TicketDetails(ticket.getParent().getTicketId(), ticket.getParent().getTitle()) : null);
        ticketDetailsView.setReporter(new UserDetails(ticket.getReporter().getId(), ticket.getReporter().getFirstName() + " " + ticket.getReporter().getLastName()));
        ticketDetailsView.setAssignedUser(new UserDetails(ticket.getAssignedUser().getId(), ticket.getAssignedUser().getFirstName() + " " + ticket.getAssignedUser().getLastName()));
        ticketDetailsView.setProject(new ProjectDetails(ticket.getProject().getProjectId(), ticket.getProject().getTitle()));
        ticketDetailsView.setChildren(
                ticket.getChildren().stream().map(t -> new TicketDetails(t.getTicketId(), t.getTitle())).toList()
        );

        return ticketDetailsView;
    }

    @Override
    public TicketListDTO findTicketsByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", projectId));
        return null;
    }

    @Transactional
    @Override
    public TicketDetailsView editTicket(Long ticketId, TicketDTO ticketDTO) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", "ticketId", ticketId));

        ticket.setTitle(ticketDTO.getTitle());
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setTicketLabel(ticketDTO.getTicketLabel());
        ticket.setTicketPriority(ticketDTO.getTicketPriority());
        ticket.setTicketProgress(ticketDTO.getTicketProgress());

        if(!ticket.getDeadline().equals(LocalDateTime.parse(ticketDTO.getDeadline()))
                && LocalDateTime.parse(ticketDTO.getDeadline()).isBefore(LocalDateTime.now()))
            throw new APIException("Deadline cannot be before the current date");

        ticket.setDeadline(LocalDateTime.parse(ticketDTO.getDeadline()));

        if(ticketDTO.getProjectId() == null)
            throw new APIException("Project ID must be provided");

        Project newProject = projectRepository.findById(ticketDTO.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project", "projectId", ticketDTO.getProjectId()));

        Ticket newParentTicket = ticketDTO.getParentTicketId() == null ? null :
                ticketRepository.findById(ticketDTO.getParentTicketId())
                        .orElseThrow(() -> new ResourceNotFoundException("Parent Ticket", "ticketId", ticketDTO.getParentTicketId()));

        if(newParentTicket != null && !newProject.getTickets().contains(newParentTicket))
            throw new APIException("Parent Ticket must belong to the supplied project");

        ticket.getProject().removeTicket(ticket);
        ticket.setProject(newProject);

        if(ticket.getParent() != null) {
            ticket.getParent().removeChild(ticket);
        }

        ticket.setParent(newParentTicket);

        User newAssignedUser = userRepository.findById(ticketDTO.getAssignedUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Assigned user", "userId", ticketDTO.getAssignedUserId()));

        ticket.getAssignedUser().removeTicket(ticket);
        ticket.setAssignedUser(newAssignedUser);

        ticket.getChildren().forEach((child) -> {
            child.getProject().removeTicket(child);
            child.setProject(newProject);
        });

        TicketDetailsView ticketDetailsView = modelMapper.map(ticket, TicketDetailsView.class);
        ticketDetailsView.setParent(ticket.getParent() != null ? new TicketDetails(ticket.getParent().getTicketId(), ticket.getParent().getTitle()) : null);
        ticketDetailsView.setReporter(new UserDetails(ticket.getReporter().getId(), ticket.getReporter().getFirstName() + " " + ticket.getReporter().getLastName()));
        ticketDetailsView.setAssignedUser(new UserDetails(ticket.getAssignedUser().getId(), ticket.getAssignedUser().getFirstName() + " " + ticket.getAssignedUser().getLastName()));
        ticketDetailsView.setProject(new ProjectDetails(ticket.getProject().getProjectId(), ticket.getProject().getTitle()));
        ticketDetailsView.setChildren(
                ticket.getChildren().stream().map(t -> new TicketDetails(t.getTicketId(), t.getTitle())).toList()
        );

        return ticketDetailsView;
    }
}
