package Server;

import java.io.IOException;
import java.net.ServerSocket;
public class QuizkampenServer {

    public QuizkampenServer() throws IOException {
        ServerSocket listener = new ServerSocket(55555);
        System.out.println("Quizkampen Server is Running");
        try {
            while (true) {
                ServerSideGame game = new ServerSideGame();
                ServerSidePlayer player1
                        = new ServerSidePlayer(listener.accept(), "1");
                System.out.println("1");
                ServerSidePlayer player2
                        = new ServerSidePlayer(listener.accept(), "2");
                System.out.println("2");
                player1.setOpponent(player2);
                System.out.println("3");
                player2.setOpponent(player1);
                System.out.println("4");
                game.currentPlayer = player1;
                player1.start();
                System.out.println("5");
                player2.start();
                System.out.println("6");

            }
        } finally {
            listener.close();
        }
    }

        public static void main(String[] args) throws Exception {
            QuizkampenServer qs = new QuizkampenServer();
        }
    }