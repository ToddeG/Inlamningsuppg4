
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * The class for the helper threads in this multithreaded server
 * application. A Player is identified by a character mark
 * which is either 'X' or 'O'. For communication with the
 * client the player has a socket with its input and output
 * streams. Since only text is being communicated we use a
 * reader and a writer.
 */
class ServerSidePlayer extends Thread {
    String player;
    ServerSidePlayer opponent;
    Socket socket;
    BufferedReader input;
    PrintWriter output;
    public ServerSidePlayer(Socket socket, String player) {
        this.socket = socket;
        this.player = player;

        try {
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException e) {
            System.out.println("Player died: " + e);
        }
    }

    /**
     * Accepts notification of who the opponent is.
     */
    public void setOpponent(ServerSidePlayer opponent) {
        this.opponent = opponent;
    }

    /**
     * Returns the opponent.
     */
    public ServerSidePlayer getOpponent() {
        return opponent;
    }

    public void run() {

    }
}