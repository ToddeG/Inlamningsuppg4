package Server;

import DatabaseQuestion.QuestionObject;
import DatabaseQuestion.ReadFromFile;

import java.io.IOException;
import java.util.ArrayList;

public class ServerSideGame {

    ServerSidePlayer player1;
    ServerSidePlayer player2;
    ServerSidePlayer currentPlayer;


    public ServerSideGame(ServerSidePlayer player1, ServerSidePlayer player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    public void playGame() throws IOException, ClassNotFoundException, InterruptedException {
        ReadFromFile readFromFile = new ReadFromFile("src/DatabaseQuestion/QuestionFile.txt");
        player1.sendString("1");
        player2.sendString("2");
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

        while(true){
            if(firstRound){
                Thread.sleep(50);
                currentPlayer.getOpponent().sendObject(player1.score);
                currentPlayer.getOpponent().sendObject(player2.score);
                currentPlayer.sendObject(categories);
                command = currentPlayer.recieveString();
                currentQuestions = readFromFile.getQuestionCategoryArrayList(command);
                currentPlayer.sendObject(currentQuestions);
                currentPlayer.score[currentPlayer.round] = ((Boolean[]) currentPlayer.recieveObject());
                currentPlayer.round++;
                currentPlayer.sendObject(player1.score);
                currentPlayer.sendObject(player2.score);
                currentPlayer = currentPlayer.getOpponent();
                firstRound = false;
                middleRound = true;
            }
            else if(middleRound){
                currentPlayer.sendObject(currentQuestions);
                currentPlayer.score[currentPlayer.round] = ((Boolean[]) currentPlayer.recieveObject());
                currentPlayer.round++;
                currentPlayer.sendObject(categories);
                command = currentPlayer.recieveString();
                currentQuestions = readFromFile.getQuestionCategoryArrayList(command);
                currentPlayer.sendObject(currentQuestions);
                currentPlayer.score[currentPlayer.round] = ((Boolean[]) currentPlayer.recieveObject());
                currentPlayer.round++;
                currentPlayer.sendObject(player1.score);
                currentPlayer.sendObject(player2.score);
                currentPlayer = currentPlayer.getOpponent();
                middleRound = false;
                lastRound = true;
            }
            else if(lastRound){
                currentPlayer.sendObject(currentQuestions);
                currentPlayer.score[currentPlayer.round] = ((Boolean[]) currentPlayer.recieveObject());
                currentPlayer.round++;
                currentPlayer.sendObject(categories);
                currentPlayer.sendObject(player1.score);
                currentPlayer.sendObject(player2.score);
            }
        }
    }
}
