import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.HibernateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestFestival {
    public static void main(String[] args) throws ParseException {
        String oldstring;
        Date date;

        Festival festival = new Festival();

        FestivalDay festivalDay = new FestivalDay();
        FestivalDay festivalDay1 = new FestivalDay();

        FestivalGanger festivalGanger = new FestivalGanger();
        FestivalGanger festivalGanger2 = new FestivalGanger();

        TicketVerkoop ticketVerkoop = new TicketVerkoop();

        Ticket ticket = new Ticket();

        TicketType ticketType = new TicketType();

        TicketZone ticketZone = new TicketZone();

        TicketDag ticketDag = new TicketDag();

        Zone zone = new Zone();
        Zone zone1 = new Zone();

        festival.setLocation("Antwerpen");
        oldstring = "2013-10-25 12:00:00.0";
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(oldstring);
        festival.setStartDate(date);
        oldstring = "2013-10-26 01:00:00.0";
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(oldstring);
        festival.setEndDate(date);
        festival.setName("Rock Werchter");

        oldstring = "2013-10-25";
        date = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring);
        festivalDay.setDate(date);
        festivalDay.setFestival(festival);

        oldstring = "2013-10-26";
        date = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring);
        festivalDay1.setDate(date);
        festivalDay1.setFestival(festival);

        festivalGanger.setNaam("Vincent Huysmans");
        festivalGanger2.setNaam("Adri Van Houdt");

        ticketVerkoop.setFestivalGanger(festivalGanger);
        ticketVerkoop.setType("Website");

        oldstring = "2011-01-18 08:00:00.0";
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(oldstring);

        ticketVerkoop.setTimestamp(date);

        ticket.setTicketVerkoop(ticketVerkoop);
        ticket.setTicketType(ticketType);

        ticketType.setNaam("Dagticket");
        ticketType.setPrijs(35);

        zone.setNaam("Mainstage");
        zone.setCapaciteit(10);
        zone.setFestival(festival);
        zone1.setNaam("Zone voor Mainstage");
        zone1.setCapaciteit(6000);
        zone1.setFestival(festival);
        zone1.setZone(zone);

        ticketZone.setTicketType(ticketType);
        ticketZone.setZone(zone1);

        ticketDag.setFestivalDay(festivalDay1);
        ticketDag.setTicketType(ticketType);

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.saveOrUpdate(festivalGanger);
        session.saveOrUpdate(festivalGanger2);
        session.saveOrUpdate(ticketVerkoop);
        session.saveOrUpdate(ticket);
        session.saveOrUpdate(ticketType);
        session.saveOrUpdate(ticketDag);
        session.saveOrUpdate(ticketZone);
        session.saveOrUpdate(zone);
        session.saveOrUpdate(zone1);
        session.saveOrUpdate(festivalDay);
        session.saveOrUpdate(festivalDay1);
        session.saveOrUpdate(festival);
        tx.commit();
    }
}
