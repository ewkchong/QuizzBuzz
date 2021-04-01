package ui;

import model.Deck;
import persistence.JsonWriter;
import ui.utilities.QuizAppUtilities;

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Menu displaying list of all decks and operations involving decks
public class MainMenu extends JPanel implements ListSelectionListener {
    private final QuizApp app;                              // parent application
    private final DefaultListModel<Deck> deckListModel;     // model of deck list
    private final JFrame parentFrame;                       // containing frame
    private final ArrayList<Deck> decks;                    // list of decks
    private JList<Deck> list;                               // JList of decks
    private JButton viewButton;                             // button for viewing chosen deck
    private JButton renameButton;                           // button for renaming chosen deck
    private JButton deleteButton;                           // button for deleting chosen deck
    private JButton saveQuitButton;                         // button for saving changes and quitting
    private JTextField deckName;                            // text field for creating new decks
    private JLabel logoPanel;                               // panel containing logo image
    private JPanel sidebar;                                 // panel containing all sidebar buttons

    // EFFECTS: creates a main menu displaying given decks in given frame
    public MainMenu(ArrayList<Deck> decks, JFrame parentFrame, QuizApp app) {
        this.app = app;
        this.parentFrame = parentFrame;
        setPreferredSize(new Dimension(1024, 768));
        setLayout(new BorderLayout(0,0));
        this.decks = decks;
        logoPanel = logoPanel();
        sidebar = buttonSidebar();
        deckListModel = new DefaultListModel<>();
        for (Deck d: decks) {
            deckListModel.addElement(d);
        }
        registerFonts();
        createAndShowUI();
    }

