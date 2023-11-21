package Client;

import javax.swing.*;
import java.awt.*;

public class SomeonesTurnResultGUI extends JFrame {
    JPanel jp = new JPanel(new BorderLayout());
    JPanel northPanel = new JPanel(new GridLayout());
    JPanel centerPanel = new JPanel(new GridLayout(1,3));
    JPanel centerPanel2 = new JPanel(new GridLayout(1,3));
    JPanel southPanel = new JPanel(new GridLayout(2,2));
    JButton yourTurn = new JButton("Din tur");
    JButton theirTurn = new JButton("Deras tur");
    JButton p1 = new JButton("Spelare 1");
    JButton p2 = new JButton("Spelare 2");
    JButton points = new JButton("X-Y");
    JButton points1 = new JButton("X-Y");
    JButton points2 = new JButton("X-Y");
    JButton points3 = new JButton("X-Y");
    JButton x1 = new JButton("");
    JButton x2 = new JButton("");
    JButton x3 = new JButton("");
    JButton menu = new JButton("Huvudmeny");

    public SomeonesTurnResultGUI() {
        this.add(jp);
        jp.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        jp.add(northPanel, BorderLayout.NORTH);
        jp.add(centerPanel, BorderLayout.CENTER);
        jp.add(centerPanel2, BorderLayout.CENTER);
        jp.add(southPanel, BorderLayout.SOUTH);
        northPanel.add(yourTurn);
        centerPanel.add(p1);
        centerPanel.add(points);
        centerPanel.add(p2);

        pack();
//        setSize(350, 300);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SomeonesTurnResultGUI str = new SomeonesTurnResultGUI();
    }
}
