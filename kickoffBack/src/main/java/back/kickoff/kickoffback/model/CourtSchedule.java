package back.kickoff.kickoffback.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.sql.Time;
import java.util.List;
import java.util.Objects;



@Data
@Entity
@Setter
@Getter
@Table
@NoArgsConstructor
public class CourtSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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



    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name= "fk_booked", referencedColumnName = "id")
    List<Reservation> bookedReservations ;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name= "fk_pending", referencedColumnName = "id")
    private List<Reservation> pendingReservations ;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name= "fk_his", referencedColumnName = "id")
    private List<Reservation> history ;

    private Time startWorkingHours ;
    private Time endWorkingHours ;
    private Time endMorning ;

    private Integer minBookingHours = 1 ;

    private Integer morningCost ;

    private Integer nightCost ;


}
