package ui;

import model.Deck;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainMenu extends JPanel implements ListSelectionListener {
    private static final String createString = "Create";
    private DefaultListModel<Deck> deckListModel;
    private JFrame parent;
    private JList<Deck> list;
    private JButton viewButton;
    private JButton renameButton;

    public MainMenu(ArrayList<Deck> decks, JFrame parent) {
        this.parent = parent;
        setPreferredSize(new Dimension(1024, 768));
        setLayout(new BorderLayout(5,5));
        deckListModel = new DefaultListModel<Deck>();
        for (Deck d: decks) {
            deckListModel.addElement(d);
        }
        displayList();
    }

    private void displayList() {
        list = new JList<Deck>(deckListModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
//        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setFixedCellHeight(60);
        JScrollPane listScrollPane = new JScrollPane(list);

        JButton createButton = new JButton(createString);
        createButton.setEnabled(false);

        JTextField deckName = new JTextField(30);

        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new BoxLayout(bottomRow, BoxLayout.LINE_AXIS));
        bottomRow.add(deckName);
        bottomRow.add(Box.createHorizontalStrut(10));
        bottomRow.add(createButton);
        bottomRow.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonSidebar(), BorderLayout.LINE_END);
        add(bottomRow, BorderLayout.PAGE_END);
    }

    public JPanel buttonSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.PAGE_AXIS));

        viewButton = createButton("View Deck");
        viewButton.addActionListener(new ViewListener());

        renameButton = createButton("Rename Deck");

        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(viewButton);
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(renameButton);

        return sidebar;
    }

    private JButton createButton(String s) {
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

        } else {
            viewButton.setEnabled(true);
            renameButton.setEnabled(true);
        }
    }

    // adapted from Java Swing ListDemo's FireListener
    class ViewListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            Container frameContent = parent.getContentPane();

            frameContent.removeAll();
            frameContent.revalidate();
            frameContent.repaint();
        }
    }
}
