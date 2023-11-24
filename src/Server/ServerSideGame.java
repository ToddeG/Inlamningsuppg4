package Server;

import DatabaseQuestion.QuestionObject;
import DatabaseQuestion.ReadFromFile;
import POJOs.GameScore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerSideGame {

    ServerSidePlayer player1;
    ServerSidePlayer player2;
    ServerSidePlayer currentPlayer;


    public ServerSideGame(ServerSidePlayer player1, ServerSidePlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void playGame() throws IOException, ClassNotFoundException, InterruptedException {
        ReadFromFile readFromFile = new ReadFromFile("src/DatabaseQuestion/QuestionFile.txt");
        player1.sendString("1");
        player2.sendString("2");

        player1.setNumberOfRoundsAndQuestions(4, 3);
        player2.setNumberOfRoundsAndQuestions(4, 3);

        String[] categories = new String[3];
        categories[0] = "Musik";
        categories[1] = "Historia";
        categories[2] = "Naturvetenskap";

        ArrayList<QuestionObject> currentQuestions = null;

        boolean firstRound = true;
        boolean middleRound = false;
        boolean lastRound = false;
        String command;
        currentPlayer = player1;

        while (true) {
            if (firstRound) {
                Thread.sleep(50);
                GameScore gameScoreTemp = new GameScore(player1.getScore(), player2.getScore());
                currentPlayer.getOpponent().sendObject(gameScoreTemp);
                currentPlayer.sendObject(categories);
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
                currentPlayer.sendObject(categories);
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
                lastRound = false;
            }
        }
    }
}
