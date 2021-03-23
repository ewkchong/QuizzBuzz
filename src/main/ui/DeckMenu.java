package ui;

import model.Deck;

import javax.swing.*;
import java.awt.*;

public class DeckMenu extends JPanel {
    private Deck deck;

    public DeckMenu(Deck d) {
        deck = d;
        JButton button = new JButton(d.getTitle());
        add(button);
    }
}
