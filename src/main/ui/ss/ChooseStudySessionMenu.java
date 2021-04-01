package ui.ss;

import model.Card;
import ui.DeckMenu;
import ui.QuizApp;
import ui.utilities.QuizAppUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Menu that allows choice of type of study session
public class ChooseStudySessionMenu extends JPanel {
    ArrayList<Card> cardList;   // unfiltered list of cards to study
    JFrame parentFrame;         // containing frame
    DeckMenu deckMenu;          // previous menu

    // EFFECTS: creates a new menu to choose study session for given list of cards
    public ChooseStudySessionMenu(ArrayList<Card> cardList, JFrame parentFrame, DeckMenu d) {
        deckMenu = d;
        this.cardList = cardList;
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        addComponents();
    }

    // EFFECTS: adds components to panel
    private void addComponents() {
        addTitle();
        addButtons();
    }

    // MODIFIES: this
    // EFFECTS: creates and adds buttons to panel
    private void addButtons() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton allButton = makeButton("Study (ignore schedule)", new AllStudyListener());
        buttonPanel.add(allButton, c);

        c.gridx = 1;
        JButton studyButton = makeButton("Study", new RegularStudyListener());
        studyButton.setFont(new Font(QuizAppUtilities.UI_FONT, Font.BOLD, 24));
        buttonPanel.add(studyButton, c);

        c.gridx = 2;
        JButton tagStudyButton = makeButton("Study by Tag", new TagStudyListener());
        buttonPanel.add(tagStudyButton, c);

        add(buttonPanel, BorderLayout.CENTER);
    }

    // EFFECTS: creates a button with the given text, adds given listener
    //          returns button
    private JButton makeButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 22));
        button.setPreferredSize(new Dimension(300, 600));
        button.addActionListener(listener);

        return button;
    }

    // MODIFIES: this
    // EFFECTS: creates, formats, and adds title to panel
    private void addTitle() {
        JLabel title = new JLabel("Choose Study Method:", SwingConstants.CENTER);
        title.setFont(new Font(QuizAppUtilities.UI_FONT, Font.BOLD, 42));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30,0,15,0));
        titlePanel.add(title, BorderLayout.PAGE_START);

        add(titlePanel, BorderLayout.PAGE_START);
    }

    private class RegularStudyListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: changes frame content to NormalStudySession
        @Override
        public void actionPerformed(ActionEvent e) {
            parentFrame.setContentPane(new NormalStudySession(cardList, parentFrame, deckMenu));
            parentFrame.getContentPane().revalidate();
            parentFrame.getContentPane().repaint();
        }
    }

    private class TagStudyListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: changes frame content to TagStudySession
        @Override
        public void actionPerformed(ActionEvent e) {
            parentFrame.setContentPane(new TagStudySession(cardList, parentFrame, new ArrayList<>(), deckMenu));
            parentFrame.getContentPane().revalidate();
            parentFrame.getContentPane().repaint();
        }
    }

    private class AllStudyListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: changes frame content to AllStudySession
        @Override
        public void actionPerformed(ActionEvent e) {
            parentFrame.setContentPane(new AllStudySession(cardList, parentFrame, deckMenu));
            parentFrame.getContentPane().revalidate();
            parentFrame.getContentPane().repaint();
        }
    }
}
