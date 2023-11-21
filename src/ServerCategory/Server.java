package ServerCategory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {

    Database d = new Database();

    public List<QuestionObject> Server(){

        try(ServerSocket ss = new ServerSocket(55555);
            Socket s = ss.accept();

            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))
        ){
            String klientPath;

            while((klientPath = in.readLine()) != null) {
                List<QuestionObject> mixedList = new ArrayList<>(d.Category(klientPath));
                Collections.shuffle(mixedList);

                List<QuestionObject> fourQuestionList = mixedList.subList(0, 4);
                return fourQuestionList;
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void main(String[] args) {
        Server s = new Server();
    }

}
