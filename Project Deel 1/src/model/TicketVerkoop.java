package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_ticketverkoop")
public class TicketVerkoop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type")
    private VerkoopsType type;

    @Column(name = "timestamp")
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "festivalId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Festival festival;

    @ManyToOne
    @JoinColumn(name = "festivalgangerId", nullable = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private FestivalGanger festivalGanger;

    public TicketVerkoop() {
    }

    public Integer getId() {
        return id;
    }

    public VerkoopsType getType() {
        return type;
    }

    public void setType(VerkoopsType type) {
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

    public enum VerkoopsType {
        WEB, VERKOOPSCENTRA, FESTIVAL
    }
}
