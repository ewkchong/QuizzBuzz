package ui;

import model.Card;
import model.Deck;
import ui.dialog.AddCardDialog;
import ui.dialog.EditCardDialog;
import ui.utilities.QuizAppUtilities;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardListMenu extends JPanel {
    JPanel mainMenu;
    JFrame parentFrame;
    JScrollPane scrollPane;
    String[][] cardListData;
    String[] columns;
    JTable table;
    JPanel buttonPanel;
    JButton editButton;
    JButton deleteButton;
    int selectedRowIndex;
    Deck deck;

    public CardListMenu(Deck d, MainMenu mainMenu) {
        this.parentFrame = mainMenu.getParentFrame();
        this.mainMenu = mainMenu;
        deck = d;
        cardListData = initializeData(d);
        columns = initializeColumnTitle();
        buttonPanel = addButtonPanel();
        addComponents();
    }

    private void addComponents() {
        setLayout(new BorderLayout());
        table = initializeTable(cardListData);
        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.LINE_END);
    }

    private JTable initializeTable(String[][] data) {
        JTable tempTable = new JTable();
        tempTable.setModel(new CardTableModel(data, columns));
        tempTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tempTable.setRowHeight(40);
        tempTable.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 16));
        tempTable.getTableHeader().setFont(new Font(QuizAppUtilities.UI_FONT, Font.BOLD, 17));
        tempTable.getSelectionModel().addListSelectionListener(new EnableButtonListener());

        return tempTable;
    }

    private JPanel addButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.ipady = 90;
        c.ipadx = 120;
        JButton addButton = new JButton("Add a Card");
        addButton.addActionListener(new AddCardListener());
        formatButton(addButton);
        panel.add(addButton, c);

        initializeEditButton(panel, c);

        initializeDeleteButton(panel, c);

        initializeReturnButton(panel, c);

        return panel;
    }

    private void initializeReturnButton(JPanel panel, GridBagConstraints c) {
        c.gridy = 3;
        JButton returnButton = new JButton("Return to Menu");
        formatButton(returnButton);
        returnButton.addActionListener(new ReturnListener());
        panel.add(returnButton, c);
    }

    private void initializeDeleteButton(JPanel panel, GridBagConstraints c) {
        c.gridy = 2;
        deleteButton = new JButton("Delete Card");
        formatButton(deleteButton);
        deleteButton.addActionListener(new DeleteListener());
        deleteButton.setEnabled(false);
        panel.add(deleteButton, c);
    }

    private void initializeEditButton(JPanel panel, GridBagConstraints c) {
        c.gridy = 1;
        editButton = new JButton("Edit Card");
        formatButton(editButton);
        editButton.addActionListener(new EditListener());
        editButton.setEnabled(false);
        panel.add(editButton, c);
    }

    private void formatButton(JButton button) {
        button.setPreferredSize(new Dimension(90, 40));
        button.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 14));
    }

    private String[] initializeColumnTitle() {
        return new String[]{"Card No.",
                            "Front",
                            "Back",
                            "Tags"};
    }

    public String[][] initializeData(Deck d) {
        int i = 0;
        String[][] cardListData = new String[d.getCardList().size()][4];
        for (Card c: d.getCardList()) {
            cardListData[i][0] = String.valueOf(i);
            cardListData[i][1] = c.getFront();
            cardListData[i][2] = c.getBack();
            cardListData[i][3] = c.getTags().toString();
            i++;
        }
        return cardListData;
    }

    class EnableButtonListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            editButton.setEnabled(true);
            deleteButton.setEnabled(true);

            selectedRowIndex = table.getSelectedRow();
        }
    }

    public class CardTableModel extends DefaultTableModel {

        public CardTableModel(String[][] data, String[] columns) {
            super(data, columns);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }

    class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            deck.getCardList().remove(selectedRowIndex);
            CardTableModel model = (CardTableModel) table.getModel();
            model.removeRow(selectedRowIndex);
            model.fireTableRowsDeleted(selectedRowIndex,selectedRowIndex);
        }
    }

    private class ReturnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            parentFrame.setContentPane(new DeckMenu((MainMenu) mainMenu, deck));
            parentFrame.revalidate();
            parentFrame.repaint();
        }
    }

    private class AddCardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddCardDialog addCardDialog = new AddCardDialog(parentFrame, deck, table.getModel());
            addCardDialog.pack();
            addCardDialog.setVisible(true);
        }
    }

    private class EditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Card c = deck.getCardList().get(selectedRowIndex);
            EditCardDialog editCardDialog = new EditCardDialog(parentFrame,
                    deck,
                    table.getModel(),
                    c,
                    selectedRowIndex);
            editCardDialog.pack();
            editCardDialog.setVisible(true);
        }
    }
}
