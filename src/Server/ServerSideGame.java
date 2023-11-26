package Server;

import POJOs.QuestionObject;
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
    private final String[] scoreboardHeader = new String[5];


    public ServerSideGame(ServerSidePlayer player1, ServerSidePlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
        scoreboardHeader[0] = "Väntar på att motståndaren ska spela klart";
        scoreboardHeader[1] = "Din tur att spela";
    }

    public void playGame() throws IOException, ClassNotFoundException, InterruptedException {
        //Calling method LoadProperties to set nr. of Rounds and Questions per game.
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
        currentPlayer = player1;
        while (true) {
            if (firstRound) {
                currentQuestions = handleFirstRound(readFromFile);
                firstRound = false;
                middleRound = true;
            } else if (middleRound) {
                currentQuestions = handleMiddleRound(readFromFile, currentQuestions);
                if (currentPlayer.getOpponent().getScore()[currentPlayer.getOpponent().getScore().length - 1][0] != null) {
                    middleRound = false;
                    lastRound = true;
                }
            } else if (lastRound) {
                handleLastRound(currentQuestions);
                lastRound = false;
            }
        }
    }

    //Method to handle first round
    private ArrayList<QuestionObject> handleFirstRound(ReadFromFile readFromFile) throws IOException, InterruptedException, ClassNotFoundException {
        Thread.sleep(100);
        GameScore gameScoreTemp1 = new GameScore(Arrays.copyOf(player1.getScore(), player1.getScore().length),
                Arrays.copyOf(player2.getScore(), player2.getScore().length),
                scoreboardHeader[0]);
        currentPlayer.getOpponent().sendObject(gameScoreTemp1);
        currentPlayer.sendObject(gameScoreTemp1.getGameScoreDifferentStatus(scoreboardHeader[1]));
        currentPlayer.sendObject(readFromFile.getCategoryArrayList());
        ArrayList<QuestionObject> currentQuestions = readFromFile.getQuestionCategoryArrayList(currentPlayer.recieveString());
        currentPlayer.sendObject(currentQuestions);
        currentPlayer.setScore(currentPlayer.getRound(), ((Boolean[]) currentPlayer.recieveObject()));
        currentPlayer.addRound();
        GameScore gameScoreTemp2 = new GameScore(player1.getScore(), player2.getScore(), scoreboardHeader[0]);
        currentPlayer.getOpponent().sendObject(gameScoreTemp1);
        currentPlayer.sendObject(gameScoreTemp2);
        currentPlayer = currentPlayer.getOpponent();
        return currentQuestions;
    }

    //Method to handle all rounds that are not the first or last
    private ArrayList<QuestionObject> handleMiddleRound(ReadFromFile readFromFile, ArrayList<QuestionObject> currentQuestions) throws IOException, ClassNotFoundException {
        currentPlayer.sendObject(currentQuestions);

        currentPlayer.setScore(currentPlayer.getRound(), ((Boolean[]) currentPlayer.recieveObject()));
        currentPlayer.addRound();

        GameScore gameScoreTemp1 = new GameScore(Arrays.copyOf(player1.getScore(), player1.getScore().length),
                Arrays.copyOf(player2.getScore(), player2.getScore().length),
                scoreboardHeader[1]);
        currentPlayer.sendObject(gameScoreTemp1);

        currentPlayer.sendObject(readFromFile.getCategoryArrayList());
        currentQuestions = readFromFile.getQuestionCategoryArrayList(currentPlayer.recieveString());
        currentPlayer.sendObject(currentQuestions);
        currentPlayer.setScore(currentPlayer.getRound(), ((Boolean[]) currentPlayer.recieveObject()));
        currentPlayer.addRound();

        GameScore gameScoreTemp2 = new GameScore(player1.getScore(), player2.getScore(), scoreboardHeader[0]);
        currentPlayer.sendObject(gameScoreTemp2);
        currentPlayer = currentPlayer.getOpponent();
        currentPlayer.sendObject(gameScoreTemp1.getGameScoreDifferentStatus(scoreboardHeader[1]));
        return currentQuestions;
    }

    //Method to handle last round
    private void handleLastRound(ArrayList<QuestionObject> currentQuestions) throws IOException, ClassNotFoundException {
        currentPlayer.sendObject(currentQuestions);
        currentPlayer.setScore(currentPlayer.getRound(), ((Boolean[]) currentPlayer.recieveObject()));
        currentPlayer.addRound();
        GameScore gameScoreTemp = new GameScore(player1.getScore(), player2.getScore(), scoreboardHeader[0]);
        int winner = checkWhoWon(gameScoreTemp);
        if((winner == 1 && currentPlayer.getPlayer().equals("1")) || (winner == 2 && currentPlayer.getPlayer().equals("2"))){
            currentPlayer.sendObject(gameScoreTemp.getGameScoreDifferentStatus("Du vann"));
            currentPlayer.getOpponent().sendObject(gameScoreTemp.getGameScoreDifferentStatus("Du förlorade"));
        }
        else if((winner == 2 && currentPlayer.getPlayer().equals("1")) || (winner == 1 && currentPlayer.getPlayer().equals("2"))){
            currentPlayer.sendObject(gameScoreTemp.getGameScoreDifferentStatus("Du förlorade"));
            currentPlayer.getOpponent().sendObject(gameScoreTemp.getGameScoreDifferentStatus("Du vann"));
        }
        else {
            currentPlayer.sendObject(gameScoreTemp.getGameScoreDifferentStatus("Det blev lika"));
            currentPlayer.getOpponent().sendObject(gameScoreTemp.getGameScoreDifferentStatus("Det blev lika"));
        }
    }

    //Method to determine winner
    public int checkWhoWon(GameScore gameScore) {
        if ((gameScore.getPlayer1ScoreNumber() > gameScore.getPlayer2ScoreNumber())) {
            return 1;
        } else if (gameScore.getPlayer2ScoreNumber() > gameScore.getPlayer1ScoreNumber()) {
            return 2;
        } else {
            return 3;
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
