package Server;

import POJOs.QuestionObject;
import DatabaseQuestion.ReadFromFile;
import POJOs.GameScore;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class ServerSideGame extends Thread{

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

    public void run() {
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
                try {
                    currentQuestions = handleFirstRound(readFromFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                firstRound = false;
                middleRound = true;
            } else if (middleRound) {
                try {
                    currentQuestions = handleMiddleRound(readFromFile, currentQuestions);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if (currentPlayer.getOpponent().getScore()[currentPlayer.getOpponent().getScore().length - 1][0] != null) {
                    middleRound = false;
                    lastRound = true;
                }
            } else if (lastRound) {
                try {
                    handleLastRound(currentQuestions);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                lastRound = false;
            }
        }
    }

    //Method to handle first round
    private ArrayList<QuestionObject> handleFirstRound(ReadFromFile readFromFile) throws IOException, InterruptedException, ClassNotFoundException {
        currentPlayer.getOpponent().recieveObject();
        GameScore gameScoreTemp = sendBothPlayersScoreboard();

        ArrayList<QuestionObject> currentQuestions = sendCategoriesLoadQuestions(readFromFile);
        sendQuestionsReceiveScore(currentQuestions);

        sendPlayersDifferentScoreboards(gameScoreTemp);
        return currentQuestions;
    }

    //Method to handle all rounds that are not the first or last
    private ArrayList<QuestionObject> handleMiddleRound(ReadFromFile readFromFile, ArrayList<QuestionObject> currentQuestions) throws IOException, ClassNotFoundException {
        sendQuestionsReceiveScore(currentQuestions);

        GameScore gameScoreTemp = sendBothPlayersScoreboard();

        currentQuestions = sendCategoriesLoadQuestions(readFromFile);
        sendQuestionsReceiveScore(currentQuestions);

        sendPlayersDifferentScoreboards(gameScoreTemp);
        return currentQuestions;
    }

    //Method to handle last round
    private void handleLastRound(ArrayList<QuestionObject> currentQuestions) throws IOException, ClassNotFoundException {
        sendQuestionsReceiveScore(currentQuestions);
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

    public void sendQuestionsReceiveScore(ArrayList<QuestionObject> currentQuestions) throws IOException, ClassNotFoundException {
        currentPlayer.sendObject(currentQuestions);
        currentPlayer.setScore(currentPlayer.getRound(), ((Boolean[]) currentPlayer.recieveObject()));
        currentPlayer.addRound();
    }

    public ArrayList<QuestionObject> sendCategoriesLoadQuestions(ReadFromFile readFromFile) throws IOException {
        currentPlayer.sendObject(readFromFile.getCategoryArrayList());
        return readFromFile.getQuestionCategoryArrayList(currentPlayer.recieveString());
    }

    public GameScore sendBothPlayersScoreboard() throws IOException {
        GameScore gameScoreTemp = new GameScore(Arrays.copyOf(player1.getScore(), player1.getScore().length),
                Arrays.copyOf(player2.getScore(), player2.getScore().length),
                scoreboardHeader[1]);
        currentPlayer.sendObject(gameScoreTemp);
        currentPlayer.getOpponent().sendObject(gameScoreTemp.getGameScoreDifferentStatus(scoreboardHeader[0]));
        return gameScoreTemp;
    }

    public void sendPlayersDifferentScoreboards(GameScore gameScoreTemp1) throws IOException {
        GameScore gameScoreTemp2 = new GameScore(player1.getScore(), player2.getScore(), scoreboardHeader[0]);
        currentPlayer.sendObject(gameScoreTemp2);
        currentPlayer = currentPlayer.getOpponent();
        currentPlayer.sendObject(gameScoreTemp1.getGameScoreDifferentStatus(scoreboardHeader[1]));
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
