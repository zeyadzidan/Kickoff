package back.kickoff.kickoffback.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.sql.Time;
import java.util.List;
import java.util.Objects;

@ToString
@Setter
@Getter
@Table
@NoArgsConstructor
@Data
@Entity
public class CourtSchedule {


    public CourtSchedule(Time startWorkingHours, Time endWorkingHours, Time endMorning, Integer morningCost, Integer nightCost, Integer minBookingHours) {
        this.startWorkingHours = startWorkingHours;
        this.endWorkingHours = endWorkingHours;
        if(endMorning == null){
            endMorning = endWorkingHours ;
        }
        this.endMorning = endMorning;
        this.morningCost = morningCost;
        if(nightCost == null)
            this.nightCost = morningCost ;
        else
            this.nightCost = nightCost ;
        if(minBookingHours != null)
            this.minBookingHours = minBookingHours ;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    Time startWorkingHours ;
    Time endWorkingHours ;
    Time endMorning ;

    Integer minBookingHours = 1 ;

    Integer morningCost ;

    Integer nightCost ;


}
