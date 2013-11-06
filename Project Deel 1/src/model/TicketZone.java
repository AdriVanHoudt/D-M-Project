package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "t_ticketzone")
public class TicketZone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ticketTypeId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private TicketType ticketType;

    @ManyToOne
    @JoinColumn(name = "zoneId", nullable = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Zone zone;

    public TicketZone() {
    }

    public Integer getId() {
        return id;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}
