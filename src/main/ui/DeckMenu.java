package ui;

import model.Deck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeckMenu extends JPanel {
    private Deck deck;
    private MainMenu mainMenu;

    public DeckMenu(MainMenu mainMenu, Deck d) {
        this.mainMenu = mainMenu;
        deck = d;
        JButton button = new JButton(d.getTitle());
        button.addActionListener(new BackListener());
        add(button);
    }

    class BackListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Container frameContent = mainMenu.getParentFrame().getContentPane();

            frameContent.removeAll();
            frameContent.add(mainMenu);
            frameContent.revalidate();
            frameContent.repaint();
        }
    }
}
