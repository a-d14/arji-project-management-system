package com.arji.arji_backend.models;

import com.arji.arji_backend.util.TicketLabel;
import com.arji.arji_backend.util.TicketPriority;
import com.arji.arji_backend.util.TicketProgress;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @Column(name = "ticket_title")
    private String title;

    @Column(name = "ticket_description")
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "parent_ticket_id")
    private Ticket parent;

    @OneToMany(mappedBy = "parent")
    private Set<Ticket> children;

    @Column(name = "ticket_label")
    private TicketLabel ticketLabel;

    @Column(name = "ticket_priority")
    private TicketPriority ticketPriority;

    @Column(name = "ticket_progress")
    private TicketProgress ticketProgress;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User assignee;

    @ManyToMany
    @JoinTable(
            name = "users_tickets",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> assignedUsers;

}
