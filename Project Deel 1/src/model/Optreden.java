package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

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

    private Integer duration;
    private Integer soundcheck;

    @ManyToOne
    @JoinColumn(name = "zoneId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Zone zone;

    @ManyToOne
    @JoinColumn(name = "festivaldagId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private FestivalDag festivalDag;

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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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
