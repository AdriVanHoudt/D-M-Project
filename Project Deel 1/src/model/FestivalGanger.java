package model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 24/10/13
 * Time: 20:05
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table (name = "t_festivalganger")
public class FestivalGanger {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "naam")
    private String naam;

    public FestivalGanger() {
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
