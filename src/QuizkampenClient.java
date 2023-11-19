import java.io.*;
import java.net.Socket;

public class QuizkampenClient {

    public QuizkampenClient() {

        try (Socket s = new Socket("127.0.0.1", 55555);
             PrintWriter out = new PrintWriter(s.getOutputStream(),true);
             ObjectOutputStream out1 = new ObjectOutputStream(s.getOutputStream());
             BufferedReader serverIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
             ObjectInputStream serverInObject = new ObjectInputStream(s.getInputStream());
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));)
        {

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        QuizkampenClient qkc = new QuizkampenClient();
    }

}