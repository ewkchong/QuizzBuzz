package persistence;

import model.Card;
import model.Deck;
import org.junit.jupiter.api.Test;
import ui.QuizApp;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {

    protected void checkCardTextFields(String front, String back, int tagLength, Card card) {
        assertEquals(front, card.getFront());
        assertEquals(back, card.getBack());
        assertEquals(tagLength, card.getTags().size());
    }

    protected void checkCardScheduleFields(int reviews, long interval, long date, double ease, int status, Card c) {
        assertEquals(reviews, c.getReviewCount());
        assertEquals(interval, c.getCurrentInterval());
        assertEquals(date, c.getReviewDate());
        assertEquals(ease, c.getEase());
        assertEquals(status, c.getStatus());
    }
}
