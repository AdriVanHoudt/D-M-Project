package deel3;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.hibernate.*;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.w3c.dom.NodeList;
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
import javax.xml.xpath.XPathConstants;
import java.io.File;
import java.lang.Object;
import java.io.Reader;
import java.util.List;
import javax.xml.xpath.XPathExpression;
import org.jdom.xpath.XPath;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 2/11/13
 * Time: 18:21
 * To change this template use File | Settings | File Templates.
 */
public class Receiver {

    private static org.hibernate.Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    private static Transaction tx = session.beginTransaction();

    public static void main(String[] args)throws JMSException {
        Receiver r = new Receiver();
        r.receiveTrackings();
    }

    public void stringToDom(String xmlSource){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try
        {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlSource)));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            StreamResult result =  new StreamResult(new File("trackings.xml"));
            transformer.transform(source, result);
            getTrackingsFromXml();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void receiveTrackings() throws JMSException{
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

        while (true){
            // this call blocks until a new message arrives
            Message message = consumer.receive();
            TextMessage textMessage = (TextMessage) message;
            sb.append(textMessage.getText()+"\n");

            if (textMessage.getText().equals("exit")){
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

    public void getTrackingsFromXml() throws JDOMException{
        XPath xPath = XPath.newInstance("//Tracking");
        List<Element> trackings = xPath.selectNodes(openXML("trackings.xml"));

        for(Element e : trackings){
            session.saveOrUpdate(e);
        }
        tx.commit();
    }

    public static org.jdom.Document openXML(String bestaandsNaam){
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

}


