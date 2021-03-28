package ui;

import model.Card;
import model.Deck;
import ui.utilities.QuizAppUtilities;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class CardListMenu extends JPanel {
    String[][] cardListData;
    String[] columns;
    JTable table;
    JPanel buttonPanel;

    public CardListMenu(Deck d) {
        cardListData = initializeData(d);
        columns = initializeColumnTitle();
        buttonPanel = addButtonPanel();
        addComponents();
    }

    private void addComponents() {
        setLayout(new BorderLayout());

        table = new JTable(cardListData, columns);
        table.setModel(new CardTableModel());
        table.setRowHeight(40);
        table.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 18));
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.LINE_END);
    }

    private JPanel addButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.ipady = 90;
        c.ipadx = 120;
        JButton addButton = new JButton("Add a Card");
        setButtonSize(addButton, 150, 70);
        panel.add(addButton, c);

        c.gridy = 1;
        JButton editButton = new JButton("Edit Card");
        setButtonSize(editButton, 150, 70);
        panel.add(editButton, c);

        c.gridy = 2;
        JButton deleteButton = new JButton("Delete Card");
        setButtonSize(deleteButton, 150, 70);
        panel.add(deleteButton, c);

        return panel;
    }

    private void setButtonSize(JButton button, int width, int height) {
        button.setPreferredSize(new Dimension(width, height));
    }

    private String[] initializeColumnTitle() {
        return new String[]{"ID",
                            "Front",
                            "Back",
                            "Tags"};
    }

    private String[][] initializeData(Deck d) {
        int i = 0;
        String[][] cardListData = new String[d.getCardList().size()][4];
        for (Card c: d.getCardList()) {
            cardListData[i][0] = Integer.toString(i);
            cardListData[i][1] = c.getFront();
            cardListData[i][2] = c.getBack();
            cardListData[i][3] = c.getTags().toString();
            i++;
        }
        return cardListData;
    }

    class CardTableModel extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return cardListData.length;
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public String getColumnName(int col) {
            return columns[col];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return cardListData[rowIndex][columnIndex];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }
}
