package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    Deck d;
    ArrayList<Card> cards;

    @BeforeEach
    public void runBefore() {
        d = new Deck("CHIN 131");
        cards = d.getCardList();
    }

    @Test
    public void testAddSingleCard() {
        assertEquals(cards.size(), 0);
        d.addCard("What is the capital city of China?", "Beijing", new ArrayList<>());
        assertEquals(cards.size(), 1);
    }

    @Test
    public void testAddMultipleCards() {
        assertEquals(cards.size(), 0);
        d.addCard("What is the capital city of China?", "Beijing", new ArrayList<>());
        d.addCard("What is the capital city of Canada?", "Ottawa", new ArrayList<>());
        d.addCard("What is the capital city of Russia?", "Moscow", new ArrayList<>());
        assertEquals(cards.size(), 3);
    }

    @Test
    public void testDeleteSingleCard() {
        assertEquals(cards.size(), 0);
        d.addCard("1+1", "2", new ArrayList<>());
        assertEquals(cards.size(), 1);
        d.deleteCard(1);
        assertEquals(cards.size(), 0);
    }

    @Test
    public void testDeleteMultipleCards() {
        assertEquals(cards.size(), 0);
        d.addCard("1+1", "2", new ArrayList<>());
        d.addCard("1+2", "3", new ArrayList<>());
        d.addCard("1+3", "4", new ArrayList<>());
        d.addCard("1+4", "5", new ArrayList<>());
        assertEquals(cards.size(), 4);
        d.deleteCard(2);
        assertEquals(cards.size(), 3);
        d.deleteCard(2);
        assertEquals(cards.size(), 2);
        assertEquals(cards.get(0).getBack(), "2");
        assertEquals(cards.get(1).getBack(), "5");
    }

    @Test
    public void testNoDelete() {
        assertEquals(cards.size(), 0);
        d.addCard("1+1", "2", new ArrayList<>());
        assertEquals(cards.size(), 1);
        d.deleteCard(0);
        assertEquals(cards.size(), 1);
    }

    @Test
    public void testRenameDeck() {
        assertEquals(d.getTitle(), "CHIN 131");
        d.renameDeck("CHIN 133");
        assertEquals(d.getTitle(), "CHIN 133");
    }

    @Test
    public void testToJson() {

    }
}
