package Server;

import java.net.ServerSocket;
public class QuizkampenServer {

        public static void main(String[] args) throws Exception {ServerSocket listener = new ServerSocket(55555);
            System.out.println("Quizkampen Server is Running");
            try {
                while (true) {
                    ServerSidePlayer player1 = new ServerSidePlayer(listener.accept(), "1");
                    ServerSidePlayer player2 = new ServerSidePlayer(listener.accept(), "2");
                    player1.setOpponent(player2);
                    player2.setOpponent(player1);
                    ServerSideGame game = new ServerSideGame(player1, player2);
                    game.playGame();
                }
            } finally {
                listener.close();
            }
        }
    }