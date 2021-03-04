package model;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ReviewCalendar extends GregorianCalendar {

    public ReviewCalendar() {
        super();
    }

    public ReviewCalendar(long hours) {
        super();
        setTimeInMillis(hours * 3600000);
    }

    // EFFECTS: returns the current time in hours since the epoch
    public long time() {
        setTimeInMillis(System.currentTimeMillis());
        return (getTimeInMillis() / 3600000);
    }

    public String displayTime() {
        int y = get(Calendar.YEAR);
        int m = get(Calendar.MONTH) + 1;
        int d = get(Calendar.DATE);

        String year = Integer.toString(y);
        String month = Integer.toString(m);
        String day = Integer.toString(d);

        return year + "-" + month + "-" + day;
    }
}
