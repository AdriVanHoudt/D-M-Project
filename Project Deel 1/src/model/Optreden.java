package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * User: Adri
 * Date: 21/10/13
 * Time: 20:13
 */
@Entity
@Table(name = "t_optreden")
public class Optreden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer soundcheck;

    private Date startTime;
    private Date endTime;

    @ManyToOne
    @JoinColumn(name = "zoneId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Zone zone;

    @ManyToOne
    @JoinColumn(name = "festivaldagId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private FestivalDag festivalDag;

    @ManyToOne
    @JoinColumn(name = "artiestId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Artiest artiest;

    public Artiest getArtiest() {
        return artiest;
    }

    public void setArtiest(Artiest artiest) {
        this.artiest = artiest;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public FestivalDag getFestivalDag() {
        return festivalDag;
    }

    public void setFestivalDag(FestivalDag festivalDag) {
        this.festivalDag = festivalDag;
    }

    public Optreden() {
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSoundcheck() {
        return soundcheck;
    }

    public void setSoundcheck(Integer soundcheck) {
        this.soundcheck = soundcheck;
    }
}
