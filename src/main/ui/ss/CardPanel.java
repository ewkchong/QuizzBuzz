package ui.ss;

import model.Card;
import ui.DeckMenu;
import ui.QuizApp;
import ui.utilities.QuizAppUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Panel that represents a flash card with a front and back
public class CardPanel extends JPanel {
    QuizApp app;            // parent application
    Card card;              // main card
    StudySession ss;        // panel containing card text
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
        showAnswer.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 18));
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
        easyButton.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 18));
        buttonPanel.add(easyButton, c);

        c.gridx = 1;
        JButton goodButton = new JButton("Good");
        goodButton.addActionListener(new GoodListener());
        goodButton.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 18));
        buttonPanel.add(goodButton, c);

        c.gridx = 2;
        JButton hardButton = new JButton("Hard");
        hardButton.addActionListener(new HardListener());
        hardButton.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 18));
        buttonPanel.add(hardButton, c);
    }

    // MODIFIES: this
    // EFFECTS: changes the GUI back to the deck menu
    private void returnToMenu() {
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(new DeckMenu(app, ss.getDeck()));
        parentFrame.revalidate();
        parentFrame.repaint();
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

    // MODIFIES: this
    // EFFECTS: processes user input for difficulty (1, 2 or 3)
    //          returns GUI to deck menu
    private void processDifficulty(int diff) {
        if (index + 1 >= size) {
            card.processReview(diff);
            returnToMenu();
            if (diff > 1) {
                ss.incrementCorrectReviews();
            }
            ss.showPerformanceDialog();
        } else {
            card.processReview(diff);
            CardLayout cl = (CardLayout) ss.getLayout();
            cl.next(ss);
            if (diff > 1) {
                ss.incrementCorrectReviews();
            }
        }
    }

    // Listens for "Easy" button press
    private class EasyListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: goes back to menu if out of cards, otherwise continues
        @Override
        public void actionPerformed(ActionEvent e) {
            processDifficulty(3);
        }
    }

    // Listens for "Good" button press
    private class GoodListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: goes back to menu if out of cards, otherwise continues
        @Override
        public void actionPerformed(ActionEvent e) {
            processDifficulty(2);
        }
    }

    // Listens to "Hard" button press
    private class HardListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: goes back to menu if out of cards, otherwise continues
        @Override
        public void actionPerformed(ActionEvent e) {
            processDifficulty(1);
        }
    }
}

