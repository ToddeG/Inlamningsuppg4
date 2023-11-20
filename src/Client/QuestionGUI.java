package Client;

import javax.swing.*;
import java.awt.*;

public class QuestionGUI extends JFrame {
    JPanel jp = new JPanel(new BorderLayout());
    JPanel northPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JPanel southPanel = new JPanel(new GridLayout(2,2));
    JButton userName = new JButton("Tarre");
    JLabel question = new JLabel("What's my name?");
    JButton answer1 = new JButton("Svar 1");
    JButton answer2 = new JButton("Svar 2");
    JButton answer3 = new JButton("Svar 3");
    JButton answer4 = new JButton("Svar 4");

    public QuestionGUI() {
        this.add(jp);
        jp.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        jp.add(northPanel, BorderLayout.NORTH);
        jp.add(centerPanel, BorderLayout.CENTER);
        jp.add(southPanel, BorderLayout.SOUTH);
        northPanel.add(userName);
        centerPanel.add(question);
        southPanel.add(answer1);
        southPanel.add(answer2);
        southPanel.add(answer3);
        southPanel.add(answer4);

        setSize(350, 300);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        QuestionGUI qGUI = new QuestionGUI();
    }
}
