package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_tracking")
public class Tracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "zoneId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Zone zone;

    public Tracking() {
    }

    public Integer getId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}
