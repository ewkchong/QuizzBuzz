package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    Card c;
    ArrayList<String> tags;

    @BeforeEach
    public void runBefore() {
        c = new Card("","",new ArrayList<>());
        tags = c.getTags();
    }

    @Test
    public void testChangeFront() {
        assertEquals(c.getFront(), "");
        c.setFront("What is the capital city of Canada?");
        assertEquals(c.getFront(), "What is the capital city of Canada?");
    }

    @Test
    public void testChangeBack() {
        assertEquals(c.getBack(), "");
        c.setBack("Ottawa");
        assertEquals(c.getBack(), "Ottawa");
    }

    @Test
    public void testAddTag() {
        assertEquals(tags.size(), 0);
        c.changeTag(-1, "Vocabulary");
        assertEquals(tags.size(), 1);
        assertTrue(tags.contains("Vocabulary"));
    }

    @Test
    public void testRemoveTag() {
        assertEquals(tags.size(), 0);
        c.changeTag(-1, "Vocabulary");
        assertEquals(tags.size(), 1);
        assertTrue(tags.contains("Vocabulary"));

        c.changeTag(0, "Vocabulary");
        assertEquals(tags.size(), 0);
        assertFalse(tags.contains("Vocabulary"));
    }
}
