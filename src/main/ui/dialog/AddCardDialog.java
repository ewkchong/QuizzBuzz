package ui.dialog;

import model.Card;
import model.Deck;
import ui.CardListMenu;
import ui.utilities.QuizAppUtilities;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddCardDialog extends CardDialog {

    public AddCardDialog(JFrame frame, Deck deck) {
        super(frame, true);
        this.deck = deck;
        addComponents();
    }

    public AddCardDialog(JFrame frame, Deck deck, TableModel tableModel) {
        super(frame, true);
        buttonText = "Add Card";
        this.deck = deck;
        this.tableModel = tableModel;
        addComponents();
    }

    @Override
    protected void handleAction() {
        String front = frontTextField.getText();
        String back = backTextField.getText();
        String tags = tagTextField.getText();

        ArrayList<String> tagsArrayList = QuizAppUtilities.stringToArrayList(tags);

        Card c = new Card(front, back, tagsArrayList);
        deck.addCard(c);

        if (tableModel != null) {
            String[] data = {String.valueOf(c.hashCode()), c.getFront(), c.getBack(), c.getTags().toString()};
            CardListMenu.CardTableModel model = (CardListMenu.CardTableModel) tableModel;
            model.addRow(data);
            model.fireTableDataChanged();
        }

        dispose();
    }
}