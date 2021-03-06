package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "t_ticketdag")
public class TicketDag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "festivaldagId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private FestivalDag festivalDag;

    @ManyToOne
    @JoinColumn(name = "ticketTypeId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private TicketType ticketType;

    public TicketDag() {
    }

    public Integer getId() {
        return id;
    }

    public FestivalDag getFestivalDag() {
        return festivalDag;
    }

    public void setFestivalDag(FestivalDag festivalDag) {
        this.festivalDag = festivalDag;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }
}
