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

// Menu displaying all cards in deck
public class CardListMenu extends JPanel {
    JPanel mainMenu;            // previous menu
    JFrame parentFrame;         // parent containing frame
    JScrollPane scrollPane;     // scroll pane containing table
    String[][] cardListData;    // 2D array of card data
    String[] columns;           // array of column names
    JTable table;               // table of cards
    JPanel buttonPanel;         // panel containing all buttons
    JButton editButton;         // button allowing user to edit cards
    JButton deleteButton;       // button allowing user to delete cards
    int selectedRowIndex;       // index of selected row in table model list
    Deck deck;                  // deck containing this card list

    // EFFECTS: creates new CardListMenu with default initial data
    public CardListMenu(Deck d, MainMenu mainMenu) {
        this.parentFrame = mainMenu.getParentFrame();
        this.mainMenu = mainMenu;
        deck = d;
        cardListData = initializeData(d);
        columns = initializeColumnTitle();
        buttonPanel = addButtonPanel();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: sets layout of panel, initializes data, and adds components to panel
    private void addComponents() {
        setLayout(new BorderLayout());
        table = initializeTable(cardListData);
        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.LINE_END);
    }

    // EFFECTS: creates and returns table representing list of cards
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

    // EFFECTS: returns a panel containing buttons for editing card list
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

    // EFFECTS: adds a button to a sub-panel for returning to menu
    private void initializeReturnButton(JPanel panel, GridBagConstraints c) {
        c.gridy = 3;
        JButton returnButton = new JButton("Return to Menu");
        formatButton(returnButton);
        returnButton.addActionListener(new ReturnListener());
        panel.add(returnButton, c);
    }

    // EFFECTS: adds a button to a sub-panel for deleting a deck
    private void initializeDeleteButton(JPanel panel, GridBagConstraints c) {
        c.gridy = 2;
        deleteButton = new JButton("Delete Card");
        formatButton(deleteButton);
        deleteButton.addActionListener(new DeleteListener());
        deleteButton.setEnabled(false);
        panel.add(deleteButton, c);
    }

    // EFFECTS: adds a button to a sub-panel for editing a deck
    private void initializeEditButton(JPanel panel, GridBagConstraints c) {
        c.gridy = 1;
        editButton = new JButton("Edit Card");
        formatButton(editButton);
        editButton.addActionListener(new EditListener());
        editButton.setEnabled(false);
        panel.add(editButton, c);
    }

    // EFFECTS: formats the given button to a certain size and font
    private void formatButton(JButton button) {
        button.setPreferredSize(new Dimension(90, 40));
        button.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 14));
    }

    // EFFECTS: return array of strings containing column title
    private String[] initializeColumnTitle() {
        return new String[]{"Card No.",
                            "Front",
                            "Back",
                            "Tags"};
    }

    // EFFECTS: creates and returns a 2D array containing field data of card list
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

        // MODIFIES: this
        // EFFECTS: enables buttons when an item is selected
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

        // EFFECTS: renders all cells uneditable
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }

    class DeleteListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: removes the selected card from the deck, updates table accordingly
        @Override
        public void actionPerformed(ActionEvent e) {
            deck.getCardList().remove(selectedRowIndex);
            CardTableModel model = (CardTableModel) table.getModel();
            model.removeRow(selectedRowIndex);
            model.fireTableRowsDeleted(selectedRowIndex,selectedRowIndex);
        }
    }

    private class ReturnListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: sets content of frame to deck menu (returns to previous menu)
        @Override
        public void actionPerformed(ActionEvent e) {
            parentFrame.setContentPane(new DeckMenu((MainMenu) mainMenu, deck));
            parentFrame.revalidate();
            parentFrame.repaint();
        }
    }

    private class AddCardListener implements ActionListener {

        // EFFECTS: shows a dialog allowing for user input to add cards to list
        @Override
        public void actionPerformed(ActionEvent e) {
            AddCardDialog addCardDialog = new AddCardDialog(parentFrame, deck, table.getModel());
            addCardDialog.pack();
            addCardDialog.setVisible(true);
        }
    }

    private class EditListener implements ActionListener {

        // EFFECTS: shows a dialog allowing for user input to modify the fields of a card
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
