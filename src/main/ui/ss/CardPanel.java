package ui.ss;

import model.Card;
import ui.MainMenu;
import ui.QuizApp;
import ui.utilities.QuizAppUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Panel that represents a flash card with a front and back
public class CardPanel extends JPanel {
    QuizApp app;
    Card card;              // main card
    StudySession ss;       // panel containing card text
    JLabel backText;        // label containing text of back of flash card
    JPanel buttonPanel;     // panel containing difficulty buttons
    JFrame parentFrame;     // containing frame
    int index;              // "place" in list of cards
    int size;               // length of study list

    // EFFECTS: creates new card panel to show a single card
    public CardPanel(Card c, StudySession ss, int i, int size, QuizApp app) {
        this.app = app;
        this.parentFrame = app.getFrame();
        this.ss = ss;
        card = c;
        index = i;
        this.size = size;
        setLayout(new BorderLayout());
        addComponents();
        addButtonPanel();
    }

    // MODIFIES: this
    // EFFECTS: creates components and adds them to panel
    private void addComponents() {
        JPanel frontPanel = new JPanel();
        frontPanel.setLayout(new BoxLayout(frontPanel, BoxLayout.PAGE_AXIS));
        JLabel front = new JLabel(card.getFront());
        front.setFont(new Font(QuizAppUtilities.UI_FONT, Font.BOLD, 52));
        frontPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        frontPanel.add(front);

        backText = new JLabel(card.getBack());
        backText.setFont(new Font(QuizAppUtilities.UI_FONT, Font.BOLD, 42));
        backText.setForeground(new Color(0,0,0,0));
        frontPanel.add(backText);

        add(frontPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: creates button panel, adds buttons, and assigns it to field
    private void addButtonPanel() {

        buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1.0;
        c.ipadx = Short.MAX_VALUE;
        c.ipady = 150;
        JButton showAnswer = new JButton("Show Answer");
        showAnswer.addActionListener(new ShowAnswerListener());
        showAnswer.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 24));
        buttonPanel.add(showAnswer, c);

        add(buttonPanel, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: adds difficulty buttons to panel with constraints
    private void addDifficultyButtons() {
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1.0;
        c.ipadx = Short.MAX_VALUE;
        c.ipady = 150;
        JButton easyButton = new JButton("Easy");
        easyButton.addActionListener(new EasyListener());
        easyButton.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 24));
        buttonPanel.add(easyButton, c);

        c.gridx = 1;
        JButton goodButton = new JButton("Good");
        goodButton.addActionListener(new GoodListener());
        goodButton.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 24));
        buttonPanel.add(goodButton, c);

        c.gridx = 2;
        JButton hardButton = new JButton("Hard");
        hardButton.addActionListener(new HardListener());
        hardButton.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 24));
        buttonPanel.add(hardButton, c);
    }

    private class ShowAnswerListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: changes back text to be visible on pressing "Show answer"
        @Override
        public void actionPerformed(ActionEvent e) {
            backText.setForeground(new Color(0,0,0,188));
            buttonPanel.removeAll();
            addDifficultyButtons();
            buttonPanel.revalidate();
            buttonPanel.repaint();
        }
    }


    private class EasyListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: goes back to menu if out of cards, otherwise continues
        @Override
        public void actionPerformed(ActionEvent e) {
            if (index + 1 >= size) {
                card.processReview(3);
                parentFrame.getContentPane().removeAll();
                parentFrame.getContentPane().add(new MainMenu(app));
                parentFrame.revalidate();
                parentFrame.repaint();
                ss.incrementCorrectReviews();
                ss.showPerformanceDialog();
            } else {
                card.processReview(3);
                CardLayout cl = (CardLayout) ss.getLayout();
                cl.next(ss);
                ss.incrementCorrectReviews();
            }
        }
    }


    private class GoodListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: goes back to menu if out of cards, otherwise continues
        @Override
        public void actionPerformed(ActionEvent e) {
            if (index + 1 >= size) {
                card.processReview(2);
                parentFrame.getContentPane().removeAll();
                parentFrame.getContentPane().add(new MainMenu(app));
                parentFrame.revalidate();
                parentFrame.repaint();
                ss.incrementCorrectReviews();
                ss.showPerformanceDialog();
            } else {
                card.processReview(2);
                CardLayout cl = (CardLayout) ss.getLayout();
                cl.next(ss);
                ss.incrementCorrectReviews();
            }
        }
    }

    private class HardListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: goes back to menu if out of cards, otherwise continues
        @Override
        public void actionPerformed(ActionEvent e) {
            if (index + 1 >= size) {
                card.processReview(1);
                parentFrame.getContentPane().removeAll();
                parentFrame.getContentPane().add(new MainMenu(app));
                parentFrame.revalidate();
                parentFrame.repaint();
                ss.showPerformanceDialog();
            } else {
                card.processReview(1);
                CardLayout cl = (CardLayout) ss.getLayout();
                cl.next(ss);
            }
        }
    }
}

