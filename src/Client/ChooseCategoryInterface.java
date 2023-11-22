package Client;

import DatabaseQuestion.QuestionObject;
import DatabaseQuestion.ReadFromFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class ChooseCategoryInterface extends JFrame {

    JFrame jframe = new JFrame();

    ChooseCategoryInterface(){
        jframe.setSize(350, 300);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jframe.setLocationRelativeTo(null);
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
                    }
                });
                categoryPanel.add(buttons[i]);
            }

            titleLable.setFont(new Font("defaultFont", Font.PLAIN, 18));
            titelPanel.add(titleLable);
            jframe.setVisible(true);
        }
        jframe.getContentPane().removeAll();
        return categoryTemp[0];

    }

    public boolean[] loadQuestionRound(ArrayList<QuestionObject> questionRound) throws InterruptedException {
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
                int finalJ = j;
                options[j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        answerTemp[0] = options[finalJ].getText();
                        System.out.println(answerTemp[0]);
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
        jframe.getContentPane().removeAll();
        return results;
    }

    public static void main(String[] args) throws InterruptedException {
        ReadFromFile fileReader = new ReadFromFile("src/DatabaseQuestion/QuestionFile.txt");
        ChooseCategoryInterface client = new ChooseCategoryInterface();
        String categoryTemp = client.loadChooseCategory();
        boolean[] results = client.loadQuestionRound(fileReader.getQuestionCategoryArrayList(categoryTemp));
        String categoryTemp2 = client.loadChooseCategory();
        boolean[] results2 = client.loadQuestionRound(fileReader.getQuestionCategoryArrayList(categoryTemp2));
        System.out.println(Arrays.toString(results));
        System.out.println(Arrays.toString(results2));
    }
}
