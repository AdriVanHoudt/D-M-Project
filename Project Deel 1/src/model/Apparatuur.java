package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

/**
 * User: Adri
 * Date: 25/10/13
 * Time: 11:23
 */

@Entity
@Table(name = "t_apparatuur")
public class Apparatuur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String naam;
    private Integer aantal;

    @ManyToOne
    @JoinColumn(name = "zoneId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Zone zone;

    public Apparatuur() {
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

    public Integer getAantal() {
        return aantal;
    }

    public void setAantal(Integer aantal) {
        this.aantal = aantal;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}
