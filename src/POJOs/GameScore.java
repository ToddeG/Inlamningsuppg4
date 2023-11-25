package POJOs;

import java.io.Serializable;

public class GameScore implements Serializable {
    private Boolean[][] player1Score;
    private Boolean[][] player2Score;
    private int player1ScoreNumber;
    private int player2ScoreNumber;
    public GameScore(Boolean[][] player1Score, Boolean[][] player2Score){
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        player1ScoreNumber = countScore(player1Score);
        player2ScoreNumber = countScore(player2Score);
    }

    public int countScore(Boolean[][] playerScore) {
        int score = 0;
        for (Boolean[] booleans : playerScore) {
            for (Boolean aBoolean : booleans) {
                try {
                    if (aBoolean) {
                        score++;
                    }
                } catch (NullPointerException e) {
                    return score;
                }
            }
        }
        return score;
    }
    public Boolean[][] getPlayer1Score() {
        return player1Score;
    }

    public Boolean[][] getPlayer2Score() {
        return player2Score;
    }
    public int getPlayer1ScoreNumber(){
        return player1ScoreNumber;
    }
    public int getPlayer2ScoreNumber(){
        return player2ScoreNumber;
    }
}
