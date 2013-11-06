package deel3;

import com.mongodb.MongoClient;
import java.net.UnknownHostException;


public class MongoConnection {
    MongoClient mongoClient;
    public MongoConnection() throws UnknownHostException{
        mongoClient = new MongoClient("localhost" , 27017);
    }
}
