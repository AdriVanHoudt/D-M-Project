package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

/**
 * User: Adri
 * Date: 25/10/13
 * Time: 11:08
 */

@Entity
@Table(name = "t_kleedkamerregistratie")
public class KleedkamerRegistratie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String flyer;

    private Date beginDatum;

    private Date eindDatum;

    @ManyToOne
    @JoinColumn(name = "artiestId", nullable = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Artiest artiest;

    @ManyToOne
    @JoinColumn(name = "faciliteitId", nullable = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Faciliteit faciliteit;

    public KleedkamerRegistratie() {
    }

    public Integer getId() {
        return id;
    }

    public String getFlyer() {
        return flyer;
    }

    public void setFlyer(String flyer) {
        this.flyer = flyer;
    }

    public Date getBeginDatum() {
        return beginDatum;
    }

    public void setBeginDatum(Date beginDatum) {
        this.beginDatum = beginDatum;
    }

    public Date getEindDatum() {
        return eindDatum;
    }

    public void setEindDatum(Date eindDatum) {
        this.eindDatum = eindDatum;
    }

    public Artiest getArtiest() {
        return artiest;
    }

    public void setArtiest(Artiest artiest) {
        this.artiest = artiest;
    }

    public Faciliteit getFaciliteit() {
        return faciliteit;
    }

    public void setFaciliteit(Faciliteit faciliteit) {
        this.faciliteit = faciliteit;
    }
}
