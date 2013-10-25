package model;

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
