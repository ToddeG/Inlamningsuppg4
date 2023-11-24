package Client;

import DatabaseQuestion.QuestionObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TestInterface extends JFrame {

    JPanel jp = new JPanel(new BorderLayout());
    JPanel categoryPanel = new JPanel(new GridLayout(2, 1));
    JPanel questionPanel = new JPanel();
    JPanel answerPanel = new JPanel(new GridLayout(2, 2));
    JLabel questionNumber;
    JLabel category;
    JLabel question;
    JButton[] options = new JButton[4];

    TestInterface() {

        this.add(jp);
        jp.add(categoryPanel, BorderLayout.NORTH);
        jp.add(questionPanel, BorderLayout.CENTER);
        jp.add(answerPanel, BorderLayout.SOUTH);


        //pack();
        setSize(350, 300);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


    }

    public boolean[] loadQuestionRound(ArrayList<QuestionObject> questionRound) {
        boolean[] results = new boolean[questionRound.size()];
        for (int i = 0; i < questionRound.size(); i++) {
            final String[] answerTemp = new String[1];
            categoryPanel.removeAll();
            questionPanel.removeAll();
            answerPanel.removeAll();
            category = new JLabel(questionRound.get(i).getCategory());
            categoryPanel.add(category);
            questionNumber = new JLabel("Fråga: " + (i + 1));
            categoryPanel.add(questionNumber);
            question = new JLabel(questionRound.get(i).getQuestion());
            questionPanel.add(question);
            for (int j = 0; j < options.length; j++) {
                options[j] = new JButton(questionRound.get(i).getOptionList()[j]);
                answerPanel.add(options[j]);
                int finalJ = j;
                options[j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        answerTemp[0] = options[finalJ].getText();
                    }
                });
            }
            revalidate();
            repaint();
            while (answerTemp[0] == null) {

            }
            results[i] = answerTemp[0].equals(questionRound.get(i).getRightOption());
        }
        this.dispose();
        return results;
    }
}
