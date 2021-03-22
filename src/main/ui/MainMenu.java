package ui;

import model.Deck;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainMenu extends JPanel {
    private static final String createString = "Create";

    public MainMenu(ArrayList<Deck> decks) {
        setPreferredSize(new Dimension(1024, 768));
        setLayout(new BorderLayout(5,5));
        createList(decks);
    }

    private void createList(ArrayList<Deck> decks) {
        DefaultListModel listModel = new DefaultListModel();
        for (Deck d: decks) {
            listModel.addElement(d.getTitle());
        }

        JList list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        list.setSelectedIndex(0);
        list.setFixedCellHeight(60);
        JScrollPane listScrollPane = new JScrollPane(list);

        JButton createButton = new JButton(createString);
        createButton.setEnabled(false);

        JTextField deckName = new JTextField(30);

        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new BoxLayout(bottomRow, BoxLayout.LINE_AXIS));
        bottomRow.add(deckName);
        bottomRow.add(Box.createHorizontalStrut(10));
        bottomRow.add(createButton);
        bottomRow.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        add(listScrollPane, BorderLayout.CENTER);
        add(new DeckMenu(), BorderLayout.LINE_END);
        add(bottomRow, BorderLayout.PAGE_END);
    }
}
