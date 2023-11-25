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
        boolean firstRound = true;
        boolean lastPlayerRound = false;
        boolean lastRoundOpponent = false;
        setPlayer(serverIn.readLine());
        while (true) {
            if (firstRound && player1or2.equals("1")) { //First round for player 1
                //Loads GUI with categories from server, sends back the answer
                out.println(client.loadChooseCategory((String[]) serverInObject.readObject()));
                //Loads GUI with questions from server, sends back results
                out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));
                //Recieves score from server, loads GUI with scoreboard, sets firstRound as false
                GameScore gameScore = (GameScore) serverInObject.readObject();
                client.loadScoreboard(gameScore.getPlayer1Score(), gameScore.getPlayer2Score());
                firstRound = false;
            }
            else if (firstRound && player1or2.equals("2")) { //First round for player 2, only loads scoreboard
                GameScore gameScore = (GameScore) serverInObject.readObject();
                client.loadScoreboard(gameScore.getPlayer1Score(), gameScore.getPlayer2Score());
                firstRound = false;
            }
            else if (lastPlayerRound) { //Last playing round
                //Player answers the questions the opponent just answered
                out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));
                //Loads scoreboard
                GameScore gameScore = (GameScore) serverInObject.readObject();
                client.loadScoreboard(gameScore.getPlayer1Score(), gameScore.getPlayer2Score());
            }
            else if (lastRoundOpponent){ //Last round for the player that didn't play the last round
                //Only loads scoreboard
                GameScore gameScore = (GameScore) serverInObject.readObject();
                client.loadScoreboard(gameScore.getPlayer1Score(), gameScore.getPlayer2Score());
            }
            else { //Every round that isn't the first or last
                //Answer the questions the opponent just answered
                out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));
                //Choose category
                out.println(client.loadChooseCategory((String[]) serverInObject.readObject()));
                //Answer the questions from the category
                out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));
                //Load scoreboard
                GameScore gameScore = (GameScore) serverInObject.readObject();
                client.loadScoreboard(gameScore.getPlayer1Score(), gameScore.getPlayer2Score());
                if((player1or2.equals("1") && gameScore.getPlayer1Score()[gameScore.getPlayer1Score().length - 1][0] != null) //Checks if the opponent is about to play the last round
                        || (player1or2.equals("2") && gameScore.getPlayer2Score()[gameScore.getPlayer2Score().length - 1][0] != null)){
                    lastRoundOpponent = true;
                }
                else if((player1or2.equals("1") && gameScore.getPlayer1Score()[gameScore.getPlayer1Score().length - 2][0] != null)//Checks if this player is about to play the last round
                || (player1or2.equals("2") && gameScore.getPlayer2Score()[gameScore.getPlayer2Score().length - 2][0] != null)){
                    lastPlayerRound = true;
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
