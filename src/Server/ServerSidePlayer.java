package Server;

import java.io.*;
import java.net.Socket;


/**
 * The class for the helper threads in this multithreaded server
 * application. A Player is identified by a character mark
 * which is either 'X' or 'O'. For communication with the
 * client the player has a socket with its input and output
 * streams. Since only text is being communicated we use a
 * reader and a writer.
 */
class ServerSidePlayer extends Thread {
    String player;
    ServerSidePlayer opponent;
    Socket socket;
    BufferedReader input;
    PrintWriter output;
    ObjectOutputStream outputObject;
    ObjectInputStream inputObject;
    private int round = 0;
    private Boolean[][] score;
    public ServerSidePlayer(Socket socket, String player) {
        this.socket = socket;
        this.player = player;

        try {
            inputObject = new ObjectInputStream(socket.getInputStream());
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputObject = new ObjectOutputStream(socket.getOutputStream());
            output = new PrintWriter(socket.getOutputStream(), true);

            


        } catch (IOException e) {
            System.out.println("Player died: " + e);
        }
    }

    /**
     * Accepts notification of who the opponent is.
     */
    public void setOpponent(ServerSidePlayer opponent) {
        this.opponent = opponent;
    }

    public void sendString(String message){
        output.println(message);
    }

    public void sendObject(Object object) throws IOException {
        outputObject.writeObject(object);
        outputObject.reset();
    }

    public String recieveString() {
        try {
            return input.readLine();
        } catch (IOException e) {
            System.out.println("Player " + player +" could not receive data " + e);
            throw new RuntimeException(e);
        }
    }

    public Object recieveObject() throws IOException, ClassNotFoundException {
        return inputObject.readObject();
    }

    public void addRound(){
        round++;
    }

    public int getRound(){
        return round;
    }

    public void setScore(int round, Boolean[] roundScore){
        score[round] = roundScore;
    }

    public void setNumberOfRoundsAndQuestions(int rounds, int questionsPerRound){
        score = new Boolean[rounds][questionsPerRound];
    }

    public Boolean[][] getScore(){
        return score;
    }

    /**
     * Returns the opponent.
     */
    public ServerSidePlayer getOpponent() {
        return opponent;
    }
    public void run(){

    }

}