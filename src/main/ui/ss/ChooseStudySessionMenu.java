package ui.ss;

import model.Card;

import javax.swing.*;
import java.util.ArrayList;

public class ChooseStudySessionMenu extends JPanel {
    ArrayList<Card> cardList;

    public ChooseStudySessionMenu(ArrayList<Card> cardList) {
        this.cardList = cardList;
    }
}
