package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

/**
 * User: adri
 * Date: 27/10/13
 * Time: 16:34
 */

@Entity
@Table(name = "t_faciliteit")
public class Faciliteit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String type;

    @ManyToOne
    @JoinColumn(name = "zoneId", nullable = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Zone zone;

    public Faciliteit() {
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}
