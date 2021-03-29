package ui.ss;

import model.Card;
import ui.DeckMenu;
import ui.MainMenu;
import ui.utilities.QuizAppUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardPanel extends JPanel {
    Card card;
    JPanel cardPanel;
    JLabel backText;
    JPanel buttonPanel;
    JFrame parentFrame;
    DeckMenu deckMenu;
    int index;
    int size;

    public CardPanel(Card c, JPanel cardPanel, int i, int size, JFrame parentFrame, DeckMenu d) {
        deckMenu = d;
        this.parentFrame = parentFrame;
        this.cardPanel = cardPanel;
        card = c;
        index = i;
        this.size = size;
        setLayout(new BorderLayout());
        addComponents();
        addButtonPanel();
    }

    private void addComponents() {
        JPanel frontPanel = new JPanel();
        frontPanel.setLayout(new BoxLayout(frontPanel, BoxLayout.PAGE_AXIS));
        JLabel front = new JLabel(card.getFront());
        front.setFont(new Font(QuizAppUtilities.UI_FONT, Font.BOLD, 36));
        frontPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        frontPanel.add(front);

        backText = new JLabel(card.getBack());
        backText.setFont(new Font(QuizAppUtilities.UI_FONT, Font.BOLD, 26));
        backText.setForeground(new Color(0,0,0,0));
        frontPanel.add(backText);

        add(frontPanel, BorderLayout.CENTER);
    }

    private void addButtonPanel() {

        buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setPreferredSize(new Dimension(Short.MAX_VALUE, 300));
        GridBagConstraints c = new GridBagConstraints();

        c.ipadx = 900;
        c.ipady = 150;
        JButton showAnswer = new JButton("Show Answer");
        showAnswer.addActionListener(new ShowAnswerListener());
        buttonPanel.add(showAnswer, c);

        add(buttonPanel, BorderLayout.PAGE_END);
    }

    private void addDifficultyButtons() {
        GridBagConstraints c = new GridBagConstraints();

        c.ipadx = 300;
        c.ipady = 150;
        JButton easyButton = new JButton("Easy");
        easyButton.addActionListener(new EasyListener());
        buttonPanel.add(easyButton, c);

        c.gridx = 1;
        JButton goodButton = new JButton("Good");
        goodButton.addActionListener(new GoodListener());
        buttonPanel.add(goodButton, c);

        c.gridx = 2;
        JButton hardButton = new JButton("Hard");
        hardButton.addActionListener(new HardListener());
        buttonPanel.add(hardButton, c);
    }

    private class ShowAnswerListener implements ActionListener {
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
        @Override
        public void actionPerformed(ActionEvent e) {
            if (index + 1 >= size) {
                parentFrame.setContentPane(deckMenu);
                parentFrame.getContentPane().revalidate();
                parentFrame.getContentPane().repaint();
            }
            CardLayout cl = (CardLayout) cardPanel.getLayout();
            cl.next(cardPanel);
        }
    }

    private class GoodListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (index + 1 >= size) {
                parentFrame.setContentPane(deckMenu);
                parentFrame.getContentPane().revalidate();
                parentFrame.getContentPane().repaint();
            }
            CardLayout cl = (CardLayout) cardPanel.getLayout();
            cl.next(cardPanel);
        }
    }

    private class HardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (index + 1 >= size) {
                parentFrame.setContentPane(deckMenu);
                parentFrame.getContentPane().revalidate();
                parentFrame.getContentPane().repaint();
            }
            CardLayout cl = (CardLayout) cardPanel.getLayout();
            cl.next(cardPanel);
        }
    }
}

