package ui.dialog;

import model.Card;
import model.Deck;
import ui.utilities.QuizAppUtilities;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

// Dialog taking user input for editing an existing card's information
public class EditCardDialog extends CardDialog {
    Card card;          // card to be edited
    int selectedRow;    // index of selected row of table model

    // EFFECTS: creates a new dialog for editing given card
    public EditCardDialog(JFrame frame, Deck deck, TableModel tableModel, Card c, int selectedRow) {
        super(frame, true);
        card = c;
        this.selectedRow = selectedRow;
        this.deck = deck;
        this.tableModel = tableModel;
        addComponents();
    }

    // EFFECTS: initializes the tags of a card to a displayable string
    private String createTagString() {
        String tagString = card.getTags().toString();
        StringBuilder sb = new StringBuilder(tagString);
        sb.deleteCharAt(tagString.length() - 1);
        sb.deleteCharAt(0);

        return sb.toString();
    }

    // MODIFIES: this, contentPanel, c
    // EFFECTS: adds label for tag and text field to panel
    @Override
    public void addTag(JPanel contentPanel, GridBagConstraints c) {
        JLabel tagLabel = makeLabel("Tags");
        tagTextField = makeTextField(20);
        tagTextField.setText(createTagString());
        c.gridy = 4;
        contentPanel.add(tagLabel, c);
        c.gridy = 5;
        contentPanel.add(tagTextField, c);
    }

    // MODIFIES: this, contentPanel, c
    // EFFECTS: adds label for back and text field to panel
    @Override
    public void addBack(JPanel contentPanel, GridBagConstraints c) {
        JLabel backLabel = makeLabel("Back of Card");
        backTextField = makeTextField(20);
        backTextField.setText(card.getBack());
        c.gridy = 2;
        contentPanel.add(backLabel, c);
        c.gridy = 3;
        contentPanel.add(backTextField, c);
    }

    // MODIFIES: this, contentPanel, c
    // EFFECTS: adds label for front and text field to panel
    @Override
    public void addFront(JPanel contentPanel, GridBagConstraints c) {
        JLabel frontLabel = makeLabel("Front of Card");
        frontTextField = makeTextField(20);
        frontTextField.setText(card.getFront());
        c.gridy = 0;
        contentPanel.add(frontLabel, c);
        c.gridy = 1;
        contentPanel.add(frontTextField, c);
    }

    // MODIFIES: this
    // EFFECTS: changes card information to user input
    @Override
    protected void handleAction() {
        String front = frontTextField.getText();
        String back = backTextField.getText();
        String tags = tagTextField.getText();

        ArrayList<String> tagsArrayList = QuizAppUtilities.stringToArrayList(tags);

        card.setFront(front);
        card.setBack(back);
        card.setTags(tagsArrayList);

        tableModel.setValueAt(front, selectedRow, 1);
        tableModel.setValueAt(back, selectedRow, 2);
        tableModel.setValueAt(tagsArrayList.toString(), selectedRow, 3);

        parentFrame.setTitle("QuizzBuzz (Unsaved Changes)");

        dispose();
    }
}
