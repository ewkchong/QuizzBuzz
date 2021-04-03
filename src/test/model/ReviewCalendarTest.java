package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReviewCalendarTest {
    long currentTime;
    ReviewCalendar rc;

    @BeforeEach
    public void runBefore() {
        rc = new ReviewCalendar();
        currentTime = System.currentTimeMillis() / 3600000;
    }

    @Test
    public void testTime() {
        assertEquals(currentTime, rc.time());
    }

    @Test
    public void testDisplayDate() {
        rc = new ReviewCalendar(1615087174 / 3600);
        assertEquals("2021-3-6", rc.displayDate());
    }

    @Test
    public void testDisplayDateZero() {
        rc = new ReviewCalendar(0);
        assertEquals("Undated", rc.displayDate());
    }
}
