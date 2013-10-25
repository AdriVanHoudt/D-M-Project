package model;

import javax.persistence.*;

/**
 * User: Adri
 * Date: 25/10/13
 * Time: 11:29
 */

@Entity
@Table(name = "t_artiest")
public class Artiest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String naam;
    private String bio;

    public Integer getId() {
        return id;
    }

    public Artiest() {
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
