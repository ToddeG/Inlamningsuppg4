package POJOs;

import java.io.Serializable;

public class GameScore implements Serializable {
    private Boolean[][] player1Score;
    private Boolean[][] player2Score;
    public GameScore(Boolean[][] player1Score, Boolean[][] player2Score){
        this.player1Score = player1Score;
        this.player2Score = player2Score;
    }

    public Boolean[][] getPlayer1Score() {
        return player1Score;
    }

    public Boolean[][] getPlayer2Score() {
        return player2Score;
    }
}
