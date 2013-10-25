import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.HibernateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestFestival {
    public static void main(String[] args) throws ParseException {

        FestivalGanger festivalGanger = new FestivalGanger();
        FestivalGanger festivalGanger2 = new FestivalGanger();

        TicketVerkoop ticketVerkoop = new TicketVerkoop();

        Ticket ticket = new Ticket();

        TicketType ticktType = new TicketType();



        festivalGanger.setNaam("Vincent Huysmans");
        festivalGanger2.setNaam("Adri Van Houdt");

        ticketVerkoop.setFestivalGanger(festivalGanger);
        ticketVerkoop.setType("Website");

        String oldstring = "2011-01-18 08:00:00.0";
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(oldstring);

        ticketVerkoop.setTimestamp(date);

        ticket.setTicketVerkoop(ticketVerkoop);



        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.saveOrUpdate(festivalGanger);
        session.saveOrUpdate(festivalGanger2);
        session.saveOrUpdate(ticketVerkoop);
        tx.commit();
    }
}
