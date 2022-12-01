package back.kickoff.kickoffback.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CourtSchedule {

    public CourtSchedule() {
    }

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

    //@ElementCollection
    //ArrayList<Long> activeRequest = new ArrayList<Long>() ;

    public List<Reservation> getBookedReservations() {
        return bookedReservations;
    }

    public void setBookedReservations(List<Reservation> bookedReservations) {
        this.bookedReservations = bookedReservations;
    }

    public List<Reservation> getPendingReservations() {
        return pendingReservations;
    }

    public void setPendingReservations(List<Reservation> pendingReservations) {
        this.pendingReservations = pendingReservations;
    }

    public List<Reservation> getHistory() {
        return history;
    }

    public void setHistory(List<Reservation> history) {
        this.history = history;
    }

    public Long getCourtID() {
        return courtID;
    }

    public void setCourtID(Long courtID) {
        this.courtID = courtID;
    }



    public Time getStartWorkingHours() {
        return startWorkingHours;
    }

    public void setStartWorkingHours(Time startWorkingHours) {
        this.startWorkingHours = startWorkingHours;
    }

    public Time getEndWorkingHours() {
        return endWorkingHours;
    }

    public void setEndWorkingHours(Time endWorkingHours) {
        this.endWorkingHours = endWorkingHours;
    }

    public Time getEndMorning() {
        return endMorning;
    }

    public void setEndMorning(Time endMorning) {
        this.endMorning = endMorning;
    }

    public Integer getMinBookingHours() {
        return minBookingHours;
    }

    public void setMinBookingHours(Integer minBookingHours) {
        this.minBookingHours = minBookingHours;
    }

    public Integer getMorningCost() {
        return morningCost;
    }

    public void setMorningCost(Integer morningCost) {
        this.morningCost = morningCost;
    }

    public Integer getNightCost() {
        return nightCost;
    }

    public void setNightCost(Integer nightCost) {
        this.nightCost = nightCost;
    }
/*
    public ArrayList<Long> getActiveRequest() {
        return activeRequest;
    }

    public void setActiveRequest(ArrayList<Long> activeRequest) {
        this.activeRequest = activeRequest;
    }
    */
}
