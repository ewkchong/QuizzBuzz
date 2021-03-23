package ui;

import model.Deck;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainMenu extends JPanel implements ListSelectionListener {
    private static final String createString = "Create new deck";
    private String imagePath = "data/logo200.png";
    private DefaultListModel<Deck> deckListModel;
    private JFrame parent;
    private ArrayList<Deck> decks;
    private JList<Deck> list;
    private JButton viewButton;
    private JButton renameButton;
    private JButton deleteButton;
    private JTextField deckName;

    public MainMenu(ArrayList<Deck> decks, JFrame parent) {
        this.parent = parent;
        setPreferredSize(new Dimension(1024, 768));
        setLayout(new BorderLayout(5,5));
        this.decks = decks;
        deckListModel = new DefaultListModel<Deck>();
        for (Deck d: decks) {
            deckListModel.addElement(d);
        }
        displayList();
    }

    public JFrame getParentFrame() {
        return parent;
    }

    private void displayList() {
        list = new JList<Deck>(deckListModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
//      list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setFixedCellHeight(60);
        JScrollPane listScrollPane = new JScrollPane(list);

        JButton createButton = new JButton(createString);
        CreateDeckListener createDeckListener = new CreateDeckListener(createButton, decks);
        createButton.setEnabled(false);
        createButton.addActionListener(createDeckListener);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(listScrollPane, BorderLayout.CENTER);

        instantiateTextField(createDeckListener);

        JPanel bottomRow = instantiateBottomRow(createButton);

        mainPanel.add(bottomRow, BorderLayout.PAGE_END);
        add(mainPanel, BorderLayout.CENTER);
        add(buttonSidebar(), BorderLayout.LINE_END);
    }

    private JPanel instantiateBottomRow(JButton createButton) {
        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new BoxLayout(bottomRow, BoxLayout.LINE_AXIS));
        bottomRow.add(deckName);
        bottomRow.add(Box.createHorizontalStrut(10));
        bottomRow.add(createButton);
        bottomRow.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        return bottomRow;
    }

    private void instantiateTextField(CreateDeckListener createDeckListener) {
        deckName = new JTextField(30);
        deckName.setFont(new Font("Segoe UI", Font.PLAIN, 19));
        deckName.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));
        deckName.addActionListener(createDeckListener);
        deckName.getDocument().addDocumentListener(createDeckListener);
    }

    public JLabel logoPanel() {
        JLabel logo = null;
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            logo = new JLabel("QuizzBuzz", new ImageIcon(image), SwingConstants.CENTER);
            logo.setVerticalTextPosition(SwingConstants.BOTTOM);
            logo.setHorizontalTextPosition(SwingConstants.CENTER);
            logo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logo;
    }

    public JPanel buttonSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        viewButton = makeButton("View Deck");
        viewButton.addActionListener(new ViewListener());

        renameButton = makeButton("Rename Deck");

        deleteButton = makeButton("Delete Deck");

        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 200;
        sidebar.add(viewButton, c);

        // c.gridx = 0;
        c.gridy = 1;
        c.ipady = 0;
        sidebar.add(renameButton, c);

        c.gridy = 2;
        sidebar.add(deleteButton, c);

        c.gridy = 3;
        c.weighty = 1.0;
        c.insets = new Insets(20, 0,0,0);
        sidebar.add(logoPanel(), c);

        return sidebar;
    }

    private JButton makeButton(String s) {
        JButton button = new JButton(s);
        button.setPreferredSize(new Dimension(200, 80));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setEnabled(false);
        return button;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (list.getSelectedIndex() == -1) {
            viewButton.setEnabled(false);
            renameButton.setEnabled(false);
            deleteButton.setEnabled(false);

        } else {
            viewButton.setEnabled(true);
            renameButton.setEnabled(true);
            deleteButton.setEnabled(true);
        }
    }

    // adapted from Java Swing ListDemo's FireListener
    class ViewListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Deck selected = list.getSelectedValue();
            Container frameContent = parent.getContentPane();

            frameContent.removeAll();
            frameContent.add(new DeckMenu(MainMenu.this, selected));
            frameContent.revalidate();
            frameContent.repaint();
        }
    }

    class CreateDeckListener implements ActionListener, DocumentListener {
        private JButton button;
        private ArrayList<Deck> deckList;

        public CreateDeckListener(JButton button, ArrayList<Deck> deckList) {
            this.button = button;
            this.deckList = deckList;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = deckName.getText();
            Deck newDeck = new Deck(name);
            deckList.add(newDeck);
            deckListModel.addElement(newDeck);

            deckName.requestFocusInWindow();
            deckName.setText("");
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        private void enableButton() {
            button.setEnabled(true);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            button.setEnabled(e.getDocument().getLength() > 0);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            button.setEnabled(e.getDocument().getLength() > 0);
        }
    }


}
