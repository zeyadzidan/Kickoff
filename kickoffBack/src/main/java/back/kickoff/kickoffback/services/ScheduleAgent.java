package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.CourtSchedule;
import back.kickoff.kickoffback.model.Reservation;
import back.kickoff.kickoffback.repositories.PlayerRepositry;
import back.kickoff.kickoffback.repositories.ReservationRepository;
import back.kickoff.kickoffback.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleAgent {

    @Autowired
    ScheduleRepository sr ;

    @Autowired
    ReservationRepository rr ;

    public ScheduleAgent(ScheduleRepository sr, ReservationRepository rr) {
        this.rr = rr;
        this.sr = sr;
    }

    boolean setWorkingHours(Time start, Time end, Long Id){
        Optional<CourtSchedule> scheduleO = sr.findById(Id) ;
        if(scheduleO.isEmpty())
            return false ;

        CourtSchedule schedule =  scheduleO.get() ;
        schedule.setStartWorkingHours(start);
        schedule.setEndWorkingHours(end);
        sr.save(schedule) ;
        return true ;
    }

    boolean setMinTime(Integer minHours, Long Id){
        Optional<CourtSchedule> scheduleO = sr.findById(Id) ;
        if(scheduleO.isEmpty())
            return false ;

        CourtSchedule schedule =  scheduleO.get() ;
        schedule.setMinBookingHours(minHours);
        sr.save(schedule) ;
        return true ;
    }

    boolean setCostMorning(Integer cost, Long Id){
        Optional<CourtSchedule> scheduleO = sr.findById(Id) ;
        if(scheduleO.isEmpty())
            return false ;

        CourtSchedule schedule =  scheduleO.get() ;
        schedule.setMorningCost(cost);
        sr.save(schedule) ;
        return true ;
    }

    boolean setCostNight(Integer cost, Long Id){
        Optional<CourtSchedule> scheduleO = sr.findById(Id) ;
        if(scheduleO.isEmpty())
            return false ;

        CourtSchedule schedule =  scheduleO.get() ;
        schedule.setNightCost(cost);
        sr.save(schedule) ;
        return true ;
    }

    boolean setMorningEnd(Time morningEnd, Long Id){
        Optional<CourtSchedule> scheduleO = sr.findById(Id) ;
        if(scheduleO.isEmpty())
            return false ;

        CourtSchedule schedule =  scheduleO.get() ;
        schedule.setEndMorning(morningEnd);
        sr.save(schedule) ;
        return true ;
    }

    boolean setReservationBooked(Reservation res, Long Id){
        Optional<CourtSchedule> scheduleO = sr.findById(Id) ;
        if(scheduleO.isEmpty())
            return false ;

        CourtSchedule schedule =  scheduleO.get() ;
        schedule.getBookedReservations().add(res) ;
        sr.save(schedule) ;
        return true ;
    }

    boolean setReservationPending(Reservation res, Long Id){
        Optional<CourtSchedule> scheduleO = sr.findById(Id) ;
        if(scheduleO.isEmpty())
            return false ;

        CourtSchedule schedule =  scheduleO.get() ;
        schedule.getPendingReservations().add(res) ;
        sr.save(schedule) ;
        return true ;
    }

    boolean deletePending(Reservation res, Long Id){
        Optional<CourtSchedule> scheduleO = sr.findById(Id) ;
        if(scheduleO.isEmpty())
            return false ;

        CourtSchedule schedule =  scheduleO.get() ;
        schedule.getPendingReservations().remove(res) ;
        sr.save(schedule) ;
        return true ;
    }

    boolean deleteBooked(Reservation res, Long Id){
        Optional<CourtSchedule> scheduleO = sr.findById(Id) ;
        if(scheduleO.isEmpty())
            return false ;

        CourtSchedule schedule =  scheduleO.get() ;
        schedule.getBookedReservations().remove(res) ;
        sr.save(schedule) ;
        return true ;
    }

    public List<Reservation> getScheduleOverlapped(Date fromD, Date toD, Time fromT, Time toT, Long CourtId){
        Optional<CourtSchedule> scheduleO = sr.findById(CourtId) ;
        if(scheduleO.isEmpty())
            return null ;

        CourtSchedule schedule =  scheduleO.get() ;
        ArrayList<Reservation> res = new ArrayList<Reservation>() ;
        for(Reservation r: schedule.getBookedReservations()){
            if(r.getStartDate().compareTo(fromD) >= 0 && r.getStartDate().compareTo(toD) <= 0 &&
                    ((r.getTimeFrom().compareTo(fromT)>=0 && r.getTimeTo().compareTo(toT)>=0)
                            || (r.getTimeFrom().compareTo(toT)>=0 && r.getTimeTo().compareTo(toT)>=0))
                            || (r.getTimeFrom().compareTo(fromT)>=0 && r.getTimeFrom().compareTo(toT)>=0) ){
                res.add(r) ;
            }
        }
        for(Reservation r: schedule.getPendingReservations()){
            if(r.getStartDate().compareTo(fromD) >= 0 && r.getStartDate().compareTo(toD) <= 0 &&
                    ((r.getTimeFrom().compareTo(fromT)>=0 && r.getTimeTo().compareTo(toT)>=0)
                            || (r.getTimeFrom().compareTo(toT)>=0 && r.getTimeTo().compareTo(toT)>=0))
                    || (r.getTimeFrom().compareTo(fromT)>=0 && r.getTimeFrom().compareTo(toT)>=0) ){
                res.add(r) ;
            }
        }

        return res ;

    }


}
