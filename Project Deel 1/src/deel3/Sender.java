package deel3;

import model.Tracking;
import model.Zone;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.hibernate.*;
import persistence.HibernateUtil;

import javax.jms.*;
import javax.jms.Session;
import java.text.SimpleDateFormat;
import java.util.*;

public class Sender {
    private List<Tracking> trackings = new ArrayList<>();
    org.hibernate.Session sessionHibernate = HibernateUtil.getSessionFactory().getCurrentSession();
    Transaction tx = sessionHibernate.beginTransaction();

    public static void main(String[] args) throws JMSException {

        Sender s = new Sender();

        s.generateTracking();
        s.generateTrackingQueue();

    }

    public void generateTracking() {
        Query getZones = sessionHibernate.createQuery("from Zone");
        List<Zone> zones = getZones.list();
        int month, year, day, hourOfDay, minute, second, index;
        Random rand = new Random();
        Boolean isIn;

        for (Zone z : zones) {
            for (int j = 0; j < 1000; j++) {

                month = rand.nextInt(11);
                year = rand.nextInt(5) + 2011;
                day = rand.nextInt(31);
                hourOfDay = rand.nextInt(12) + 12;
                minute = rand.nextInt(60);
                second = rand.nextInt(60);

                index = rand.nextInt(200);

                GregorianCalendar calendar = new GregorianCalendar(year, month, day, hourOfDay, minute, second);

                Date timestamp = calendar.getTime();

                String formattedTimeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(timestamp);

                if (j % 2 == 0) {
                    isIn = false;
                } else {
                    isIn = true;
                }

                if (timestamp.before(new Date())) {
                    Tracking tracking = new Tracking();
                    tracking.setZone(z);
                    tracking.setTimestamp(new Date(formattedTimeStamp));
                    tracking.setDirection(isIn);
                    tracking.setPolsbandId(index);
                    trackings.add(tracking);
                }
            }
        }
    }

    public void generateTrackingQueue() throws JMSException {
        // Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        // Create a Connection to ActiceMQ
        Connection connection = connectionFactory.createConnection();
        connection.start();
        // Create a Session that allows you to work with activeMQ
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // Create the destination queue (or retrieve it, if it already exists)
        Destination destination = session.createQueue("TEST.SENDRECEIVE");
        // Create a MessageProducer for the Destination
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        String zoneXML = "";

        for (Tracking t : trackings) {
          zoneXML = "<Tracking zone=\"" + t.getZone().getId() + "\" timestamp=\"" + t.getTimestamp() + "\" polsbandId=\"" + t.getPolsbandId() + "\" type=\"" + t.isDirection() + "\" />";
          TextMessage message = session.createTextMessage(zoneXML);
          producer.send(message);
        }

        TextMessage message = session.createTextMessage("exit");
        producer.send(message);

        producer.close();
        session.close();
        connection.close();

        System.exit(0);
    }
}