    // EFFECTS: registers required fonts for UI
    private void registerFonts() {
        try {
            Font montserrat = Font.createFont(
                    Font.TRUETYPE_FONT,
                    new File("data/fonts/Montserrat-Regular.ttf"));
            Font bold = Font.createFont(
                    Font.TRUETYPE_FONT,
                    new File("data/fonts/Montserrat-Bold.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(montserrat);
            ge.registerFont(bold);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JFrame getParentFrame() {
        return parentFrame;
    }

    // MODIFIES: this
    // EFFECTS: adds all components to frame
    private void createAndShowUI() {
        list = new JList<>(deckListModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 16));
//        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setFixedCellHeight(60);
        JScrollPane listScrollPane = new JScrollPane(list);

        JButton createButton = new JButton("Create New Deck");
        createButton.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 12));
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
        add(sidebar, BorderLayout.LINE_END);
    }

    // MODIFIES: this
    // EFFECTS: returns a panel with bottom row components
    private JPanel instantiateBottomRow(JButton createButton) {
        JPanel bottomRow = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        bottomRow.setLayout(new GridBagLayout());

        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = Short.MAX_VALUE;
        c.weightx = 1.0;
        c.insets = new Insets(5, 5, 5,0);
        bottomRow.add(deckName, c);

        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 0;
        c.weightx = 0;
        c.ipady = 11;
        bottomRow.add(createButton, c);
        return bottomRow;
    }

    // MODIFIES: this
    // EFFECTS: creates text field, assigns it to field, and formats it
    private void instantiateTextField(CreateDeckListener createDeckListener) {
        deckName = new JTextField(30);
        deckName.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 19));
        deckName.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));
        deckName.addActionListener(createDeckListener);
        deckName.getDocument().addDocumentListener(createDeckListener);
    }


    // EFFECTS: returns a panel containing the logo image
    public JLabel logoPanel() {
        JLabel logo = null;
        try {
            String imagePath = "data/images/logo200.png";
            BufferedImage image = ImageIO.read(new File(imagePath));
            logo = new JLabel("QuizzBuzz", new ImageIcon(image), SwingConstants.CENTER);
            logo.setVerticalTextPosition(SwingConstants.BOTTOM);
            logo.setHorizontalTextPosition(SwingConstants.CENTER);
            logo.setFont(new Font(QuizAppUtilities.UI_FONT, Font.BOLD, 24));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logo;
    }

    // EFFECTS: adds buttons to sidebar and returns sidebar
    public JPanel buttonSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        initializeButtons();

        c.ipady = 200;
        sidebar.add(viewButton, c);

        c.gridy = 1;
        c.ipady = 0;
        sidebar.add(renameButton, c);

        c.gridy = 2;
        sidebar.add(deleteButton, c);

        c.gridy = 3;
        c.weighty = 1.0;
        c.insets = new Insets(20, 0,0,0);
        sidebar.add(logoPanel, c);

        c.gridy = 4;
        c.weighty = 0;
        c.insets = new Insets(0,0,10,0);
        sidebar.add(saveQuitButton, c);

        return sidebar;
    }

    // MODIFIES: this
    // EFFECTS: sets button fields to new buttons, formats buttons and adds listeners
    private void initializeButtons() {
        viewButton = makeButton("View Deck");
        viewButton.addActionListener(new ViewListener());

        renameButton = makeButton("Rename Deck");
        renameButton.addActionListener(new RenameListener());

        deleteButton = makeButton("Delete Deck");
        deleteButton.addActionListener(new DeleteListener());

        saveQuitButton = new JButton("Save and Quit");
        saveQuitButton.setPreferredSize(new Dimension(180, 40));
        saveQuitButton.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 12));
        saveQuitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveQuitButton.addActionListener(new QuitListener());
    }

    // EFFECTS: returns a formatted button with label using given string
    private JButton makeButton(String s) {
        JButton button = new JButton(s);
        button.setPreferredSize(new Dimension(200, 80));
        button.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 12));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setEnabled(false);
        return button;
    }

    // MODIFIES: this
    // EFFECTS: removes deck from list of decks
    public void removeDeck(Deck d) {
        decks.remove(d);
        deckListModel.removeElement(d);
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    // EFFECTS: listens to list selection, if something is selected,
    //          enable all buttons
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

        // MODIFIES: this
        // EFFECTS: on button press, change frame to deck menu
        public void actionPerformed(ActionEvent e) {
            Deck selected = list.getSelectedValue();
            Container frameContent = parentFrame.getContentPane();

            frameContent.removeAll();
            frameContent.add(new DeckMenu(MainMenu.this, selected));
            frameContent.revalidate();
            frameContent.repaint();
        }
    }

    class CreateDeckListener implements ActionListener, DocumentListener {
        private final JButton button;
        private final ArrayList<Deck> deckList;

        public CreateDeckListener(JButton button, ArrayList<Deck> deckList) {
            this.button = button;
            this.deckList = deckList;
        }

        // MODIFIES: this
        // EFFECTS: gets text from text field, creates new deck with given text as name
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = deckName.getText();
            tobs(name.toLowerCase());
            Deck newDeck = new Deck(name);
            deckList.add(newDeck);
            deckListModel.addElement(newDeck);

            deckName.requestFocusInWindow();
            deckName.setText("");
        }

        // EFFECTS: enables button if text is added
        @Override
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        // MODIFIES: this
        // EFFECTS: enables button
        private void enableButton() {
            button.setEnabled(true);
        }

        // MODIFIES: this
        // EFFECTS: enables button if text field non-empty
        @Override
        public void removeUpdate(DocumentEvent e) {
            button.setEnabled(e.getDocument().getLength() > 0);
        }

        // MODIFIES: this
        // EFFECTS: enables button if text field non-empty
        @Override
        public void changedUpdate(DocumentEvent e) {
            button.setEnabled(e.getDocument().getLength() > 0);
        }

        // MODIFIES: this
        // EFFECTS: changes logo image to tobs when a deck named "tobs" is created
        private void tobs(String name) {
            String imagePath;
            if (name.equals("tobs")) {
                imagePath = "data/images/tobs.png";
            } else {
                imagePath = "data/images/logo200.png";
            }
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File(imagePath));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            logoPanel.setIcon(new ImageIcon(image));
        }
    }

    class DeleteListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: deletes selected deck, shows confirm dialog on button press
        @Override
        public void actionPerformed(ActionEvent e) {
            Deck d = list.getSelectedValue();
            int confirm = QuizAppUtilities.createDeleteDialog(d, parentFrame);
            if (confirm == 0) {
                int i = list.getSelectedIndex();
                decks.remove(d);
                deckListModel.removeElementAt(i);
            }
        }
    }

    class RenameListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: renames selected deck, shows dialog to enter text on button press
        @Override
        public void actionPerformed(ActionEvent e) {
            Deck d = list.getSelectedValue();
            String s = QuizAppUtilities.createRenameDialog(d, parentFrame);
            if (s != null && !(s.equals(""))) {
                d.renameDeck(s);
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        }
    }

    private class QuitListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: saves app to JSON file, closes window on button press
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JsonWriter writer = new JsonWriter("./data/decks.json");
                writer.save(app.deckListToJson());
            } catch (FileNotFoundException f) {
                System.out.println("File cannot be found!");
            }
            parentFrame.dispose();
        }
    }
}
