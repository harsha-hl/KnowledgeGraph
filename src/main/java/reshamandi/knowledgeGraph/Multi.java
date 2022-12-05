package reshamandi.knowledgeGraph;


import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.*;

public class Multi extends Thread {
    public void run()
    {
        Driver driver = GraphDatabase.driver("neo4j+s://ba6b34f2.databases.neo4j.io", AuthTokens.basic("neo4j", "yjPkJyIuUi65j4p5yNUK4Tua1ZzgK3z4VPGc0iU_7rU"));
        try (Session session = driver.session()) {
            String answer[][] = session.writeTransaction(tx -> {
                Result result = tx.run("");
                List<Record> list = new ArrayList<Record>(result.list());
                String queryAnswer[][] = new String[list.size() + 1][11];
                queryAnswer[0] = list.get(0).keys().toArray(new String[0]);
                for (int j = 0; j < 11; j++)
                    queryAnswer[0][j] = queryAnswer[0][j].replace('_', ' ');
                for (int k = 0; k < list.size(); k++)
                    for (int j = 0; j < 11; j++)
                        queryAnswer[k + 1][j] = list.get(k).get(j).asString();
                return queryAnswer;
            });
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error");
        }

    }
}
 
// Main Class
class Multithread {
    public static void main(String[] args)
    {
        LinkedList<String> l= new LinkedList<>();
        l.add("none");
        for (int i = 0; i < 10; i++) {
            Multi object
                = new Multi();
            object.start();
            l.remove(1);
        }
    }
}