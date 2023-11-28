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

            /*System.out.println(startMode + " " + firstRound);*/
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
        }

    }

    private void handleFirstRoundPlayer1(Interface client) throws IOException, ClassNotFoundException, InterruptedException {
        loadScoreBoard(client);
        out.println(client.loadChooseCategory((ArrayList<String>) serverInObject.readObject()));
        out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));
        loadScoreBoard(client);
        loadScoreBoard(client);
    }

    private void handleFirstRoundPlayer2(Interface client) throws IOException, ClassNotFoundException, InterruptedException {
        out1.writeObject("");
        loadScoreBoard(client);
    }

    private void handleLastPlayerRound(Interface client) throws IOException, ClassNotFoundException, InterruptedException {
        loadScoreBoard(client);
        out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));
        loadScoreBoard(client);
    }

    private void handleLastRoundOpponent(Interface client) throws InterruptedException, IOException, ClassNotFoundException {
        loadScoreBoard(client);
    }

    private GameScore handleRegularRound(Interface client) throws InterruptedException, IOException, ClassNotFoundException {
        loadScoreBoard(client);
        out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));
        loadScoreBoard(client);
        out.println(client.loadChooseCategory((ArrayList<String>) serverInObject.readObject()));
        out1.writeObject(client.loadQuestionRound((ArrayList<QuestionObject>) serverInObject.readObject()));
        GameScore gameScore3 = loadScoreBoard(client);
        loadScoreBoard(client);
        return gameScore3;
    }

    public GameScore loadScoreBoard(Interface client) throws InterruptedException, IOException, ClassNotFoundException {
        GameScore gameScore = (GameScore) serverInObject.readObject();
        client.loadScoreboard(gameScore.getPlayer1Score(), gameScore.getPlayer2Score(), gameScore.getStatus(), player1or2);
        return gameScore;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        QuizkampenClient qc = new QuizkampenClient();
        qc.play();
    }

    public void setPlayer(String number) {
        player1or2 = number;
    }

    public void gameDisconnected() {
        JOptionPane.showMessageDialog(null, "Motspelaren har avslutat spelet, spelet avbrutet");
    }
}
