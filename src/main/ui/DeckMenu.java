package ui;

import javax.swing.*;
import java.awt.*;

public class DeckMenu extends JPanel {

    public DeckMenu() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JButton view = createButton("View Deck");

        JButton rename = createButton("Rename Deck");

        add(Box.createVerticalStrut(5));
        add(view);
        add(Box.createVerticalStrut(5));
        add(rename);
    }

    private JButton createButton(String s) {
        JButton button = new JButton(s);
        button.setPreferredSize(new Dimension(200, 80));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setEnabled(false);
        return button;
    }


}
