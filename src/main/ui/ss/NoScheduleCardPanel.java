package ui.ss;

import model.Card;
import ui.QuizApp;

import java.awt.*;

// Panel that represents a flash card with a front and back
public class NoScheduleCardPanel extends StudyCardPanel {

    // EFFECTS: creates new card panel to show a single card
    public NoScheduleCardPanel(Card c, StudySession ss, int i, int size, QuizApp app) {
        super(app, c, ss, i, size);
        setLayout(new BorderLayout());
        addComponents();
        addButtonPanel();
    }

    // MODIFIES: this
    // EFFECTS: processes user input for difficulty (1, 2 or 3)
    //          returns GUI to deck menu
    @Override
    protected void processDifficulty(int diff) {
        if (index + 1 >= size) {
            returnToMenu();
            if (diff > 1) {
                ss.incrementCorrectReviews();
            }
            ss.showPerformanceDialog();
        } else {
            CardLayout cl = (CardLayout) ss.getLayout();
            cl.next(ss);
            if (diff > 1) {
                ss.incrementCorrectReviews();
            }
        }
    }

}

