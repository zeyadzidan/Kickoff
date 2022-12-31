package back.kickoff.kickoffback.Commands;

import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.Reservation;
import back.kickoff.kickoffback.model.ReservationState;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;
@NoArgsConstructor
@Setter
@Getter
@Data
public class FrontEndReservation{

    Long id;
    Long playerID;
    String playerName;
    String phoneNumber;
    //Player mainPlayer;
    Long courtID;
    Long courtOwnerID;
    Date startDate ;
    Date endDate;
    Time timeFrom;
    Time timeTo;
    ReservationState state;
    int moneyPayed ;
    int totalCost ;
    String receiptUrl;
    //Set<Player> players;

    public FrontEndReservation(Reservation reservation){
        this.id = reservation.getId();
        this.playerID = reservation.getMainPlayer().getId();
        this.playerName = reservation.getMainPlayer().getName();
        //this.mainPlayer = reservation.getMainPlayer();
        this.courtID = reservation.getCourtID();
        this.courtOwnerID = reservation.getCourtOwnerID();
        this.startDate = reservation.getStartDate();
        this.endDate = reservation.getEndDate();
        this.timeFrom = reservation.getTimeFrom();
        this.timeTo = reservation.getTimeTo();
        this.state = reservation.getState();
        this.moneyPayed = reservation.getMoneyPayed();
        this.totalCost = reservation.getTotalCost();
        this.receiptUrl = reservation.getReceiptUrl();
        this.phoneNumber = reservation.getMainPlayer().getPhoneNumber();
        //this.players = reservation.getPlayers();
    }
}
