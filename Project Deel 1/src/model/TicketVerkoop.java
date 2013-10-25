package model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 24/10/13
 * Time: 21:00
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table (name = "t_ticketverkoop")
public class TicketVerkoop {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column (name = "type")
    private String type;
    @Column (name = "timestamp")
    private Date timestamp;
    @ManyToOne
    @JoinColumn(name = "festivalgangerId", nullable=false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private FestivalGanger festivalGanger;

    public TicketVerkoop() {
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public FestivalGanger getFestivalGanger() {
        return festivalGanger;
    }

    public void setFestivalGanger(FestivalGanger festivalGanger) {
        this.festivalGanger = festivalGanger;
    }
}
