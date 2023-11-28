package Client;

import POJOs.QuestionObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Interface extends JFrame {

    JFrame jframe = new JFrame();

    private ObjectInputStream serverInObject;


    Interface() throws InterruptedException {
        JPanel basePanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        JPanel emptyPanel = new JPanel();
        JPanel emptyPanel2 = new JPanel();
        JLabel titelText = new JLabel("Välkommen till Quizkampen!");
        JTextArea messageWindow = new JTextArea();
        JButton startButton = new JButton("Starta spel");

        jframe.add(basePanel);
        emptyPanel.setBackground(Color.blue);
        emptyPanel2.setBackground(Color.blue);
        buttonPanel.setBackground(Color.blue);
        basePanel.setBackground(Color.blue);
        jframe.add(emptyPanel, BorderLayout.WEST);
        jframe.add(emptyPanel2, BorderLayout.EAST);
        basePanel.add(messageWindow, BorderLayout.CENTER);
        basePanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        titelText.setBorder(BorderFactory.createEmptyBorder( 0, 20, 30, 20));
        basePanel.add(titelText, BorderLayout.NORTH);
        titelText.setHorizontalAlignment(SwingConstants.CENTER);
        titelText.setForeground(Color.WHITE);
        titelText.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
        jframe.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(startButton);
        startButton.setSize(10, 10);

        final boolean[] loop = {true};

        startButton.addActionListener(e -> {
            if(e.getSource() == startButton){
                try {
                    loop[0] = false;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        jframe.setSize(350,300);
        jframe.setVisible(true);
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(EXIT_ON_CLOSE);

        while(loop[0]) {
            Thread.sleep(10);
        }

        buttonPanel.remove(startButton);
        messageWindow.setText("Väntar på motståndare");
    }



    public String loadChooseCategory(ArrayList<String> categoriesInput) {
        jframe.getContentPane().removeAll();
        final String[] categoryTemp = new String[1];
        while (categoryTemp[0] == null) {
            JPanel basePanel = new JPanel(new BorderLayout());
            JPanel titlePanel = new JPanel();
            JPanel categoryPanel = new JPanel(new GridLayout(3, 1));
            JLabel titleLabel = new JLabel("Välj Kategori");
            JButton[] buttons = new JButton[categoriesInput.size()];
            jframe.add(basePanel);
            basePanel.add(titlePanel, BorderLayout.NORTH);
            basePanel.add(categoryPanel, BorderLayout.SOUTH);

            for (int i = 0; i < buttons.length; i++) {
                buttons[i] = new JButton(categoriesInput.get(i));
                buttons[i].setPreferredSize(new Dimension(300, 75));
                buttons[i].setFont(new Font("defaultFont", Font.PLAIN, 18));
                buttons[i].setFocusPainted(false);
                int finalI = i;
                buttons[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        categoryTemp[0] = buttons[finalI].getText();
                    }
                });
                categoryPanel.add(buttons[i]);
            }

            titleLabel.setFont(new Font("defaultFont", Font.PLAIN, 18));
            titlePanel.add(titleLabel);
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
            category.setHorizontalAlignment(SwingConstants.CENTER);
            categoryPanel.add(category);
            questionNumber = new JLabel("Fråga: " + (i + 1));
            questionNumber.setHorizontalAlignment(SwingConstants.CENTER);
            categoryPanel.add(questionNumber);
            question = new JLabel();
            question.setPreferredSize(new Dimension(300, 100));
            question.setText("<html>" + questionRound.get(i).getQuestion() + "</html>");
            question.setHorizontalAlignment(SwingConstants.CENTER);
            questionPanel.add(question);
            for (int j = 0; j < options.length; j++) {
                options[j] = new JButton("<html>" + questionRound.get(i).getOptionList()[j] + "</html>");
                options[j].setFocusPainted(false);
                int finalJ = j;
                int finalI = i;
                options[j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (answerTemp[0] == null) {
                            answerTemp[0] = options[finalJ].getText();
                            if(answerTemp[0].equals("<html>" + questionRound.get(finalI).getRightOption() + "</html>")){
                                options[finalJ].setBackground(Color.GREEN);
                            }
                            else{
                                options[finalJ].setBackground(Color.RED);
                            }
                        }
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
            results[i] = answerTemp[0].equals("<html>" + questionRound.get(i).getRightOption() + "</html>");
            Thread.sleep(500);
        }
        return results;
    }

    public void loadScoreboard(Boolean[][] player1Score, Boolean[][] player2Score, String stageString, String player1or2) throws InterruptedException {
        jframe.getContentPane().removeAll();
        JPanel basePanel = new JPanel(new BorderLayout());
        jframe.add(basePanel);
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        basePanel.add(headerPanel, BorderLayout.NORTH);
        JLabel stage = new JLabel(stageString);
        stage.setHorizontalAlignment(SwingConstants.CENTER);
        stage.setBorder(new EmptyBorder(5,0,10,0));
        headerPanel.add(stage);

        JPanel playerPanel = new JPanel(new GridLayout(1, 3));
        headerPanel.add(playerPanel);
        String player1 = "Spelare 1";
        String player2 = "Spelare 2";
        if (player1or2.equals("1")){
            player1 = player1 + " (Du)";
        }
        else if (player1or2.equals("2")){
            player2 = player2 + " (Du)";
        }
        JLabel player1Header = new JLabel(player1);
        player1Header.setHorizontalAlignment(SwingConstants.CENTER);
        playerPanel.add(player1Header);
        JLabel scoreHeader = new JLabel(countScore(player1Score) + " - " + countScore(player2Score));
        scoreHeader.setHorizontalAlignment(SwingConstants.CENTER);
        playerPanel.add(scoreHeader);
        JLabel player2Header = new JLabel(player2);
        player2Header.setHorizontalAlignment(SwingConstants.CENTER);
        playerPanel.add(player2Header);

        JPanel scorePanel = new JPanel(new GridLayout(player1Score.length, 3));
        for (int i = 0; i < player1Score.length; i++) {
            loadScoreboardButtons(scorePanel, player1Score, i);
            JLabel round = new JLabel(String.valueOf(i + 1));
            round.setPreferredSize(new Dimension(5, 5));
            round.setHorizontalAlignment(SwingConstants.CENTER);
            scorePanel.add(round);
            loadScoreboardButtons(scorePanel, player2Score, i);
        }
        basePanel.revalidate();
        basePanel.repaint();
        basePanel.add(scorePanel, BorderLayout.CENTER);

        final boolean[] loop = {false};
        if(stageString.equals("Din tur att spela")){
            JPanel jpButtons = new JPanel(new GridLayout(1,2));
            JButton playButton = new JButton("Spela");
            playButton.setFocusPainted(false);
            loop[0] = true;
            playButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    loop[0] = false;
                }
            });
            JButton giveUp = new JButton("Ge upp");
            giveUp.setFocusPainted(false);
            loop[0] = true;
            giveUp.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(null, "Är du säker?");
                    jframe.dispatchEvent(new WindowEvent(jframe, WindowEvent.WINDOW_CLOSING));
                }
            });
            basePanel.add(jpButtons, BorderLayout.SOUTH);
            jpButtons.add(playButton);
            jpButtons.add(giveUp);
        }
        jframe.setVisible(true);
        jframe.revalidate();
        jframe.repaint();
        while(loop[0]){
            Thread.sleep(10);
        }
    }
    public void loadScoreboardButtons(JPanel scorePanel, Boolean[][] playerScore, int i){
        JPanel player1Round = new JPanel(new GridLayout(1, playerScore[i].length));
        for (int j = 0; j < playerScore[i].length; j++) {
            JButton score = new JButton();
            if (playerScore[i][j] == null) {
                score.setBackground(Color.gray);
            } else if (playerScore[i][j]) {
                score.setBackground(Color.GREEN);
            } else {
                score.setBackground(Color.RED);
            }
            player1Round.add(score);
        }
        scorePanel.add(player1Round);
    }

    //Counts a players total score
    public int countScore(Boolean[][] playerScore) {
        int score = 0;
        for (Boolean[] booleans : playerScore) {
            for (Boolean aBoolean : booleans) {
                try {
                    if (aBoolean) {
                        score++;
                    }
                } catch (NullPointerException e) {
                    return score;
                }
            }
        }
        return score;
    }
    public void startMenu(){

        jframe.getContentPane().removeAll();

        JPanel basePanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        JPanel emptyPanel = new JPanel();
        JPanel emptyPanel2 = new JPanel();
        JLabel titelText = new JLabel("Välkommen till Quizkampen!");
        JTextArea messageWindow = new JTextArea();
        JButton startButton = new JButton("Starta spel");

        jframe.add(basePanel);
        emptyPanel.setBackground(Color.blue);
        emptyPanel2.setBackground(Color.blue);
        buttonPanel.setBackground(Color.blue);
        basePanel.setBackground(Color.blue);
        jframe.add(emptyPanel, BorderLayout.WEST);
        jframe.add(emptyPanel2, BorderLayout.EAST);
        basePanel.add(messageWindow, BorderLayout.CENTER);
        messageWindow.setText("Väntar på motståndare");
        basePanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        titelText.setBorder(BorderFactory.createEmptyBorder( 0, 20, 30, 20));
        basePanel.add(titelText, BorderLayout.NORTH);
        titelText.setHorizontalAlignment(SwingConstants.CENTER);
        titelText.setForeground(Color.WHITE);
        titelText.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
        jframe.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(startButton);
        startButton.setSize(10, 10);

        /*startButton.addActionListener(e -> {
            if(e.getSource() == startButton){
                QuizkampenClient.startMode = false;
                System.out.println("Knapp tryckt");
                QuizkampenClient.firstRound = true;
                System.out.println(QuizkampenClient.startMode + " " + QuizkampenClient.firstRound);
            }
        });*/
        jframe.setVisible(true);
        jframe.revalidate();
        jframe.repaint();

    }

    public static void main(String[] args) throws InterruptedException {
        Interface i = new Interface();
        i.startMenu();
    }
}
