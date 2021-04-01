package ui.dialog;

import model.Card;
import model.Deck;
import ui.CardListMenu;
import ui.utilities.QuizAppUtilities;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.ArrayList;

// Dialog taking user input for adding a card
public class AddCardDialog extends CardDialog {

    // EFFECTS: creates a dialog that takes user input to add a card;
    public AddCardDialog(JFrame frame, Deck deck) {
        super(frame, true);
        this.deck = deck;
        addComponents();
    }

    // EFFECTS: creates a dialog that takes user input,
    // can modify a table model;
    public AddCardDialog(JFrame frame, Deck deck, TableModel tableModel) {
        super(frame, true);
        buttonText = "Add Card";
        this.deck = deck;
        this.tableModel = tableModel;
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: adds card to deck with given user input as fields
    @Override
    protected void handleAction() {
        String front = frontTextField.getText();
        String back = backTextField.getText();
        String tags = tagTextField.getText();

        ArrayList<String> tagsArrayList = QuizAppUtilities.stringToArrayList(tags);

        Card c = new Card(front, back, tagsArrayList);
        deck.addCard(c);

        ArrayList<Card> cardList = deck.getCardList();

        if (tableModel != null) {
            String[] data = {String.valueOf(cardList.indexOf(c)),
                    c.getFront(),
                    c.getBack(),
                    c.getTags().toString()};
            CardListMenu.CardTableModel model = (CardListMenu.CardTableModel) tableModel;
            model.addRow(data);
            model.fireTableDataChanged();
        }

        dispose();
    }
}