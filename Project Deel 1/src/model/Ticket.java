package model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 24/10/13
 * Time: 21:22
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table (name = "t_ticket")
public class Ticket {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "persoonorgaanId", nullable=false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private PersOrgaan persOrgaan;
    @ManyToOne
    @JoinColumn(name = "ticketverkoopId", nullable=false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private TicketVerkoop ticketVerkoop;
    @ManyToOne
    @JoinColumn(name = "ticketTypeId", nullable=false)
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
