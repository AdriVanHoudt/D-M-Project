package deel3;

import com.mongodb.MongoClient;
import java.net.UnknownHostException;


/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 11/10/13
 * Time: 10:20
 * To change this template use File | Settings | File Templates.
 */
public class MongoConnection {
    MongoClient mongoClient;
    public MongoConnection() throws UnknownHostException{
        mongoClient = new MongoClient("localhost" , 27017);
    }
}
