package model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 24/10/13
 * Time: 21:25
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_persorgaan")
public class PersOrgaan {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
