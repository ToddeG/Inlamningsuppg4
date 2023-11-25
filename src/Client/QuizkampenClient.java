package Client;

import POJOs.QuestionObject;
import POJOs.GameScore;

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
        while (true) {
            if (round == 1 && player1or2.equals("1")) {
                out.println(client.loadChooseCategory((ArrayList<String>) serverInObject.readObject()));
                out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));
                GameScore gameScore = (GameScore) serverInObject.readObject();
                client.loadScoreboard(gameScore.getPlayer1Score(), gameScore.getPlayer2Score());
                round = 2;
            }
            else if (round == 3) {
                out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));
                GameScore gameScore = (GameScore) serverInObject.readObject();
                client.loadScoreboard(gameScore.getPlayer1Score(), gameScore.getPlayer2Score());
            }
            else if (round == 1 && player1or2.equals("2")) {
                GameScore gameScore = (GameScore) serverInObject.readObject();
                client.loadScoreboard(gameScore.getPlayer1Score(), gameScore.getPlayer2Score());
                round = 2;
            }
            else if (round == 4){
                GameScore gameScore = (GameScore) serverInObject.readObject();
                client.loadScoreboard(gameScore.getPlayer1Score(), gameScore.getPlayer2Score());
            }
            else {
                out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));
                out.println(client.loadChooseCategory((ArrayList<String>) serverInObject.readObject()));
                out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));
                GameScore gameScore = (GameScore) serverInObject.readObject();
                client.loadScoreboard(gameScore.getPlayer1Score(), gameScore.getPlayer2Score());
                if((player1or2.equals("1") && gameScore.getPlayer1Score()[gameScore.getPlayer1Score().length - 1][0] != null)
                        || (player1or2.equals("2") && gameScore.getPlayer2Score()[gameScore.getPlayer2Score().length - 1][0] != null)){
                    round = 4;
                }
                else if((player1or2.equals("1") && gameScore.getPlayer1Score()[gameScore.getPlayer1Score().length - 2][0] != null)
                || (player1or2.equals("2") && gameScore.getPlayer2Score()[gameScore.getPlayer2Score().length - 2][0] != null)){
                    round = 3;
                }
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
