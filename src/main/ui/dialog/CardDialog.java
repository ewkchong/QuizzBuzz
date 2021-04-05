package ui.dialog;

import model.Deck;
import ui.utilities.QuizAppUtilities;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Representation of card dialog that takes user input
public abstract class CardDialog extends JDialog {
    protected Deck deck;                      // containing deck for card
    protected String buttonText;              // text for button label
    protected JTextField frontTextField;      // text field for front text
    protected JTextField backTextField;       // text field for back text
    protected JTextField tagTextField;        // text field for tags
    protected TableModel tableModel;          // table of cards model
    protected JFrame parentFrame;             // containing frame
    protected final int textFieldWidth = 20;  // columns of all textFields

    // EFFECTS: creates a new dialog with default button label "Confirm"
    public CardDialog(Frame owner, boolean modal) {
        super(owner, modal);
        parentFrame = (JFrame) owner;
        buttonText = "Confirm";
    }

    // MODIFIES: this
    // EFFECTS: adds all components to dialog
    protected void addComponents() {
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

    // MODIFIES: contentPanel, c
    // EFFECTS: adds buttons to given content panel with constraints
    private void addButtons(JPanel contentPanel, GridBagConstraints c) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton addButton = new JButton(buttonText);
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

    // MODIFIES: this, contentPanel, c
    // EFFECTS: initializes text field for tag entry, adds it to given panel
    protected void addTag(JPanel contentPanel, GridBagConstraints c) {
        JLabel tagLabel = makeLabel("Tags");
        tagTextField = makeTextField();
        c.gridy = 4;
        contentPanel.add(tagLabel, c);
        c.gridy = 5;
        contentPanel.add(tagTextField, c);
    }

    // MODIFIES: this, contentPanel, c
    // EFFECTS: initializes text field for back entry, adds it to given panel
    protected void addBack(JPanel contentPanel, GridBagConstraints c) {
        JLabel backLabel = makeLabel("Back of Card");
        backTextField = makeTextField();
        c.gridy = 2;
        contentPanel.add(backLabel, c);
        c.gridy = 3;
        contentPanel.add(backTextField, c);
    }

    // MODIFIES: this, contentPanel, c
    // EFFECTS: initializes text field for front entry, adds it to given panel
    protected void addFront(JPanel contentPanel, GridBagConstraints c) {
        JLabel frontLabel = makeLabel("Front of Card");
        frontTextField = makeTextField();
        c.gridy = 0;
        contentPanel.add(frontLabel, c);
        c.gridy = 1;
        contentPanel.add(frontTextField, c);
    }

    // EFFECTS: creates and returns a text field with a given width
    protected JTextField makeTextField() {
        JTextField textField = new JTextField(textFieldWidth);
        textField.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 24));

        return textField;
    }

    // EFFECTS: creates and returns a label with given text
    protected JLabel makeLabel(String s) {
        JLabel label = new JLabel(s);
        label.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 16));

        return label;
    }

    protected abstract void handleAction();

    // Listens for "Cancel" button press
    class CancelListener implements ActionListener {

        // EFFECTS: closes window on button press
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    // Listens for "Add" button press
    class AddListener implements ActionListener {

        // EFFECTS: calls handleAction() on button press
        @Override
        public void actionPerformed(ActionEvent e) {
            handleAction();
        }
    }
}
