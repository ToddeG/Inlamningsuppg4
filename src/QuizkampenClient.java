import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class QuizkampenClient {
    public static void main(String[] args) {
        try (Socket s = new Socket("127.0.0.1", 55555);
             PrintWriter out = new PrintWriter(s.getOutputStream(),true);
             BufferedReader serverIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));)
        {

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}