package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

/**
 * User: Adri
 * Date: 21/10/13
 * Time: 19:10
 */
@Entity
@Table(name = "t_festivalday")
public class FestivalDag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date date;

    private Integer aantalBeschikbareTickets;

    @ManyToOne
    @JoinColumn(name = "festivalId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Festival festival;


    public FestivalDag() {
    }

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Festival getFestival() {
        return festival;
    }

    public Integer getAantalBeschikbareTickets() {
        return aantalBeschikbareTickets;
    }

    public void setAantalBeschikbareTickets(Integer aantalBeschikbareTickets) {
        this.aantalBeschikbareTickets = aantalBeschikbareTickets;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }
}
