package ui;

import model.Deck;
import ui.QuizApp;
import ui.exceptions.EmptyStudyListException;
import ui.ss.AllStudySession;
import ui.ss.NormalStudySession;
import ui.ss.TagStudySession;
import ui.utilities.QuizAppUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Menu that allows choice of type of study session
public class ChooseStudySessionMenu extends JPanel {
    QuizApp app;                // parent application
    JFrame parentFrame;         // containing frame
    Deck deck;                  // deck for which this study session is for

    // EFFECTS: creates a new menu to choose study session for given list of cards
    public ChooseStudySessionMenu(QuizApp app, Deck d) {
        this.app = app;
        deck = d;
        this.parentFrame = app.getFrame();
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
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.ipadx = Short.MAX_VALUE;
        c.ipady = 600;

        JButton allButton = makeButton("Study (ignore schedule)", new AllStudyListener());
        buttonPanel.add(allButton, c);

        c.gridx = 1;
        JButton studyButton = makeButton("Study", new RegularStudyListener());
        studyButton.setFont(new Font(QuizAppUtilities.UI_FONT, Font.BOLD, 24));
        buttonPanel.add(studyButton, c);

        c.gridx = 2;
        JButton tagStudyButton = makeButton("Study by Tag", new TagStudyListener());
        buttonPanel.add(tagStudyButton, c);

        c.ipady = 300;
        c.gridx = 0;
        c.gridwidth = 3;
        c.gridy = 1;
        JButton returnButton = makeButton("Return to Menu", new ReturnListener());
        buttonPanel.add(returnButton, c);

        add(buttonPanel, BorderLayout.CENTER);
    }

    // EFFECTS: creates a button with the given text, adds given listener
    //          returns button
    private JButton makeButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 22));
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
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 15, 0));
        titlePanel.add(title, BorderLayout.PAGE_START);

        add(titlePanel, BorderLayout.PAGE_START);
    }

    // Listens for "Study" button press
    private class RegularStudyListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: changes frame content to NormalStudySession
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                parentFrame.setContentPane(new NormalStudySession(deck, parentFrame, app));
                parentFrame.getContentPane().revalidate();
                parentFrame.getContentPane().repaint();
            } catch (EmptyStudyListException f) {
                QuizAppUtilities.showNoCardsWarning(parentFrame);
            }
        }
    }

    // Listens for "Study by Tag" button press
    private class TagStudyListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: changes frame content to TagStudySession
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String s = (String) JOptionPane.showInputDialog(
                        parentFrame,
                        "Enter Tags",
                        "Tags to Study",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "");
                if (s != null) {
                    ArrayList<String> tags = QuizAppUtilities.stringToArrayList(s.toLowerCase());
                    parentFrame.setContentPane(new TagStudySession(deck, tags, app));
                    parentFrame.getContentPane().revalidate();
                    parentFrame.getContentPane().repaint();
                }
            } catch (EmptyStudyListException f) {
                QuizAppUtilities.showNoCardsWarning(parentFrame);
            }
        }
    }

    // Listens for "Study (ignore schedule)" button press
    private class AllStudyListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: changes frame content to AllStudySession
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                parentFrame.setContentPane(new AllStudySession(deck, parentFrame, app));
                parentFrame.getContentPane().revalidate();
                parentFrame.getContentPane().repaint();
            } catch (EmptyStudyListException f) {
                QuizAppUtilities.showNoCardsWarning(parentFrame);
            }
        }
    }

    // Listens for "Return to Menu" button press
    private class ReturnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            app.returnToDeckMenu(deck);
        }
    }
}
