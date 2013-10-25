package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 24/10/13
 * Time: 21:58
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_zone")
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column (name = "naam")
    private String naam;
    @Column (name = "capaciteit")
    private Integer capaciteit;
    @ManyToOne
    @JoinColumn(name = "festivalId", nullable=false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Festival festival;
    @ManyToOne
    @JoinColumn(name = "ticketZoneId", nullable=false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private TicketZone ticketZone;
    @ManyToOne
    @JoinColumn(name = "zoneId", nullable=false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Zone zone;

    public Zone() {
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

    public Integer getCapaciteit() {
        return capaciteit;
    }

    public void setCapaciteit(Integer capaciteit) {
        this.capaciteit = capaciteit;
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public TicketZone getTicketZone() {
        return ticketZone;
    }

    public void setTicketZone(TicketZone ticketZone) {
        this.ticketZone = ticketZone;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}
