package model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

/**
 * User: Adri
 * Date: 25/10/13
 * Time: 11:25
 */

@Entity
@Table(name = "t_optredenApparatuur")
public class OptredenApparatuur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String naam;
    private Integer aantal;

    @ManyToOne
    @JoinColumn(name = "optredenId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Optreden optreden;

    @ManyToOne
    @JoinColumn(name = "apparatuurId", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Apparatuur apparatuur;

    public Integer getId() {
        return id;
    }

    public OptredenApparatuur() {
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Integer getAantal() {
        return aantal;
    }

    public void setAantal(Integer aantal) {
        this.aantal = aantal;
    }

    public Optreden getOptreden() {
        return optreden;
    }

    public void setOptreden(Optreden optreden) {
        this.optreden = optreden;
    }

    public Apparatuur getApparatuur() {
        return apparatuur;
    }

    public void setApparatuur(Apparatuur apparatuur) {
        this.apparatuur = apparatuur;
    }
}
