package Client;

import DatabaseQuestion.QuestionObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class ChooseCategoryInterface extends JFrame {

    JFrame jframe = new JFrame();

    ChooseCategoryInterface() {
        jframe.setSize(350, 300);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jframe.setLocationRelativeTo(null);
    }

    public String loadChooseCategory(String[] categoriesInput) {
        jframe.getContentPane().removeAll();
        final String[] categoryTemp = new String[1];
        while (categoryTemp[0] == null) {
            JPanel basePanel = new JPanel(new BorderLayout());
            JPanel titelPanel = new JPanel();
            JPanel categoryPanel = new JPanel(new GridLayout(3, 1));
            JLabel titleLable = new JLabel("Välj Kategori");
            JButton[] buttons = new JButton[categoriesInput.length];
            jframe.add(basePanel);
            basePanel.add(titelPanel, BorderLayout.NORTH);
            basePanel.add(categoryPanel, BorderLayout.SOUTH);

            for (int i = 0; i < buttons.length; i++) {
                buttons[i] = new JButton(categoriesInput[i]);
                buttons[i].setPreferredSize(new Dimension(300, 75));
                buttons[i].setFont(new Font("defaultFont", Font.PLAIN, 18));
                int finalI = i;
                buttons[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        categoryTemp[0] = buttons[finalI].getText();
                    }
                });
                categoryPanel.add(buttons[i]);
            }

            titleLable.setFont(new Font("defaultFont", Font.PLAIN, 18));
            titelPanel.add(titleLable);
            jframe.setVisible(true);
        }
        return categoryTemp[0];
    }

    public Boolean[] loadQuestionRound(ArrayList<QuestionObject> questionRound) throws InterruptedException {
        jframe.getContentPane().removeAll();
        JPanel basePanel = new JPanel(new BorderLayout());
        JPanel categoryPanel = new JPanel(new GridLayout(2, 1));
        JPanel questionPanel = new JPanel();
        JPanel answerPanel = new JPanel(new GridLayout(2, 2));
        JLabel questionNumber;
        JLabel category;
        JLabel question;
        JButton[] options = new JButton[4];

        jframe.add(basePanel);
        basePanel.add(categoryPanel, BorderLayout.NORTH);
        basePanel.add(questionPanel, BorderLayout.CENTER);
        basePanel.add(answerPanel, BorderLayout.SOUTH);

        jframe.setVisible(true);
        jframe.revalidate();
        jframe.repaint();

        Boolean[] results = new Boolean[questionRound.size()];
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
                int finalJ = j;
                options[j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        answerTemp[0] = options[finalJ].getText();
                    }
                });
                options[j].setPreferredSize(new Dimension(70, 70));
                answerPanel.add(options[j]);
            }
            basePanel.revalidate();
            basePanel.repaint();
            while (answerTemp[0] == null) {
                Thread.sleep(10);
            }
            results[i] = answerTemp[0].equals(questionRound.get(i).getRightOption());
        }
        return results;
    }

    public void loadScoreboard(Boolean[][] player1Score, Boolean[][] player2Score) {
        jframe.getContentPane().removeAll();
        JPanel basePanel = new JPanel(new BorderLayout());
        jframe.add(basePanel);
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        basePanel.add(headerPanel, BorderLayout.NORTH);
        JLabel stage = new JLabel("Din tur");
        headerPanel.add(stage);

        JPanel playerPanel = new JPanel(new GridLayout(1, 3));
        headerPanel.add(playerPanel);

        JLabel player1Header = new JLabel("Player 1");
        playerPanel.add(player1Header);
        JLabel scoreHeader = new JLabel("0 - 0");
        playerPanel.add(scoreHeader);
        JLabel player2Header = new JLabel("Player 2");
        playerPanel.add(player2Header);

        JPanel scorePanel = new JPanel(new GridLayout(player1Score.length, 3));
        for (int i = 0; i < player1Score.length; i++) {
            JPanel player1Round = new JPanel(new GridLayout(1, player1Score[i].length));
            for (int j = 0; j < player1Score[i].length; j++) {
                JButton score = new JButton();
                if (player1Score[i][j] == null) {
                    score.setBackground(Color.gray);
                } else if (player1Score[i][j]) {
                    score.setBackground(Color.GREEN);
                } else if (!player1Score[i][j]) {
                    score.setBackground(Color.RED);
                }
                player1Round.add(score);
            }
            scorePanel.add(player1Round);
            JLabel round = new JLabel(String.valueOf(i + 1));
            round.setPreferredSize(new Dimension(5, 5));
            scorePanel.add(round);
            JPanel player2Round = new JPanel(new GridLayout(1, player2Score[i].length));
            for (int j = 0; j < player2Score[i].length; j++) {
                JButton score = new JButton();
                if (player2Score[i][j] == null) {
                    score.setBackground(Color.gray);
                } else if (player2Score[i][j]) {
                    score.setBackground(Color.GREEN);
                } else if (!player2Score[i][j]) {
                    score.setBackground(Color.RED);
                }
                player2Round.add(score);
            }
            scorePanel.add(player2Round);
        }
        basePanel.revalidate();
        basePanel.repaint();
        basePanel.add(scorePanel, BorderLayout.CENTER);

        JButton playButton = new JButton("Spela");
        basePanel.add(playButton, BorderLayout.SOUTH);
        jframe.setVisible(true);
        jframe.revalidate();
        jframe.repaint();
    }

    /*public static void main(String[] args){
        ChooseCategoryInterface client = new ChooseCategoryInterface();
        ArrayList<Boolean[]> player1Score = new ArrayList<>();
        ArrayList<Boolean[]> player2Score = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            Boolean[] roundScore = new Boolean[3];
            for(int j = 0; j < roundScore.length; j++){
                roundScore[j] = null;
            }
            player1Score.add(roundScore);
            player2Score.add(roundScore);
        }
        client.loadScoreboard(player1Score, player2Score);
    }*/
}
