package Client;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class QuizkampenClient extends JFrame {

    String inputFromServer;

    JPanel jp = new JPanel(new BorderLayout());
    JPanel categoryPanel = new JPanel(new GridLayout(2, 1));
    JPanel questionPanel = new JPanel();
    JPanel answerPanel = new JPanel(new GridLayout(2, 2));
    JLabel questionNumber = new JLabel("Fråga 1");
    JLabel category = new JLabel("Musik");
    JLabel question = new JLabel("Vem sjunger bäst?");
    JLabel playerStatus = new JLabel();

    JButton jb1 = new JButton("Svar 1");
    JButton jb2 = new JButton("Svar 2");
    JButton jb3 = new JButton("Svar 3");
    JButton jb4 = new JButton("Svar 4");


    public QuizkampenClient() {

        try (Socket s = new Socket("127.0.0.1", 55555);
             PrintWriter out = new PrintWriter(s.getOutputStream(),true);
             ObjectOutputStream out1 = new ObjectOutputStream(s.getOutputStream());
             BufferedReader serverIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
             ObjectInputStream serverInObject = new ObjectInputStream(s.getInputStream());
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));)
        {

            this.add(jp);

            jp.add(categoryPanel, BorderLayout.NORTH);
            jp.add(questionPanel, BorderLayout.CENTER);
            jp.add(answerPanel, BorderLayout.SOUTH);
            categoryPanel.add(playerStatus);
            categoryPanel.add(questionNumber);
            categoryPanel.add(category);
            questionPanel.add(question);
            answerPanel.add(jb1);
            answerPanel.add(jb2);
            answerPanel.add(jb3);
            answerPanel.add(jb4);

            //pack();
            setSize(350, 300);
            setVisible(true);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            //spelare1
            inputFromServer = serverIn.readLine();
            if(inputFromServer.startsWith("1"))
            {
                System.out.println("111111111111");
                System.out.println(inputFromServer);
                playerStatus.setText(inputFromServer);}
            //spelare2
            else if(inputFromServer.startsWith("2")){
                System.out.println("22222");
                System.out.println(inputFromServer);
                playerStatus.setText(inputFromServer);
            }
            while (true){
                //all kontroll-logik bör ligga här i while-satsen
                inputFromServer = serverIn.readLine();
                if(Objects.equals(inputFromServer, "Båda är online")){
                    playerStatus.setText("Båda är online");
                }
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        QuizkampenClient qc = new QuizkampenClient();
    }
}