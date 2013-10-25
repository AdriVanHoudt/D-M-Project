package model;

import javax.persistence.*;
import java.util.Date;

/**
 * User: Adri
 * Date: 21/10/13
 * Time: 19:10
 */
@Entity
@Table(name = "t_festivalday")
public class FestivalDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date date;

    public FestivalDay() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
