package Client;

import POJOs.QuestionObject;
import POJOs.GameScore;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class QuizkampenClient extends JFrame {

    private final PrintWriter out;
    private final ObjectOutputStream out1;
    private final BufferedReader serverIn;
    private final ObjectInputStream serverInObject;
    private String player1or2;
    Interface client;

    public QuizkampenClient() throws IOException, InterruptedException {
        client = new Interface();
        Socket s = new Socket("127.0.0.1", 55555);
        out = new PrintWriter(s.getOutputStream(), true);
        out1 = new ObjectOutputStream(s.getOutputStream());
        serverIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
        serverInObject = new ObjectInputStream(s.getInputStream());
    }

    public void play() throws IOException, ClassNotFoundException, InterruptedException {
        boolean firstRound = true;
        boolean lastPlayerRound = false;
        boolean lastRoundOpponent = false;
        setPlayer((String) serverInObject.readObject());

        while (true) {
            try {
                if (firstRound && player1or2.equals("1")) { //First round for player 1 Test2
                    handleFirstRoundPlayer1(client);
                    firstRound = false;
                } else if (firstRound && player1or2.equals("2")) { //First round for player 2, only loads scoreboard
                    handleFirstRoundPlayer2(client);
                    firstRound = false;
                } else if (lastPlayerRound) { //Last playing round
                    handleLastPlayerRound(client);
                } else if (lastRoundOpponent) { //Last round for the player that didn't play the last round
                    handleLastRoundOpponent(client);
                } else { //Every round that isn't the first or last
                    GameScore gameScore3 = handleRegularRound(client);
                    if ((player1or2.equals("1") && gameScore3.getPlayer1Score()[gameScore3.getPlayer1Score().length - 1][0] != null) //Checks if the opponent is about to play the last round
                            || (player1or2.equals("2") && gameScore3.getPlayer2Score()[gameScore3.getPlayer2Score().length - 1][0] != null)) {
                        lastRoundOpponent = true;
                    } else if ((player1or2.equals("1") && gameScore3.getPlayer1Score()[gameScore3.getPlayer1Score().length - 2][0] != null)//Checks if this player is about to play the last round
                            || (player1or2.equals("2") && gameScore3.getPlayer2Score()[gameScore3.getPlayer2Score().length - 2][0] != null)) {
                        lastPlayerRound = true;
                    }
                }
            } catch (WriteAbortedException e) {
                System.out.println("Class hittades ej");
                gameDisconnected();
                break;
            }
        }
    }

    private void handleFirstRoundPlayer1(Interface client) throws IOException, ClassNotFoundException, InterruptedException {
        GameScore gameScore1 = (GameScore) serverInObject.readObject();
        client.loadScoreboard(gameScore1.getPlayer1Score(), gameScore1.getPlayer2Score(), gameScore1.getStatus(), player1or2);
        //Loads GUI with categories from server, sends back the answer
        out.println(client.loadChooseCategory((ArrayList<String>) serverInObject.readObject()));
        //Loads GUI with questions from server, sends back results
        out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));
        //Receives score from server, loads GUI with scoreboard, sets firstRound as false
        GameScore gameScore2 = (GameScore) serverInObject.readObject();
        client.loadScoreboard(gameScore2.getPlayer1Score(), gameScore2.getPlayer2Score(), gameScore2.getStatus(), player1or2);
        GameScore gameScore0 = (GameScore) serverInObject.readObject();
        client.loadScoreboard(gameScore0.getPlayer1Score(), gameScore0.getPlayer2Score(), gameScore0.getStatus(), player1or2);
    }

    private void handleFirstRoundPlayer2(Interface client) throws IOException, ClassNotFoundException, InterruptedException {
        out1.writeObject("");
        GameScore gameScore = (GameScore) serverInObject.readObject();
        client.loadScoreboard(gameScore.getPlayer1Score(), gameScore.getPlayer2Score(), gameScore.getStatus(), player1or2);
    }

    private void handleLastPlayerRound(Interface client) throws IOException, ClassNotFoundException, InterruptedException {
        //Player answers the questions the opponent just answered
        GameScore gameScore1 = (GameScore) serverInObject.readObject();
        client.loadScoreboard(gameScore1.getPlayer1Score(), gameScore1.getPlayer2Score(), gameScore1.getStatus(), player1or2);
        out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));
        //Loads scoreboard
        GameScore gameScore2 = (GameScore) serverInObject.readObject();
        client.loadScoreboard(gameScore2.getPlayer1Score(), gameScore2.getPlayer2Score(), gameScore2.getStatus(), player1or2);
    }

    private void handleLastRoundOpponent(Interface client) throws InterruptedException, IOException, ClassNotFoundException {
        //Only loads scoreboard
        GameScore gameScore = (GameScore) serverInObject.readObject();
        client.loadScoreboard(gameScore.getPlayer1Score(), gameScore.getPlayer2Score(), gameScore.getStatus(), player1or2);
    }

    private GameScore handleRegularRound(Interface client) throws InterruptedException, IOException, ClassNotFoundException {

        GameScore gameScore1 = (GameScore) serverInObject.readObject();
        client.loadScoreboard(gameScore1.getPlayer1Score(), gameScore1.getPlayer2Score(), gameScore1.getStatus(), player1or2);

        //Answer the questions the opponent just answered
        out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));

        GameScore gameScore2 = (GameScore) serverInObject.readObject();
        client.loadScoreboard(gameScore2.getPlayer1Score(), gameScore2.getPlayer2Score(), gameScore2.getStatus(), player1or2);
        //Choose category
        out.println(client.loadChooseCategory((ArrayList<String>) serverInObject.readObject()));
        //Answer the questions from the category
        out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));
        //Load scoreboard
        GameScore gameScore3 = (GameScore) serverInObject.readObject();
        client.loadScoreboard(gameScore3.getPlayer1Score(), gameScore3.getPlayer2Score(), gameScore3.getStatus(), player1or2);
        GameScore gameScore0 = (GameScore) serverInObject.readObject();
        client.loadScoreboard(gameScore0.getPlayer1Score(), gameScore0.getPlayer2Score(), gameScore0.getStatus(), player1or2);
        return gameScore3;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        QuizkampenClient qc = new QuizkampenClient();
        qc.play();
    }

    public void setPlayer(String number) {
        player1or2 = number;
    }

    public void gameDisconnected() {
        JOptionPane.showMessageDialog(null, "Motspelaren taggade, spelet avbrutet");
    }
}
