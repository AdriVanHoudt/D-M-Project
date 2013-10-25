package model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 24/10/13
 * Time: 21:42
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_ticketdag")
public class TicketDag {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "festivaldagId", nullable=false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private FestivalDay festivalDay;
    @ManyToOne
    @JoinColumn(name = "ticketTypeId", nullable=false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private TicketType ticketType;

    public TicketDag() {
    }

    public Integer getId() {
        return id;
    }

    public FestivalDay getFestivalDay() {
        return festivalDay;
    }

    public void setFestivalDay(FestivalDay festivalDay) {
        this.festivalDay = festivalDay;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }
}
