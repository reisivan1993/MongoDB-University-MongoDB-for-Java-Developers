import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
/**
 * Created by rei.sivan on 11/28/2015.
 */
public class MyTest {
    public static void main(String[] args) throws IOException {
        MongoClient client =  new MongoClient(new MongoClientURI("mongodb://localhost"));
        MongoDatabase db = client.getDatabase("test");

        MongoCollection album = db.getCollection("albums");
        MongoCollection image = db.getCollection("images");

        MongoCursor cur = image.find().iterator();
        cur.next();

        while (cur.hasNext()){
            Object id = cur.getServerCursor().getId();
            MongoCursor curalbum = album.find(new Document("images", id)).iterator();
            if(!curalbum.hasNext()){
                image.deleteOne(new Document("_id", id));
            }
            cur.next();
        }
    }
}

