package Main;

import model.Nummer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.HibernateUtil;

import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        // gewoon omdat hibernate dan zijn ding doet
        Nummer nr = new Nummer();

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.saveOrUpdate(nr);
    }


    //start console app
    private void runConsole() {
        //init menu


        //registratie verkoop
        //back to menu
        //opslaan passage
        //back to menu
        //zoeken optreden
        //back to menu
        //zoeken festival
        //back to menu
    }

}

