package Client;

import DatabaseQuestion.QuestionObject;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class QuizkampenClient extends JFrame {

    private Socket s;
    private PrintWriter out;
    private ObjectOutputStream out1;
    private BufferedReader serverIn;
    private ObjectInputStream serverInObject;
    private BufferedReader userInput;
    private String player1or2;


    public QuizkampenClient() throws IOException {

        s = new Socket("127.0.0.1", 55555);
        out = new PrintWriter(s.getOutputStream(), true);
        out1 = new ObjectOutputStream(s.getOutputStream());
        serverIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
        serverInObject = new ObjectInputStream(s.getInputStream());
        userInput = new BufferedReader(new InputStreamReader(System.in));
    }

    public void play() throws IOException, ClassNotFoundException, InterruptedException {
        String command;
        ChooseCategoryInterface client = new ChooseCategoryInterface();
        int round = 1;
        setPlayer(serverIn.readLine());
        System.out.println(player1or2);
        while (true) {
            if (round == 1 && player1or2.equals("1")) {
                out.println(client.loadChooseCategory((String[]) serverInObject.readObject()));
                out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));
                Boolean[][] tempScore1 = (Boolean[][]) serverInObject.readObject();
                Boolean[][] tempScore2 = (Boolean[][]) serverInObject.readObject();
                client.loadScoreboard(tempScore1, tempScore2);
                round++;
            }
            else if (round == 2 && player1or2.equals("1")) {
                out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));
                Boolean[][] tempScore1 = (Boolean[][]) serverInObject.readObject();
                Boolean[][] tempScore2 = (Boolean[][]) serverInObject.readObject();
                client.loadScoreboard(tempScore1, tempScore2);
            }
            else if (round == 1 && player1or2.equals("2")) {
                Boolean[][] tempScore1 = (Boolean[][]) serverInObject.readObject();
                Boolean[][] tempScore2 = (Boolean[][]) serverInObject.readObject();
                client.loadScoreboard(tempScore1, tempScore2);
                round++;
            }
            else {
                out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));
                out.println(client.loadChooseCategory((String[]) serverInObject.readObject()));
                out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));
                Boolean[][] tempScore1 = (Boolean[][]) serverInObject.readObject();
                Boolean[][] tempScore2 = (Boolean[][]) serverInObject.readObject();
                client.loadScoreboard(tempScore1, tempScore2);
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        QuizkampenClient qc = new QuizkampenClient();
        qc.play();
    }

    public void setPlayer(String number) {
        player1or2 = number;
    }
}