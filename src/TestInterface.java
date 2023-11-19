import javax.swing.*;
import java.awt.*;

public class TestInterface extends JFrame {

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

    TestInterface(){

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

}
