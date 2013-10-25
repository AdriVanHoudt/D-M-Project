package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

/**
 * User: Adri
 * Date: 25/10/13
 * Time: 11:08
 */
@Entity
@Table(name = "t_nummer")
public class Nummer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String artiest;
    private String titel;
    private Integer duur;

    @ManyToOne
    @JoinColumn(name = "optredenId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Optreden optreden;

    public Nummer() {
    }

    public Integer getId() {
        return id;
    }

    public String getArtiest() {
        return artiest;
    }

    public void setArtiest(String artiest) {
        this.artiest = artiest;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Integer getDuur() {
        return duur;
    }

    public void setDuur(Integer duur) {
        this.duur = duur;
    }

    public Optreden getOptreden() {
        return optreden;
    }

    public void setOptreden(Optreden optreden) {
        this.optreden = optreden;
    }
}
