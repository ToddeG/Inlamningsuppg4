package Server;

import java.io.*;
import java.net.Socket;


class ServerSidePlayer extends Thread {
    private String player;
    private ServerSidePlayer opponent;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private ObjectOutputStream outputObject;
    private ObjectInputStream inputObject;
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

    public ServerSidePlayer getOpponent() {
        return opponent;
    }
    public void run(){

    }

}