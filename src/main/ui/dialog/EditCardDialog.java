package ui.dialog;

import model.Card;
import model.Deck;
import ui.utilities.QuizAppUtilities;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

public class EditCardDialog extends CardDialog {
    Card card;
    int selectedRow;

    public EditCardDialog(JFrame frame, Deck deck, TableModel tableModel, Card c, int selectedRow) {
        super(frame, true);
        card = c;
        this.selectedRow = selectedRow;
        this.deck = deck;
        this.tableModel = tableModel;
        addComponents();
    }

    private String createTagString() {
        String tagString = card.getTags().toString();
        StringBuilder sb = new StringBuilder(tagString);
        sb.deleteCharAt(tagString.length() - 1);
        sb.deleteCharAt(0);

        return sb.toString();
    }

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

        dispose();
    }
}
