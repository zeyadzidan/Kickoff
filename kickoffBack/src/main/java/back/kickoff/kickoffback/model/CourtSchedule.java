package back.kickoff.kickoffback.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.sql.Time;
import java.util.List;
import java.util.Objects;

@ToString
@RequiredArgsConstructor
@Setter
@Getter
@Table
@Entity
public class CourtSchedule {


    public CourtSchedule(Long courtID, Time startWorkingHours, Time endWorkingHours, Time endMorning, Integer morningCost, Integer nightCost) {
        this.courtID = courtID;
        this.startWorkingHours = startWorkingHours;
        this.endWorkingHours = endWorkingHours;
        this.endMorning = endMorning;
        this.morningCost = morningCost;
        this.nightCost = nightCost;
    }

    @Id
    Long courtID;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name= "fk_booked", referencedColumnName = "courtID")
    List<Reservation> bookedReservations ;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name= "fk_pending", referencedColumnName = "courtID")
    List<Reservation> pendingReservations ;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name= "fk_his", referencedColumnName = "courtID")
    List<Reservation> history ;

    @Column(nullable = false)
    Time startWorkingHours ;
    @Column(nullable = false)
    Time endWorkingHours ;
    Time endMorning ;

    Integer minBookingHours = 1 ;

    @Column(nullable = false)
    Integer morningCost ;

    Integer nightCost ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CourtSchedule schedule = (CourtSchedule) o;
        return courtID != null && Objects.equals(courtID, schedule.courtID);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
