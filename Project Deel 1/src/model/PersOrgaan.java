package model;

import javax.persistence.*;


@Entity
@Table(name = "t_persorgaan")
public class PersOrgaan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "naam")
    private String naam;

    public PersOrgaan() {
    }

    public Integer getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }
}
