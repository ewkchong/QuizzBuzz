package ui.dialog;

import model.Deck;
import ui.utilities.QuizAppUtilities;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class CardDialog extends JDialog {
    Deck deck;
    String buttonText;
    JTextField frontTextField;
    JTextField backTextField;
    JTextField tagTextField;
    TableModel tableModel;

    public CardDialog(Frame owner, boolean modal) {
        super(owner, modal);
        buttonText = "Confirm";
    }

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

    protected void addTag(JPanel contentPanel, GridBagConstraints c) {
        JLabel tagLabel = makeLabel("Tags");
        tagTextField = makeTextField(20);
        c.gridy = 4;
        contentPanel.add(tagLabel, c);
        c.gridy = 5;
        contentPanel.add(tagTextField, c);
    }

    protected void addBack(JPanel contentPanel, GridBagConstraints c) {
        JLabel backLabel = makeLabel("Back of Card");
        backTextField = makeTextField(20);
        c.gridy = 2;
        contentPanel.add(backLabel, c);
        c.gridy = 3;
        contentPanel.add(backTextField, c);
    }

    protected void addFront(JPanel contentPanel, GridBagConstraints c) {
        JLabel frontLabel = makeLabel("Front of Card");
        frontTextField = makeTextField(20);
        c.gridy = 0;
        contentPanel.add(frontLabel, c);
        c.gridy = 1;
        contentPanel.add(frontTextField, c);
    }

    protected JTextField makeTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 24));

        return textField;
    }

    protected JLabel makeLabel(String s) {
        JLabel label = new JLabel(s);
        label.setFont(new Font(QuizAppUtilities.UI_FONT, Font.PLAIN, 16));

        return label;
    }

    protected abstract void handleAction();

    class CancelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    class AddListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            handleAction();
        }
    }
}
