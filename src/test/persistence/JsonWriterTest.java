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

public class JsonWriterTest extends JsonTest {

    @Test
    public void testBadFileName() {
        try {
            new JsonWriter("./data/\0bad:file.json");
            fail("File name cannot contain a colon");
        } catch (IOException e) {
            // pass!
        }
    }

    @Test
    public void testWriteEmptyDecks() {
        try {
            QuizApp qa = new QuizApp(new ArrayList<>());
            ArrayList<Deck> decks = qa.getDecks();
            decks.add(new Deck("CHIN 131"));
            decks.add(new Deck("CPSC 210"));
            JsonWriter writer = new JsonWriter("./data/testEmptyDecks.json");
            writer.save(qa.deckListToJson());

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
            fail("No exception should be thrown");
        }
    }

    @Test
    public void testWriteNormalDeck() {
        try {
            JsonWriter writer = new JsonWriter("./data/testNormalDeck.json");
            QuizApp qa = new QuizApp(new ArrayList<>());
            initializeNormalDeck(qa);
            writer.save(qa.deckListToJson());

            JsonReader reader = new JsonReader("./data/testNormalDeck.json");
            QuizApp app = new QuizApp(reader.read());
            Deck d = app.getDecks().get(0);
            assertEquals("Arithmetic", d.getTitle());
            assertEquals(2, d.getCardList().size());
            Card c0 = d.getCardList().get(0);
            Card c1 = d.getCardList().get(1);
            checkCardTextFields("1 + 1", "2", 0, c0);
            checkCardTextFields("2 - 2", "0", 2, c1);
            checkCardScheduleFields(0,0,0,2.5,0, c0);
            checkCardScheduleFields(4, 60,444444,1.6,1, c1);
        } catch (IOException e) {
            fail("No exception should be thrown");
        }
    }

    private void initializeNormalDeck(QuizApp qa) {
        ArrayList<Deck> decks = qa.getDecks();
        Deck d = new Deck("Arithmetic");
        d.addCard("1 + 1", "2", new ArrayList<>());

        ArrayList<String> exampleTags = new ArrayList<>();
        exampleTags.add("Subtraction");
        exampleTags.add("Difficult");
        Card c = new Card("2 - 2", "0", exampleTags);
        c.setReviewCount(4)
                .setCurrentInterval(60)
                .setReviewDate(444444)
                .setEase(1.6)
                .setStatus(1);
        d.addCard(c);

        decks.add(d);
    }
}
