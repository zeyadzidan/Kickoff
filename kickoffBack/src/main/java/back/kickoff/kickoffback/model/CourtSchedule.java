package back.kickoff.kickoffback.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Time;
import java.util.ArrayList;

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

    @ElementCollection
    ArrayList<Long> bookedReservations = new ArrayList<Long>() ;

    @ElementCollection
    ArrayList<Long> pendingReservations = new ArrayList<Long>() ;

    @ElementCollection
    ArrayList<Long> history = new ArrayList<Long>() ;

    @Column(nullable = false)
    Time startWorkingHours ;
    @Column(nullable = false)
    Time endWorkingHours ;
    Time endMorning ;

    Integer minBookingHours = 1 ;

    @Column(nullable = false)
    Integer morningCost ;

    Integer nightCost ;

    @ElementCollection
    ArrayList<Long> activeRequest = new ArrayList<Long>() ;


    public Long getCourtID() {
        return courtID;
    }

    public void setCourtID(Long courtID) {
        this.courtID = courtID;
    }

    public ArrayList<Long> getBookedReservations() {
        return bookedReservations;
    }

    public void setBookedReservations(ArrayList<Long> bookedReservations) {
        this.bookedReservations = bookedReservations;
    }

    public ArrayList<Long> getPendingReservations() {
        return pendingReservations;
    }

    public void setPendingReservations(ArrayList<Long> pendingReservations) {
        this.pendingReservations = pendingReservations;
    }

    public ArrayList<Long> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Long> history) {
        this.history = history;
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

    public ArrayList<Long> getActiveRequest() {
        return activeRequest;
    }

    public void setActiveRequest(ArrayList<Long> activeRequest) {
        this.activeRequest = activeRequest;
    }
}
