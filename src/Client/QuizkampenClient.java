package Client;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class QuizkampenClient extends JFrame {

    JPanel jp = new JPanel(new BorderLayout());
    JPanel categoryPanel = new JPanel(new GridLayout(2, 1));
    JPanel questionPanel = new JPanel();
    JPanel answerPanel = new JPanel(new GridLayout(2, 2));
    JLabel questionNumber = new JLabel("Fråga 1");
    JLabel category = new JLabel("Musik");
    JLabel question = new JLabel("Vem sjunger bäst?");
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

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        QuizkampenClient qc = new QuizkampenClient();
    }
}