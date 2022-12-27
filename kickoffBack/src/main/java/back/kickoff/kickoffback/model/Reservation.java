package back.kickoff.kickoffback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

@Setter
@Getter
@Table
@NoArgsConstructor
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

/*
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reservation_player",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    Set<LitePlayer> playersID = new HashSet<>();
*/

    Long pid;
    String pname;
    Long courtID;
    Long courtOwnerID;
    Date startDate;
    Date endDate;
    Time timeFrom;
    Time timeTo;

    Date dateReserved;
    Time timeReserved ;

    ReservationState state;
    String receiptUrl;
    int moneyPayed ;
    int totalCost ;
    //@ManyToMany(mappedBy = "reservations")
    //Set<Player> players;
    //Long messageID ;



    public Reservation(Long pid, String pname, Long courtID, Long courtOwnerID, Date startDate, Date endDate, Time timeFrom,
                       Time timeTo, ReservationState state, int moneyPayed, int totalCost) {
        this.pid = pid;
        this.pname = pname;
        this.courtID = courtID;
        this.courtOwnerID = courtOwnerID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.state = state;
        this.moneyPayed = moneyPayed;
        this.totalCost = totalCost;
        this.dateReserved = new Date(System.currentTimeMillis());
        this.timeReserved = new Time(System.currentTimeMillis());
    }
}

















