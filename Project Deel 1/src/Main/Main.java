package Main;

import model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.HibernateUtil;
import persistence.TestData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    Scanner scanner = new Scanner(System.in);
    static Session session;
    static Transaction tx;


    public static void main(String[] args) throws ParseException {
        Main me = new Main();

        me.askTestData();

        //session and transaction placed here because if you do want testdata you will open 2 session which is not allowed by hibernate
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        tx = session.beginTransaction();
        me.runConsole();
        tx.commit();
    }

    //ask for testdata
    private void askTestData() {
        System.out.println("Wilt u de testdata laden? (y/n)");
        if (scanner.next().equals("y")) {
            TestData td = new TestData();
            td.runTestData();
        }
    }

    //start and run console app
    private void runConsole() throws ParseException {
        //init menu
        int menu = getOptionMainMenu();
        while (menu != 5) {
            //print menu and returns the chosen option
            switch (menu) {
                case 1:

                    //maak festivalganger
                    System.out.println("Geef je naam:");
                    FestivalGanger buyer = new FestivalGanger();
                    buyer.setNaam(scanner.nextLine());

                    //getFestival
                    Festival festival = getFestival();

                    //maak ticketverkoop
                    TicketVerkoop sale = new TicketVerkoop();
                    sale.setFestivalGanger(buyer);
                    sale.setTimestamp(new Date());
                    sale.setType(TicketVerkoop.VerkoopsType.WEB);
                    sale.setFestival(festival);

                    //get tickettypes
                    System.out.println("Welk tickettype wil je?");
                    Query getticketTypes = session.createQuery("from TicketType where naam like :festivalname");
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
                    scanner.nextLine();
                    TicketType tt = (TicketType) ticketTypes.get(typeChoice - 1);

                    //kies aantal

                    System.out.println("Hoeveel tickets wil je?:");
                    int aantalTickets = scanner.nextInt();
                    scanner.nextLine();

                    //maak tickets

                    session.saveOrUpdate(buyer);
                    session.saveOrUpdate(sale);
                    for (int i = 0; i < aantalTickets; i++) {
                        Ticket ticket = new Ticket();
                        ticket.setTicketType(tt);
                        ticket.setTicketVerkoop(sale);
                        session.saveOrUpdate(ticket);
                    }

                    System.out.print("Het totaal bedraagt:");
                    System.out.println("€" + (tt.getPrijs() * aantalTickets));

                    break;
                case 2:

                    //getfestival
                    Festival festival2 = getFestival();

                    //getZone
                    Zone zone = getZone(festival2);

                    //in out?
                    System.out.println("In or out? (in = 1 / out = 0)");
                    int isInInt = scanner.nextInt();
                    scanner.nextLine();

                    Boolean isIn;
                    //isIn = true if isInInt = 1 else false
                    isIn = isInInt == 1;

                    //poslbandID
                    System.out.println("Geef de polsbandId:");
                    int polsbandID = scanner.nextInt();
                    scanner.nextLine();

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
                    Query getDates = session.createQuery("select op.startTime from FestivalDag fd, Optreden op where fd.festival = :festival and op.festivalDag = fd");

                    getDates.setParameter("festival", festival3);
                    List dates = getDates.list();

                    System.out.println("Kies de datum:");
                    for (int i = 0; i < dates.size(); i++) {
                        System.out.print((i + 1) + ": ");
                        System.out.println((dates.get(i)).toString());
                    }

                    int datePick = scanner.nextInt();
                    scanner.nextLine();

                    //get optredens in zone where startdate < date >  enddate
                    Query getOptredens = session.createQuery("from Optreden op where :date > op.startTime and :date < op.endTime and op.zone = :zone");

                    //add 1 min
                    Calendar cal = Calendar.getInstance();
                    cal.setTime((Date) dates.get(datePick - 1));
                    cal.add(Calendar.MINUTE, 1);

                    getOptredens.setParameter("date", cal.getTime());
                    getOptredens.setParameter("zone", zone2);
                    List optredens = getOptredens.list();

                    //output
                    System.out.println("");
                    System.out.println("Optreden: ");
                    for (Object o : optredens) {
                        System.out.println(((Optreden) o).getArtiest().getNaam());
                    }

                    break;
                case 4:
                    //get artiests
                    Query getArtiests = session.createQuery("from Artiest");
                    List artiests = getArtiests.list();

                    System.out.println("Kies je artiest");
                    for (int i = 0; i < artiests.size(); i++) {
                        System.out.print((i + 1) + ": ");
                        System.out.println(((Artiest) artiests.get(i)).getNaam());
                    }

                    int artiestPick = scanner.nextInt();
                    scanner.nextLine();

                    //get dates
                    SimpleDateFormat df = new SimpleDateFormat("mm/dd/yyyy");
                    System.out.println("Geef datum 1 (mm/dd/yyyy):");
                    String date1 = scanner.next();
                    System.out.println("Geeft datum 2 (mm/dd/yyyy)");
                    String date2 = scanner.next();

                    Date d1 = df.parse(date1.trim());
                    Date d2 = df.parse(date2.trim());

                    //get festivals
                    Query getFestivalDaysFromArtiest = session.createQuery("select o.festivalDag from Optreden o where o.artiest = :artiest ");
                    getFestivalDaysFromArtiest.setParameter("artiest", artiests.get(artiestPick - 1));

                    List festivaldays = getFestivalDaysFromArtiest.list();

                    Set<Festival> setFestival = new HashSet<>();
                    for (Object festivalday1 : festivaldays) {
                        FestivalDag festivalday = (FestivalDag) festivalday1;
                        Query getFestivals = session.createQuery("select fd.festival from FestivalDag fd, Optreden o where fd = :festivaldag AND fd.date BETWEEN :date1 AND :date2");
                        getFestivals.setParameter("festivaldag", festivalday);
                        getFestivals.setDate("date1", d1);
                        getFestivals.setDate("date2", d2);
                        setFestival.addAll(getFestivals.list());
                    }

                    System.out.println("Festivals: ");
                    for (Festival f : setFestival) {
                        System.out.println(f.getName());
                    }
                    break;
            }

            //gives the menu again
            menu = getOptionMainMenu();
        }
    }

    private int getOptionMainMenu() {
        System.out.println("");
        System.out.println("Kies welke opdracht je wilt uitvoeren:");

        //options
        System.out.println("1: Registratie van een verkoop");
        System.out.println("2: Opslaan passage");
        System.out.println("3: Zoeken otreden");
        System.out.println("4: Zoeken festival");
        System.out.println("5: Stoppen");
        int result = scanner.nextInt();
        scanner.nextLine();

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
        scanner.nextLine();

        return (Festival) festivals.get(festivalChoice - 1);
    }

    private Zone getZone(Festival festival) {
        //getzones
        Query getZones = session.createQuery("from Zone zone where zone.festival = :festival and zone.naam like '%Stage%' ");
        getZones.setParameter("festival", festival);
        List zones = getZones.list();

        //select zone

        System.out.println("Welke zone?");
        for (int i = 0; i < zones.size(); i++) {
            System.out.print((i + 1) + ": ");
            System.out.println(((Zone) zones.get(i)).getNaam());
        }

        int zone = scanner.nextInt();
        scanner.nextLine();

        return (Zone) zones.get(zone - 1);
    }

}

