package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    Card c;

    @BeforeEach
    public void runBefore() {
        c = new Card("","",new ArrayList<>());
    }

    @Test
    public void testChangeFront() {
        assertEquals(c.getFront(), "");
        c.changeFront("What is the capital city of Canada?");
        assertEquals(c.getFront(), "What is the capital city of Canada?");
    }

    @Test
    public void testChangeBack() {
        assertEquals(c.getBack(), "");
        c.changeBack("Ottawa");
        assertEquals(c.getBack(), "Ottawa");
    }
}
