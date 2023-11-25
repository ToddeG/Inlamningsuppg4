package Server;

import DatabaseQuestion.QuestionObject;
import DatabaseQuestion.ReadFromFile;
import POJOs.GameScore;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class ServerSideGame {

    ServerSidePlayer player1;
    ServerSidePlayer player2;
    ServerSidePlayer currentPlayer;


    public ServerSideGame(ServerSidePlayer player1, ServerSidePlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void playGame() throws IOException, ClassNotFoundException, InterruptedException {
        //Calling method LoadProperties to set nr. of Rounds and Questions per game. Testing
        int[] properties = LoadProperties();
        int rounds = properties[0];
        int questionsPerRound = properties[1];

        ReadFromFile readFromFile = new ReadFromFile("src/DatabaseQuestion/QuestionFile.txt", questionsPerRound);
        player1.sendString("1");
        player2.sendString("2");

        player1.setNumberOfRoundsAndQuestions(rounds, questionsPerRound);
        player2.setNumberOfRoundsAndQuestions(rounds, questionsPerRound);

        ArrayList<QuestionObject> currentQuestions = null;

        boolean firstRound = true;
        boolean middleRound = false;
        boolean lastRound = false;
        String command;
        currentPlayer = player1;

        while (true) {
            if (firstRound) {
                Thread.sleep(70);
                GameScore gameScoreTemp = new GameScore(player1.getScore(), player2.getScore());
                currentPlayer.getOpponent().sendObject(gameScoreTemp);
                currentPlayer.sendObject(readFromFile.getCategoryArrayList());
                command = currentPlayer.recieveString();
                currentQuestions = readFromFile.getQuestionCategoryArrayList(command);
                currentPlayer.sendObject(currentQuestions);
                currentPlayer.setScore(currentPlayer.getRound(), ((Boolean[]) currentPlayer.recieveObject()));
                currentPlayer.addRound();
                GameScore gameScore = new GameScore(player1.getScore(), player2.getScore());
                currentPlayer.sendObject(gameScore);
                currentPlayer = currentPlayer.getOpponent();
                firstRound = false;
                middleRound = true;
            } else if (middleRound) {
                currentPlayer.sendObject(currentQuestions);
                currentPlayer.setScore(currentPlayer.getRound(), ((Boolean[]) currentPlayer.recieveObject()));
                currentPlayer.addRound();
                currentPlayer.sendObject(readFromFile.getCategoryArrayList());
                command = currentPlayer.recieveString();
                currentQuestions = readFromFile.getQuestionCategoryArrayList(command);
                currentPlayer.sendObject(currentQuestions);
                currentPlayer.setScore(currentPlayer.getRound(), ((Boolean[]) currentPlayer.recieveObject()));
                currentPlayer.addRound();
                GameScore gameScore = new GameScore(player1.getScore(), player2.getScore());
                currentPlayer.sendObject(gameScore);
                if (currentPlayer.getScore()[currentPlayer.getScore().length - 1][0] != null) {
                    middleRound = false;
                    lastRound = true;
                }
                currentPlayer = currentPlayer.getOpponent();
            } else if (lastRound) {
                currentPlayer.sendObject(currentQuestions);
                currentPlayer.setScore(currentPlayer.getRound(), ((Boolean[]) currentPlayer.recieveObject()));
                currentPlayer.addRound();
                GameScore gameScore = new GameScore(player1.getScore(), player2.getScore());
                currentPlayer.sendObject(gameScore);
                currentPlayer.getOpponent().sendObject(gameScore);
                lastRound = false;
            }
        }
    }



    // Properties method for setting nr. of Rounds and Questions per game.
    public int[] LoadProperties(){

        Properties p = new Properties();

        try{
            p.load(new FileInputStream("src/Server/GameSettings.properties"));
        }

        catch(Exception e){
            System.out.println("File not found");
        }

        int rounds = Integer.parseInt(p.getProperty("rounds", "2"));
        int questionsPerRound = Integer.parseInt(p.getProperty("questionsPerRound", "2"));

        return new int[]{rounds, questionsPerRound};

    }


}
