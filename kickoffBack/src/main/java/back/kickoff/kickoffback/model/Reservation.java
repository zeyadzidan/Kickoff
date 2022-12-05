package back.kickoff.kickoffback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.cfg.annotations.reflection.internal.XMLContext;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data
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
    Set<Player> playersID = new HashSet<>();
*/

    //@OneToOne(fetch = FetchType.EAGER)
    Long playerID;
    String playerName;
    Long courtID;
    Long courtOwnerID;
    Date startDate ;
    Date endDate;
    Time timeFrom;
    Time timeTo;
    ReservationState state;
    int moneyPayed ;
    int totalCost ;
    //Long messageID ;



    public Reservation(Long playerID, String playerName, Long courtID, Long courtOwnerID, Date startDate, Date endDate, Time timeFrom,
                       Time timeTo, ReservationState state, int moneyPayed, int totalCost) {
        this.playerID = playerID ;
        this.playerName = playerName;
        this.courtID = courtID;
        this.courtOwnerID = courtOwnerID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.state = state;
        this.moneyPayed = moneyPayed;
        this.totalCost = totalCost;
    }
}

















