package back.kickoff.kickoffback.services;

import java.sql.Date;
import java.sql.Time;
import java.util.Comparator;
import java.util.Objects;

public class DateTime implements Comparable<DateTime> {
    public Time time ;
    public Date data ;

    public DateTime(Date data, Time time) {
        this.time = time;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateTime dateTime = (DateTime) o;
        return Objects.equals(time, dateTime.time) && Objects.equals(data, dateTime.data);
    }


    @Override
    public int compareTo(DateTime o) {
        int diffInDayesH = (int) (this.data.getTime()-o.data.getTime()) / (1000 * 60 * 60 );
        int diffInHours = (int) (this.time.getTime()-o.time.getTime()) / (1000 * 60 * 60);
        return diffInDayesH + diffInHours ;
    }
}
