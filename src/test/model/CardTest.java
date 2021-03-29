package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    Card c;
    Card c1;
    ArrayList<String> tags;

    @BeforeEach
    public void runBefore() {
        ArrayList<String> tagsExample = new ArrayList<>();
        tagsExample.add("Addition");
        tagsExample.add("Arithmetic");
        c = new Card("","",new ArrayList<>());
        c.setEase(1.4);
        c1 = new Card("1 + 1", "2", tagsExample);
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

    @Test
    public void testToJson() {
        c1.setStatus(1)
                .setReviewDate(444444);
        JSONObject json = c1.toJson();
         assertEquals("1 + 1", json.getString("front"));
         assertEquals("2", json.getString("back"));
         assertEquals(2, json.getJSONArray("tags").length());
         assertEquals(0, json.getInt("reviewCount"));
         assertEquals(1, json.getInt("status"));
         assertEquals(444444, json.getLong("reviewDate"));
         assertEquals(0, json.getLong("currentInterval"));
         assertEquals(2.5, json.getDouble("ease"));
    }

    @Test
    public void testEaseCalculationNormal() {
        c1.calculateEase(3);
        assertEquals(2.5 + (0.1 - 0 * (0.08 + 0 * 0.02)), c1.getEase());
    }

    @Test
    public void testEaseCalculationAtLowerLimit() {
        Card c2 = new Card("", "", new ArrayList<>());
        c2.setEase(1.4);
        c.calculateEase(1);
        assertEquals(1.4, c.getEase());
    }

    @Test
    public void testEaseCalculationPastLowerLimit() {
        Card c2 = new Card("", "", new ArrayList<>());
        c2.setEase(1.3);
        c.calculateEase(2);
        assertEquals(1.4, c.getEase());
    }


    @Test
    public void testProcessReviewCountZero() {
        assertEquals(0, c.getReviewCount());
        c.processReview(3);
        assertEquals(24, c.getCurrentInterval());
        assertEquals(1, c.getReviewCount());
        assertNotEquals(0, c.getReviewDate());
    }

    @Test
    public void testProcessReviewCountOne() {
        c.setReviewCount(1);
        assertEquals(1, c.getReviewCount());
        c.processReview(3);
        assertEquals(24 * 6, c.getCurrentInterval());
        assertEquals(2, c.getReviewCount());
        assertNotEquals(0, c.getReviewDate());
    }

    @Test
    public void testProcessReviewCountMoreThanOne() {
        c.setReviewCount(3);
        c.setCurrentInterval(60);
        assertEquals(3, c.getReviewCount());
        c.processReview(2);
        assertEquals(60 * 1.4, c.getCurrentInterval());
        assertEquals(4, c.getReviewCount());
        assertNotEquals(0, c.getReviewDate());
    }

    @Test
    public void testProcessReviewCardNotYetMature() {
        c.setCurrentInterval((long) ((24 * 21) / 1.4 - 1));
        c.setEase(1);
        c.setReviewCount(3);
        c.processReview(2);
        assertEquals(0, c.getStatus());
    }

    @Test
    public void testProcessReviewCardMature() {
        c.setCurrentInterval(24 * 21);
        c.setEase(1);
        c.setReviewCount(3);
        c.processReview(3);
        assertEquals(1, c.getStatus());
    }

    @Test
    public void testProcessReviewCardPastMature() {
        c.setCurrentInterval(24 * 21 + 1);
        c.setEase(1);
        c.setReviewCount(3);
        c.processReview(3);
        assertEquals(1, c.getStatus());
    }

    @Test
    public void testSetTags() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add("Hello!");
        tags.add("Bye!");
        c.setTags(tags);
        assertEquals(2, c.getTags().size());
        assertTrue(c.getTags().contains("Hello!"));
        assertTrue(c.getTags().contains("Bye!"));
    }
}
