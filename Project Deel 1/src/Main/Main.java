package Main;

import model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.HibernateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    Scanner scanner = new Scanner(System.in);
    static Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    static Transaction tx = session.beginTransaction();


    public static void main(String[] args) {
        Main me = new Main();
        me.runConsole();
        tx.commit();
    }


    //start console app
    private void runConsole() {
        //init menu
        int menu = getOptionMainMenu();
        while (menu != 5) {
            //print menu and returns the chosen option
            switch (menu) {
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
                    Query getticketTypes = session.createQuery("from TicketType where naam like : festivalname");
                    String festivalname = "%" + ((Festival) festivals.get(festivalChoice - 1)).getName() + "%";
                    getticketTypes.setString("festivalname", festivalname);

                    List ticketTypes = getticketTypes.list();

                    //kies tickettype
                    System.out.println("Kies je tickettype:");

                    for (int i = 0; i < ticketTypes.size(); i++) {
                        System.out.print((i + 1) + ": ");
                        System.out.println(((TicketType) ticketTypes.get(i)).getNaam());
                    }

                    int typeChoice = scanner.nextInt();
                    if (scanner.hasNext()) scanner.next();
                    TicketType tt = (TicketType) ticketTypes.get(typeChoice - 1);

                    //kies aantal

                    System.out.println("Hoeveel tickets wil je?:");
                    int aantalTickets = scanner.nextInt();
                    if (scanner.hasNext()) scanner.next();

                    //maak tickets
                    List<Ticket> tickets = new ArrayList<>();

                    for (int i = 0; i < aantalTickets; i++) {
                        Ticket ticket = new Ticket();
                        ticket.setTicketType(tt);
                        ticket.setTicketVerkoop(sale);
                        session.saveOrUpdate(ticket);
                        tickets.add(ticket);
                    }

                    System.out.println("Het totaal bedraagt:");
                    System.out.println(tt.getPrijs() * aantalTickets);

                    break;
                case 2:
                    //TODO opslaan passage
                    break;
                case 3:
                    //TODO zoeken optreden

                    break;
                case 4:
                    //TODO zoeken festival

                    break;
            }

            //gives the menu again
            menu = getOptionMainMenu();
        }
        return;
    }

    private int getOptionMainMenu() {
        System.out.println("Welcome");
        System.out.println("Kies welke opdracht je wilt uitvoeren:");

        //options
        System.out.println("1: Registratie van een verkoop");
        System.out.println("2: Opslaan passage");
        System.out.println("3: Zoeken otreden");
        System.out.println("4: Zoeken festival");
        System.out.println("5: Stoppen");
        int result = scanner.nextInt();
        if (scanner.hasNext()) scanner.next();

        return result;
    }

}

