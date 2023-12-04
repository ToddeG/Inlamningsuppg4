package Server;

import java.io.*;
import java.net.Socket;

class ServerSidePlayer extends Thread {
    private final String player;
    private ServerSidePlayer opponent;
    private ObjectOutputStream outputObject;
    private ObjectInputStream inputObject;
    private int round = 0;
    private Boolean[][] score;
    public ServerSidePlayer(Socket socket, String player) {
        this.player = player;

        try {
            inputObject = new ObjectInputStream(socket.getInputStream());
            outputObject = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Avbrutet.");
        }
    }


    public String getPlayer(){
        return player;
    }
    public void setOpponent(ServerSidePlayer opponent) {
        this.opponent = opponent;
    }

    public void sendObject(Object object) {
        try {
            outputObject.flush();
            outputObject.writeObject(object);
            outputObject.reset();
        } catch (IOException e) {
            e.getStackTrace();
            Disconnected d = new Disconnected();
            opponent.sendObject(d);
        }
    }

    public Object recieveObject() {
        try {
            return inputObject.readObject();
        } catch (IOException e) {
            Disconnected d = new Disconnected();
            opponent.sendObject(d);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    public ServerSidePlayer getOpponent() {
        return opponent;
    }
    public void run(){

    }

    public void endGame() {

    }
}

class Disconnected {
}