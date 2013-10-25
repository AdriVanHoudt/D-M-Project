package model;

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





    private enum Toelating {
        FOTO, FILM, FOTOFILM
    }

}


