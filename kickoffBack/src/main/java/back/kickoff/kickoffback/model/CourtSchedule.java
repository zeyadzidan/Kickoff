package back.kickoff.kickoffback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.List;


@Data
@Entity
@Setter
@Getter
@Table
@NoArgsConstructor
public class CourtSchedule {
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_booked", referencedColumnName = "id")
    List<Reservation> bookedReservations;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_pending", referencedColumnName = "id")
    private List<Reservation> pendingReservations;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_his", referencedColumnName = "id")
    private List<Reservation> history;
    private Time startWorkingHours;
    private Time endWorkingHours;
    private Time endMorning;
    private Integer minBookingHours = 1;
    private Integer morningCost;
    private Integer nightCost;

    public CourtSchedule(Time startWorkingHours, Time endWorkingHours, Time endMorning, Integer morningCost, Integer nightCost, Integer minBookingHours) {
        this.startWorkingHours = startWorkingHours;
        this.endWorkingHours = endWorkingHours;
        if (endMorning == null) {
            endMorning = endWorkingHours;
        }
        this.endMorning = endMorning;
        this.morningCost = morningCost;
        if (nightCost == null)
            this.nightCost = morningCost;
        else
            this.nightCost = nightCost;
        if (minBookingHours != null)
            this.minBookingHours = minBookingHours;
    }


}
