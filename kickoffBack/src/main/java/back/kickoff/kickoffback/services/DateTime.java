package back.kickoff.kickoffback.services;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Objects;

public class DateTime implements Comparable<DateTime> {
    public LocalTime time ;
    public LocalDate date ;

    public LocalDateTime dateTime ;

    public DateTime(LocalDate date, LocalTime time) {
        this.time = time;
        this.date = date;

        dateTime = LocalDateTime.of(date, time) ;
    }

    public boolean equals(DateTime o) {
        if (this == o) return true;
        if (o == null) return false;
        return this.dateTime.equals(o.dateTime) ;
    }


    @Override
    public int compareTo(DateTime o) {
        System.out.println(this.dateTime + " compare to " + o.dateTime);
        LocalDateTime tempDateTime = LocalDateTime.from(o.dateTime);
        int res = (int) tempDateTime.until( this.dateTime, ChronoUnit.HOURS );

        System.out.println(res);

        return res;
    }

    @Override
    public String toString() {
        return "DateTime{" +
                "time=" + time +
                ", data=" + date +
                '}';
    }
}
