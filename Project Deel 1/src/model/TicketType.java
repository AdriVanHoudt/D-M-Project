package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "t_tickettype")
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "naam")
    private String naam;

    @Column(name = "prijs")
    private Integer prijs;

    @ManyToOne
    @JoinColumn(name = "ticketDagId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private TicketDag ticketDag;

    @ManyToOne
    @JoinColumn(name = "ticketzoneId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private TicketZone ticketZone;

    public TicketType() {
    }

    public Integer getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Integer getPrijs() {
        return prijs;
    }

    public void setPrijs(Integer prijs) {
        this.prijs = prijs;
    }

    public TicketDag getTicketDag() {
        return ticketDag;
    }

    public void setTicketDag(TicketDag ticketDag) {
        this.ticketDag = ticketDag;
    }

    public TicketZone getTicketZone() {
        return ticketZone;
    }

    public void setTicketZone(TicketZone ticketZone) {
        this.ticketZone = ticketZone;
    }
}
