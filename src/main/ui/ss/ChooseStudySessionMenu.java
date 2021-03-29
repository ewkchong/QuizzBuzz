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

public class ChooseStudySessionMenu extends JPanel {
    ArrayList<Card> cardList;
    JFrame parentFrame;
    DeckMenu deckMenu;

    public ChooseStudySessionMenu(ArrayList<Card> cardList, JFrame parentFrame, DeckMenu d) {
        deckMenu = d;
        this.cardList = cardList;
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        addComponents();
    }

    private void addComponents() {
        addTitle();
        addButtons();
    }

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

    private JButton makeButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 22));
        button.setPreferredSize(new Dimension(300, 600));
        button.addActionListener(listener);

        return button;
    }

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
        @Override
        public void actionPerformed(ActionEvent e) {
            parentFrame.setContentPane(new NormalStudySession(cardList, parentFrame, deckMenu));
            parentFrame.getContentPane().revalidate();
            parentFrame.getContentPane().repaint();
        }
    }

    private class TagStudyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            parentFrame.setContentPane(new TagStudySession(cardList, parentFrame, new ArrayList<>(), deckMenu));
            parentFrame.getContentPane().revalidate();
            parentFrame.getContentPane().repaint();
        }
    }

    private class AllStudyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            parentFrame.setContentPane(new AllStudySession(cardList, parentFrame, deckMenu));
            parentFrame.getContentPane().revalidate();
            parentFrame.getContentPane().repaint();
        }
    }
}
