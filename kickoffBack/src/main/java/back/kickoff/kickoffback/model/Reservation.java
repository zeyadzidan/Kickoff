package back.kickoff.kickoffback.model;

import jakarta.persistence.*;
import org.hibernate.cfg.annotations.reflection.internal.XMLContext;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @ManyToMany
    @JoinTable(name = "reservation_player",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    Set<Player> playersID = new HashSet<>();


    @OneToOne(fetch = FetchType.EAGER)
    Player mainPlayerID;

    @Column(nullable = false)
    Long courtID;
    @Column(nullable = false)
    Long courtOwnerID;
    Date date ;
    Time timeFrom;
    Time timeTo;
    String state;
    int moneyPayed ;
    int totalCost ;
    Long messageID ;

    public Date getDate() {
        return date;
    }

    public Reservation setDate(Date date) {
        this.date = date;
        return this ;
    }

    public Reservation() {
    }

    public Reservation(Player mainPlayerID, Long courtID, Long courtOwnerID) {
        this.mainPlayerID = mainPlayerID;
        this.courtID = courtID;
        this.courtOwnerID = courtOwnerID;
    }


    public Long getId() {
        return id;
    }

    public Player getMainPlayerID() {
        return mainPlayerID;
    }

    public Reservation setMainPlayerID(Player mainPlayerID) {
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
                ", mainPlayerID=" + mainPlayerID.Id +
                ", courtID=" + courtID +
                ", courtownerID=" + courtOwnerID +
                ", timeFrom=" + timeFrom +
                ", timeTo=" + timeTo +
                ", state='" + state + '\'' +
                ", moneyPayed=" + moneyPayed +
                ", totalCost=" + totalCost +
                ", messageID=" + messageID +
                '}';
    }

    public Time getTimeFrom() {
        return timeFrom;
    }

    public Reservation setTimeFrom(Time timeFrom) {
        this.timeFrom = timeFrom;
        return this;
    }

    public Time getTimeTo() {
        return timeTo;
    }

    public Reservation setTimeTo(Time timeTo) {
        this.timeTo = timeTo;
        return this;
    }
}

















