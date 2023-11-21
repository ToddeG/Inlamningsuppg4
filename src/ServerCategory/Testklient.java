package ServerCategory;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Testklient {

    public Testklient(){

        try(Socket s = new Socket("127.0.0.1", 55555);
            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
        )
        {

            Object fromServer = "";
            String fromUser = "";

            fromServer = ois. readObject();
            System.out.println(fromServer);


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        Testklient t = new Testklient();
    }

}
