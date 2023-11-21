package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChooseCategoryInterface extends JFrame {

    JFrame jframe = new JFrame();


    ChooseCategoryInterface(){
    }

    public String loadChooseCategory(){
        final String[] categoryTemp = new String[1];
        while(categoryTemp[0] == null) {
            JPanel basePanel = new JPanel(new BorderLayout());
            JPanel titelPanel = new JPanel();
            JPanel categoryPanel = new JPanel(new GridLayout(3, 1));
            JLabel titleLable = new JLabel("Välj Kategori");
            JButton[] buttons = new JButton[3];
            jframe.add(basePanel);
            basePanel.add(titelPanel, BorderLayout.NORTH);
            basePanel.add(categoryPanel, BorderLayout.SOUTH);

            String[] categories = new String[3];
            categories[0] = "Musik";
            categories[1] = "Historia";
            categories[2] = "Naturvetenskap";

            for (int i = 0; i < buttons.length; i++) {
                buttons[i] = new JButton(categories[i]);
                buttons[i].setPreferredSize(new Dimension(300, 75));
                buttons[i].setFont(new Font("defaultFont", Font.PLAIN, 18));
                int finalI = i;
                buttons[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        categoryTemp[0] = buttons[finalI].getText();
                        jframe.dispose();
                    }
                });
                categoryPanel.add(buttons[i]);
            }

            titleLable.setFont(new Font("defaultFont", Font.PLAIN, 18));
            titelPanel.add(titleLable);

            jframe.setSize(350, 300);
            jframe.setVisible(true);
            jframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
            jframe.setLocationRelativeTo(null);
        }
        return categoryTemp[0];
    }
}
