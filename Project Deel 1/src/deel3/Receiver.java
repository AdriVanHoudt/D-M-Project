package deel3;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import model.Tracking;
import model.Zone;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.hibernate.*;
import org.jdom.Attribute;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.xml.sax.InputSource;

import javax.jms.*;

import org.w3c.dom.Document;
import persistence.HibernateUtil;

import javax.jms.Session;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.net.UnknownHostException;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Receiver {

    private static org.hibernate.Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    private static Transaction tx = session.beginTransaction();

    List<Attribute> trackingZones = new ArrayList<>();
    List<Attribute> trackingTimestamps = new ArrayList<>();
    List<Attribute> trackingPolsbandIds = new ArrayList<>();
    List<Attribute> trackingTypes = new ArrayList<>();

    public static void main(String[] args) throws JMSException {
        Receiver r = new Receiver();
        r.receiveTrackings();
    }

    public void stringToDom(String xmlSource) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlSource)));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(new File("trackings.xml"));
            transformer.transform(source, result);
            getTrackingsFromXml();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void receiveTrackings() throws JMSException {
        String xmlString = "";
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n<Festival>\n");

        // Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        // Create a Connection to ActiceMQ
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session that allows you to work with activeMQ
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // Create the destination queue (or retrieve it, if it already exists)
        Destination destination = session.createQueue("TEST.SENDRECEIVE");

        // Create a MessageConsumer
        MessageConsumer consumer = session.createConsumer(destination);
        //consumer.setMessageListener(DeliveryMode.NON_PERSISTENT);

        while (true) {
            // this call blocks until a new message arrives
            Message message = consumer.receive();
            TextMessage textMessage = (TextMessage) message;
            sb.append(textMessage.getText() + "\n");

            if (textMessage.getText().equals("exit")) {
                consumer.close();
                session.close();
                connection.close();
                sb.append("\n</Festival>");
                xmlString = sb.toString();
                stringToDom(xmlString);
                System.exit(0);
            }

        }

    }

    public void getTrackingsFromXml() throws JDOMException {
        Date d = new Date();

        XPath xPath = XPath.newInstance("//@zone");
        trackingZones = xPath.selectNodes(openXML("trackings.xml"));

        xPath = XPath.newInstance("//@timestamp");
        trackingTimestamps = xPath.selectNodes(openXML("trackings.xml"));

        xPath = XPath.newInstance("//@polsbandId");
        trackingPolsbandIds = xPath.selectNodes(openXML("trackings.xml"));

        xPath = XPath.newInstance("//@type");
        trackingTypes = xPath.selectNodes(openXML("trackings.xml"));

        for (int i = 0; i < trackingZones.size(); i++) {
            Tracking tracking = new Tracking();
            tracking.setPolsbandId(Integer.parseInt(trackingPolsbandIds.get(i).getValue()));
            tracking.setDirection(Boolean.parseBoolean(trackingTypes.get(i).getValue()));
            try {
                String string = trackingTimestamps.get(i).getValue();
                d = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH).parse(string);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tracking.setTimestamp(d);
            Query getZones = session.createQuery("from Zone where id = " + Integer.parseInt(trackingZones.get(i).getValue()));
            List<Zone> zones = getZones.list();
            tracking.setZone(zones.get(0));
            //  session.saveOrUpdate(tracking);
        }
        // tx.commit();

        try{
            connectToMongoDB();
        }catch(UnknownHostException e){
            e.getStackTrace();
        }
    }

    public static org.jdom.Document openXML(String bestaandsNaam) {
        try {
            org.jdom.Document d = new SAXBuilder().build(bestaandsNaam);
            //return d.getRootElement();
            return d;

        } catch (JDOMException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return null;
    }

    public void connectToMongoDB() throws UnknownHostException {

        MongoConnection mc = new MongoConnection();
        DB db = mc.mongoClient.getDB("project");
        TreeSet<Integer> setTrackingPolsbandIds = new TreeSet<>();

        for(Attribute a : trackingPolsbandIds){
            setTrackingPolsbandIds.add(Integer.parseInt(a.getValue()));
        }

        BasicDBObject tracking = new BasicDBObject();

        DBCollection coll = db.getCollection("project");
        //collection leegmaken
        coll.drop();

        for (Integer i : setTrackingPolsbandIds) {
            Query getTrackings = session.createQuery("from Tracking where PolsbandId = " + i);
            List<Tracking> trackings = getTrackings.list();

            for (Tracking t : trackings) {
                tracking = new BasicDBObject("Timestamp", t.getTimestamp()).
                        append("Zone naam",t.getZone().getNaam()).
                        append("Type",t.isDirection());
            }

            BasicDBObject polsband = new BasicDBObject("PolsbandId " + i,tracking);
            coll.insert(polsband);
        }


    }

}


