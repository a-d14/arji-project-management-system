package com.arji.arji_backend.models;

import com.arji.arji_backend.util.TicketLabel;
import com.arji.arji_backend.util.TicketPriority;
import com.arji.arji_backend.util.TicketProgress;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long ticketId;

    @Column(name = "ticket_title")
    private String title;

    @Column(name = "ticket_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_ticket_id")
    private Ticket parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Ticket> children;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_label")
    private TicketLabel ticketLabel;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_priority")
    private TicketPriority ticketPriority;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_progress")
    private TicketProgress ticketProgress;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "ticket_deadline")
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "ticket_project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "ticket_reporter_id")
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    public void addChild(Ticket ticket) {
        this.getChildren().add(ticket);
        ticket.setParent(this);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Ticket ticket = (Ticket) o;
        return getTicketId() != null && Objects.equals(getTicketId(), ticket.getTicketId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }

}
