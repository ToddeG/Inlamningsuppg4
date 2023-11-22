package Client;

import javax.swing.*;
import java.awt.*;

public class SomeonesTurnResultGUI extends JFrame {

    JPanel gameGridPanel = new JPanel(new GridLayout(4,1));
    JLabel whoseTurn = new JLabel();
    JPanel playerPanel = new JPanel(new FlowLayout());
    JLabel playerOne = new JLabel("player One");
    JLabel scoreBoard = new JLabel("SCORE");
    JLabel playerTwo = new JLabel("player Two");
    JPanel roundsPanel = new JPanel(new FlowLayout());
    JPanel playerOneGrid = new JPanel(new GridLayout(2,2));
    JButton plOneQOne = new JButton();
    JButton plOneQTwo= new JButton();
    JButton plOneQThree = new JButton();
    JButton plOneQFour = new JButton();
    JPanel scoreboardGrid = new JPanel(new GridLayout(2,1));
    JLabel genreOne = new JLabel("Genre One");
    JLabel genreTwo = new JLabel("Genre two");
    JPanel playerTwoGrid = new JPanel(new GridLayout(2,2));
    JButton plTwoQOne = new JButton();
    JButton plTwoQTwo= new JButton();
    JButton plTwoQThree = new JButton();
    JButton plTwoQFour = new JButton();






    public SomeonesTurnResultGUI() {

        this.add(gameGridPanel);

        gameGridPanel.setBorder(BorderFactory.createEtchedBorder());
        gameGridPanel.add(whoseTurn);
        whoseTurn.setBackground(Color.red);
        whoseTurn.setText("vems tur?");
        whoseTurn.setBorder(BorderFactory.createEtchedBorder());
        whoseTurn.setHorizontalAlignment(JLabel.CENTER);


        gameGridPanel.add(playerPanel);
        playerPanel.setBorder(BorderFactory.createEtchedBorder());
        playerPanel.add(playerOne);
        playerPanel.add(scoreBoard);
        playerPanel.add(playerTwo);

        gameGridPanel.add(roundsPanel);
        roundsPanel.setBorder(BorderFactory.createEtchedBorder());

        roundsPanel.add(playerOneGrid);
        playerOneGrid.setBorder(BorderFactory.createEtchedBorder());
        playerOneGrid.setBackground(Color.BLUE);
        roundsPanel.add(scoreboardGrid);
        scoreboardGrid.setBorder(BorderFactory.createEtchedBorder());
        roundsPanel.add(playerTwoGrid);
        playerTwoGrid.setBorder(BorderFactory.createEtchedBorder());

        playerOneGrid.add(plOneQOne);
        playerOneGrid.add(plOneQTwo);
        playerOneGrid.add(plOneQThree);
        playerOneGrid.add(plOneQFour);

        scoreboardGrid.add(genreOne);
        scoreboardGrid.add(genreTwo);

        playerTwoGrid.add(plTwoQOne);
        playerTwoGrid.add(plTwoQTwo);
        playerTwoGrid.add(plTwoQThree);
        playerTwoGrid.add(plTwoQFour);




        this.setSize(500, 700);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SomeonesTurnResultGUI str = new SomeonesTurnResultGUI();}
}
