package Main;

import model.Festival;
import model.FestivalGanger;
import model.Nummer;
import model.TicketVerkoop;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.HibernateUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    Scanner scanner = new Scanner(System.in);
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();


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
                //make sure scanner is empty
                if (scanner.hasNext()) scanner.next();

                //TODO registratie verkoop
                //1..n; via web

                //maak festivalganger
                System.out.println("Geef je naam:");
                FestivalGanger buyer = new FestivalGanger();
                buyer.setNaam(scanner.next());
                session.saveOrUpdate(buyer);

                //get festivals
                Query getFestivals = session.createQuery("from Festival");
                List festivals = getFestivals.list();

                //kies festival
                System.out.println("Kies je festival:");
                for (int i = 0; i < festivals.size(); i++) {
                    System.out.print((i + 1) + ": ");
                    System.out.println(((Festival) festivals.get(i)).getName());
                }

                int festivalChoice = scanner.nextInt();
                if (scanner.hasNext()) scanner.next();

                //maak ticketverkoop
                TicketVerkoop sale = new TicketVerkoop();
                sale.setFestivalGanger(buyer);
                sale.setTimestamp(new Date());
                sale.setType(TicketVerkoop.VerkoopsType.WEB);
                sale.setFestival((Festival) festivals.get(festivalChoice - 1));

                session.saveOrUpdate(sale);

                //get tickettypes
                System.out.println("Welk tickettype wil je?");
                Query getticketTypes = session.createQuery("from TicketType ");
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

