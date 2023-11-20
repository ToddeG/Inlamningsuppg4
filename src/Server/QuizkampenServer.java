package Server;

import java.io.IOException;
import java.net.ServerSocket;
public class QuizkampenServer {

    public QuizkampenServer() throws IOException {
        ServerSocket listener = new ServerSocket(55555);
        System.out.println("Quizkampen Server is Running");
        try {
            while (true) {
                ServerSidePlayer player1
                        = new ServerSidePlayer(listener.accept(), "1");
                ServerSidePlayer player2
                        = new ServerSidePlayer(listener.accept(), "2");

                player1.setOpponent(player2);
                player2.setOpponent(player1);
                //game.currentPlayer = playerX;
                player1.start();
                player2.start();

            }
        } finally {
            listener.close();
        }
    }

        public static void main(String[] args) throws Exception {
            QuizkampenServer qs = new QuizkampenServer();
        }
    }