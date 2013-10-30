package Main;

import model.Nummer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.HibernateUtil;

import java.text.ParseException;
import java.util.Scanner;

public class Main {

    Scanner scanner = new Scanner(System.in);


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
        //print menu and returns the chosen option
        switch (getOptionMainMenu()) {
            case 1:
                //TODO registratie verkoop
                //1..n via web

                //maak festivalganger
                System.out.println("Geef je naam:");
                scanner.next();
                //maak ticketverkoop
                //set type op web
                //kies tickettype
                //maak tickets


                //back to menu
                break;
            case 2:
                //TODO opslaan passage
                //back to menu
                break;
            case 3:
                //TODO zoeken optreden
                //back to menu
                break;
            case 4:
                //TODO zoeken festival
                //back to menu
                break;
        }

    }

    private int getOptionMainMenu() {
        System.out.println("Welcome");
        System.out.println("Kies welke opdracht je wilt uitvoeren:");

        //options
        System.out.println("1: Registratie van een verkoop");
        System.out.println("2: Opslaan passage");
        System.out.println("3: Zoeken otreden");
        System.out.println("4: Zoeken festival");

        return scanner.nextInt();
    }

}

