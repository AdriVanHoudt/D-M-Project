package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;


@Entity
@Table(name = "t_zone")
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "naam")
    private String naam;

    @Column(name = "capaciteit")
    private Integer capaciteit;

    @ManyToOne
    @JoinColumn(name = "festivalId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Festival festival;

    @ManyToOne
    @JoinColumn(name = "zoneId", nullable = false)
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

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}
