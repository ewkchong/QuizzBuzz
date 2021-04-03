package persistence;

import model.Card;
import model.Deck;
import org.junit.jupiter.api.Test;
import ui.QuizApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    public void testFileNotFound() {
        JsonReader reader = new JsonReader("./data/nonexistent.json");
        try {
            reader.read();
            fail("IOException should be thrown");
        } catch (IOException e) {
            // pass!
        }
    }

    @Test
    public void testReadEmptyDecks() {
        try {
            JsonReader reader = new JsonReader("./data/testEmptyDecks.json");
            QuizApp qa1 = new QuizApp(reader.read());
            ArrayList<Deck> decks1 = qa1.getDecks();
            Deck chin = decks1.get(0);
            Deck cpsc = decks1.get(1);
            assertEquals(2, decks1.size());
            assertEquals("CHIN 131", chin.getTitle());
            assertEquals("CPSC 210", cpsc.getTitle());
            assertEquals(0, chin.getCardList().size());
            assertEquals(0, cpsc.getCardList().size());
        } catch (IOException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testReadNormalDeck() {
        try {
            JsonReader reader = new JsonReader("./data/testNormalDeck.json");
            QuizApp app = new QuizApp(reader.read());
            Deck d = app.getDecks().get(0);
            assertEquals("Arithmetic", d.getTitle());
            assertEquals(2, d.getCardList().size());
            Card c0 = d.getCardList().get(0);
            Card c1 = d.getCardList().get(1);
            checkCardTextFields("1 + 1", "2", 0, c0);
            checkCardTextFields("2 - 2", "0", 2, c1);
            checkCardScheduleFields(0, 0, 0, 2.5, 0, c0);
            checkCardScheduleFields(4, 60, 444444, 1.6, 1, c1);
        } catch (IOException e) {
            fail("No exception should be thrown");
        }
    }
}
