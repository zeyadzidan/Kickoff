package back.kickoff.kickoffback.model;

import jakarta.persistence.*;
import org.hibernate.cfg.annotations.reflection.internal.XMLContext;

import java.sql.Time;
import java.util.ArrayList;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ElementCollection
    ArrayList<Long> playersID = new ArrayList<Long>();

    @Column(nullable = false)
    Long mainPlayerID;
    @Column(nullable = false)
    Long courtID;
    @Column(nullable = false)
    Long courtOwnerID;
    Time time;
    String state;
    int moneyPayed ;
    int totalCost ;
    Long messageID ;

    public Reservation() {
    }

    public Reservation(Long mainPlayerID, Long courtID, Long courtOwnerID) {
        this.mainPlayerID = mainPlayerID;
        this.courtID = courtID;
        this.courtOwnerID = courtOwnerID;
    }


    public Long getId() {
        return id;
    }

    public Long getMainPlayerID() {
        return mainPlayerID;
    }

    public Reservation setMainPlayerID(Long mainPlayerID) {
        this.mainPlayerID = mainPlayerID;
        return this;

    }

    public Long getCourtID() {
        return courtID;
    }

    public Reservation setCourtID(Long courtID) {
        this.courtID = courtID;
        return this;
    }

    public Long getCourtOwnerID() {
        return courtOwnerID;
    }

    public Reservation setCourtownerID(Long courtownerID) {
        this.courtOwnerID = courtownerID;
        return this;

    }

    public Time getTime() {
        return time;
    }

    public Reservation setTime(Time time) {
        this.time = time;
        return this;

    }

    public String getState() {
        return state;
    }

    public Reservation setState(String state) {
        this.state = state;
        return this;

    }

    public int getMoneyPayed() {
        return moneyPayed;
    }

    public Reservation setMoneyPayed(int moneyPayed) {
        this.moneyPayed = moneyPayed;
        return this;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public Reservation setTotalCost(int totalCost) {
        this.totalCost = totalCost;
        return this;

    }

    public Long getMessageID() {
        return messageID;
    }

    public Reservation setMessageID(Long messageID) {
        this.messageID = messageID;
        return this;

    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", mainPlayerID=" + mainPlayerID +
                ", courtID=" + courtID +
                ", courtownerID=" + courtOwnerID +
                ", time=" + time +
                ", state='" + state + '\'' +
                ", moneyPayed=" + moneyPayed +
                ", totalCost=" + totalCost +
                ", messageID=" + messageID +
                '}';
    }
}

















