package ui;

import model.Card;
import model.Deck;
import ui.utilities.QuizAppUtilities;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddCardDialog extends JDialog {
    Deck deck;
    JTextField frontTextField;
    JTextField backTextField;
    JTextField tagTextField;
    TableModel tableModel;

    public AddCardDialog(JFrame frame, Deck deck) {
        super(frame, true);
        this.deck = deck;
        addComponents();
    }

    public AddCardDialog(JFrame frame, Deck deck, TableModel tableModel) {
        super(frame, true);
        this.deck = deck;
        this.tableModel = tableModel;
        addComponents();
    }

    private void addComponents() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        addFront(contentPanel, c);
        addBack(contentPanel, c);
        addTag(contentPanel, c);

        addButtons(contentPanel, c);

        add(contentPanel);
    }

    private void addButtons(JPanel contentPanel, GridBagConstraints c) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton addButton = new JButton("Add Card");
        addButton.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 16));
        addButton.addActionListener(new AddListener());
        buttonPanel.add(addButton, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 20, 0, 0);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new CancelListener());
        cancelButton.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 16));
        buttonPanel.add(cancelButton, gbc);

        c.gridy = 6;
        contentPanel.add(buttonPanel, c);
    }

    private void addTag(JPanel contentPanel, GridBagConstraints c) {
        JLabel tagLabel = makeLabel("Tags");
        tagTextField = makeTextField(20);
        c.gridy = 4;
        contentPanel.add(tagLabel, c);
        c.gridy = 5;
        contentPanel.add(tagTextField, c);
    }

    private void addBack(JPanel contentPanel, GridBagConstraints c) {
        JLabel backLabel = makeLabel("Back of Card");
        backTextField = makeTextField(20);
        c.gridy = 2;
        contentPanel.add(backLabel, c);
        c.gridy = 3;
        contentPanel.add(backTextField, c);
    }

    private void addFront(JPanel contentPanel, GridBagConstraints c) {
        JLabel frontLabel = makeLabel("Front of Card");
        frontTextField = makeTextField(20);
        c.gridy = 0;
        contentPanel.add(frontLabel, c);
        c.gridy = 1;
        contentPanel.add(frontTextField, c);
    }

    private JTextField makeTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 24));

        return textField;
    }

    private JLabel makeLabel(String s) {
        JLabel label = new JLabel(s);
        label.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 16));

        return label;
    }

    class CancelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    class AddListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String front = frontTextField.getText();
            String back = backTextField.getText();
            String tags = tagTextField.getText();

            String[] tagsArray = tags.split("\\s*,\\s*");
            List<String> tagsList = Arrays.asList(tagsArray);
            ArrayList<String> tagsArrayList = new ArrayList<>(tagsList);

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
}