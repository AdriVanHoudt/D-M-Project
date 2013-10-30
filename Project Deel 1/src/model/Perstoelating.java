package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

/**
 * User: Adri
 * Date: 25/10/13
 * Time: 12:02
 */

@Entity
@Table(name = "t_perstoelating")
public class Perstoelating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private ToelatingSoort soort;

    @ManyToOne
    @JoinColumn(name = "artiestId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Artiest artiest;

    @ManyToOne
    @JoinColumn(name = "persorgaanId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private PersOrgaan persOrgaan;

    public Perstoelating() {
    }

    public Integer getId() {
        return id;
    }

    public ToelatingSoort getSoort() {
        return soort;
    }

    public void setSoort(ToelatingSoort soort) {
        this.soort = soort;
    }

    public Artiest getArtiest() {
        return artiest;
    }

    public void setArtiest(Artiest artiest) {
        this.artiest = artiest;
    }

    public PersOrgaan getPersOrgaan() {
        return persOrgaan;
    }

    public void setPersOrgaan(PersOrgaan persOrgaan) {
        this.persOrgaan = persOrgaan;
    }

    public enum ToelatingSoort {
        FOTO, FILM, FOTOFILM
    }

}


