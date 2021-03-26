package ui.utilities;

import model.Deck;

import javax.swing.*;

public class QuizAppUtilities {
    public static final String UI_FONT = "Montserrat";

    public static String createRenameDialog(Deck d, JFrame parent) {
        return (String) JOptionPane.showInputDialog(
                parent,
                "What would you like to rename the deck to?",
                "Rename Deck",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                d.getTitle());
    }

    public static int createDeleteDialog(Deck d, JFrame parent) {
        return  JOptionPane.showConfirmDialog(
                parent,
                "Are you sure you would like to delete the deck: " + '"' + d.getTitle() + '"' + "?",
                "Delete Deck",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE);
    }
}
