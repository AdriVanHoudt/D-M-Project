package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "t_ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "persoonorgaanId", nullable = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private PersOrgaan persOrgaan;

    @ManyToOne
    @JoinColumn(name = "ticketverkoopId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private TicketVerkoop ticketVerkoop;

    @ManyToOne
    @JoinColumn(name = "ticketTypeId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private TicketType ticketType;

    public Ticket() {

    }

    public Integer getId() {
        return id;
    }

    public PersOrgaan getPersOrgaan() {
        return persOrgaan;
    }

    public void setPersOrgaan(PersOrgaan persOrgaan) {
        this.persOrgaan = persOrgaan;
    }

    public TicketVerkoop getTicketVerkoop() {
        return ticketVerkoop;
    }

    public void setTicketVerkoop(TicketVerkoop ticketVerkoop) {
        this.ticketVerkoop = ticketVerkoop;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }
}
