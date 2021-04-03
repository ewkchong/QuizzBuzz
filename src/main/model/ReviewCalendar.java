package model;

import java.util.Calendar;
import java.util.GregorianCalendar;

// A calendar that represents dates in hours
public class ReviewCalendar extends GregorianCalendar {

    // EFFECTS: instantiates a new calendar, with local time
    public ReviewCalendar() {
        super();
    }

    // EFFECTS: instantiates a new calendar, whose time is set to the given amount of hours since the epoch
    public ReviewCalendar(long hours) {
        super();
        setTimeInMillis(hours * 3600000);
    }

    // MODIFIES: this
    // EFFECTS: returns the current time in hours since the epoch
    public long time() {
        setTimeInMillis(System.currentTimeMillis());
        return (getTimeInMillis() / 3600000);
    }


    // EFFECTS: returns a string representation of the calendar object's date
    public String displayDate() {
        if (get(Calendar.YEAR) <= 1980) {
            return "Undated";
        }
        int y = get(Calendar.YEAR);
        int m = get(Calendar.MONTH) + 1;
        int d = get(Calendar.DATE);

        String year = Integer.toString(y);
        String month = Integer.toString(m);
        String day = Integer.toString(d);

        return year + "-" + month + "-" + day;
    }
}
