import model.Festival;
import model.FestivalGanger;
import model.TicketVerkoop;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.HibernateUtil;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 22/10/13
 * Time: 23:04
 * To change this template use File | Settings | File Templates.
 */
public class TestFestival {
    public static void main(String[] args) {
        FestivalGanger festivalGanger = new FestivalGanger();
        FestivalGanger festivalGanger2 = new FestivalGanger();
        TicketVerkoop ticketVerkoop = new TicketVerkoop();

        festivalGanger.setNaam("Vincent Huysmans");
        festivalGanger2.setNaam("Adri Van Houdt");

        ticketVerkoop.setFestivalGanger(festivalGanger);
        ticketVerkoop.setType("Website");

        ticketVerkoop.setTimestamp();

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.saveOrUpdate(festivalGanger);
        session.saveOrUpdate(festivalGanger2);
        tx.commit();
    }
}
