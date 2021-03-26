package ui;

import model.Card;
import model.Deck;
import ui.utilities.QuizAppUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeckMenu extends JPanel {
    private Deck deck;
    private MainMenu mainMenu;
    private JLabel title;

    public DeckMenu(MainMenu mainMenu, Deck d) {
        this.mainMenu = mainMenu;
        deck = d;

        addComponents();
        title = addTitlePanel();
    }

    private void addComponents() {
        setLayout(new BorderLayout());
        addTitlePanel();
        addButtonPanel();
    }

    private void addButtonPanel() {
        GridBagConstraints c = new GridBagConstraints();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        c.weighty = 1.0;
        c.weightx = 1.0;
        c.ipady = Short.MAX_VALUE;
        c.ipadx = Short.MAX_VALUE;
        addButtonsToPanel(c, buttonPanel);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void addButtonsToPanel(GridBagConstraints c, JPanel buttonPanel) {
        JButton bt1 = makeButton("Study!", null);
        buttonPanel.add(bt1, c);

        c.gridx = 1;
        JButton bt2 = makeButton("View all Cards", new ViewCardsListener());
        buttonPanel.add(bt2, c);

        c.gridx = 2;
        JButton bt3 = makeButton("Add a Card", null);
        buttonPanel.add(bt3, c);

        c.gridx = 0;
        c.gridy = 1;
        JButton bt4 = makeButton("Rename Deck", new RenameListener());
        buttonPanel.add(bt4, c);

        c.gridx = 1;
        JButton bt5 = makeButton("Delete Deck", new DeleteListener());
        buttonPanel.add(bt5, c);

        c.gridx = 2;
        JButton bt6 = makeButton("Back to Menu", new BackListener());
        buttonPanel.add(bt6, c);
    }

    private JButton makeButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 14));

        if (listener != null) {
            button.addActionListener(listener);
        }

        return button;
    }

    private JLabel addTitlePanel() {
        JLabel title = new JLabel(deck.getTitle(), SwingConstants.CENTER);
        title.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 48));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15,0,15,0));
        titlePanel.add(title, BorderLayout.PAGE_START);

        add(titlePanel, BorderLayout.PAGE_START);

        return title;
    }

    private void backToMenu() {
        Container frameContent = mainMenu.getParentFrame().getContentPane();

        frameContent.removeAll();
        frameContent.add(mainMenu);
        frameContent.revalidate();
        frameContent.repaint();
    }

    class BackListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            backToMenu();
        }
    }

    class RenameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String s = QuizAppUtilities.createRenameDialog(deck, mainMenu.getParentFrame());
            if (s != null && !(s.equals(""))) {
                deck.renameDeck(s);
                title.setText(s);
                mainMenu.getParentFrame().revalidate();
                mainMenu.getParentFrame().repaint();
            }
        }
    }

    class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = QuizAppUtilities.createDeleteDialog(deck, mainMenu.getParentFrame());
            if (confirm == 0) {
                backToMenu();
                mainMenu.removeDeck(deck);
            }
        }
    }

    class ViewCardsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Container frameContent = mainMenu.getParentFrame().getContentPane();
            frameContent.removeAll();
            frameContent.add(new CardListMenu(deck));
            frameContent.revalidate();
            frameContent.repaint();
        }
    }
}
