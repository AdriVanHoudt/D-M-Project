package Main;

import model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.HibernateUtil;

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

                    //maak festivalganger
                    System.out.println("Geef je naam:");
                    FestivalGanger buyer = new FestivalGanger();
                    buyer.setNaam(scanner.next());
                    session.saveOrUpdate(buyer);

                    //getFestival
                    Festival festival = getFestival();

                    //maak ticketverkoop
                    TicketVerkoop sale = new TicketVerkoop();
                    sale.setFestivalGanger(buyer);
                    sale.setTimestamp(new Date());
                    sale.setType(TicketVerkoop.VerkoopsType.WEB);
                    sale.setFestival(festival);

                    session.saveOrUpdate(sale);

                    //get tickettypes
                    System.out.println("Welk tickettype wil je?");
                    Query getticketTypes = session.createQuery("from TicketType where naam like : festivalname");
                    String festivalname = "%" + festival.getName() + "%";
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

                    for (int i = 0; i < aantalTickets; i++) {
                        Ticket ticket = new Ticket();
                        ticket.setTicketType(tt);
                        ticket.setTicketVerkoop(sale);
                        session.saveOrUpdate(ticket);
                    }

                    System.out.println("Het totaal bedraagt:");
                    System.out.println(tt.getPrijs() * aantalTickets);

                    break;
                case 2:

                    //getfestival
                    Festival festival2 = getFestival();

                    //getZone
                    Zone zone = getZone(festival2);

                    //in out?
                    System.out.println("In or out? (in = 1; out = 0");
                    int isInInt = scanner.nextInt();
                    if (scanner.hasNext()) scanner.next();

                    Boolean isIn;
                    //isIn = true if isInInt = 1 else false
                    isIn = isInInt == 1;

                    //poslbandID
                    System.out.println("Geef de polsbandId:");
                    int polsbandID = scanner.nextInt();
                    if (scanner.hasNext()) scanner.next();

                    //gentracking
                    Tracking tracking = new Tracking();
                    tracking.setZone(zone);
                    tracking.setTimestamp(new Date());
                    tracking.setDirection(isIn);
                    tracking.setPolsbandId(polsbandID);
                    session.saveOrUpdate(tracking);

                    break;
                case 3:

                    //get festivals
                    Festival festival3 = getFestival();

                    //get zones
                    Zone zone2 = getZone(festival3);

                    //get date
                    Query getDates = session.createQuery("select fd.date from FestivalDag fd where fd.festival = :festival");

                    getDates.setParameter("festival", festival3);
                    List dates = getDates.list();

                    System.out.println("Kies de datum:");
                    for (int i = 0; i < dates.size(); i++) {
                        System.out.print((i + 1) + ": ");
                        System.out.println(((Date) dates.get(i)).toString());
                    }

                    int datePick = scanner.nextInt();
                    if (scanner.hasNext()) scanner.next();

                    //get optredens in zone where startdate < date >  enddate
                    Query getOptredens = session.createQuery("from Optreden op where op.startTime < : date AND op.endTime > : date");
                    getOptredens.setParameter("date", dates.get(datePick - 1));
                    List optredens = getOptredens.list();

                    for (Object o : optredens) {
                        System.out.println(((Optreden) o).getArtiest().getNaam());
                    }

                    break;
                case 4:
                    //TODO zoeken festival
                    //get artiest
                    //get dates
                    //get festivals
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

    private Festival getFestival() {
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

        return (Festival) festivals.get(festivalChoice - 1);
    }

    private Zone getZone(Festival festival) {
        //getzones
        Query getZones = session.createQuery("from Zone zone where zone.festival = : festival ");
        getZones.setParameter("festival", festival);
        List zones = getZones.list();

        //select zone

        System.out.println("Welke zone?");
        for (int i = 0; i < zones.size(); i++) {
            System.out.print((i + 1) + ": ");
            System.out.println(zones.get(i));
        }

        int zone = scanner.nextInt();
        if (scanner.hasNext()) scanner.next();

        return (Zone) zones.get(zone - 1);
    }

}

