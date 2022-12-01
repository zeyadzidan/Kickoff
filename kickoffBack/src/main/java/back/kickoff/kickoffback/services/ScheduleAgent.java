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
        if(!sr.existsById(Id))
            return false ;

        CourtSchedule schedule = sr.getReferenceById(Id);
        schedule.setStartWorkingHours(start);
        schedule.setEndWorkingHours(end);
        sr.save(schedule) ;
        return true ;
    }

    boolean setMinTime(Integer minHours, Long Id){
        if(!sr.existsById(Id))
            return false ;

        CourtSchedule schedule = sr.getReferenceById(Id);
        schedule.setMinBookingHours(minHours);
        sr.save(schedule) ;
        return true ;
    }

    boolean setCostMorning(Integer cost, Long Id){
        if(!sr.existsById(Id))
            return false ;

        CourtSchedule schedule = sr.getReferenceById(Id);
        schedule.setMorningCost(cost);
        sr.save(schedule) ;
        return true ;
    }

    boolean setCostNight(Integer cost, Long Id){
        if(!sr.existsById(Id))
            return false ;

        CourtSchedule schedule = sr.getReferenceById(Id);
        schedule.setNightCost(cost);
        sr.save(schedule) ;
        return true ;
    }

    boolean setMorningEnd(Time morningEnd, Long Id){
        if(!sr.existsById(Id))
            return false ;

        CourtSchedule schedule = sr.getReferenceById(Id);
        schedule.setEndMorning(morningEnd);
        sr.save(schedule) ;
        return true ;
    }

    boolean setReservationBooked(Long res, Long Id){
        if(!sr.existsById(Id))
            return false ;

        CourtSchedule schedule = sr.getReferenceById(Id);
        schedule.getBookedReservations().add(res) ;
        sr.save(schedule) ;
        return true ;
    }

    boolean setReservationPending(Long res, Long Id){
        if(!sr.existsById(Id))
            return false ;

        CourtSchedule schedule = sr.getReferenceById(Id);
        schedule.getPendingReservations().add(res) ;
        sr.save(schedule) ;
        return true ;
    }

    boolean deletePending(Long res, Long Id){
        if(!sr.existsById(Id))
            return false ;

        CourtSchedule schedule = sr.getReferenceById(Id);
        schedule.getPendingReservations().remove(res) ;
        sr.save(schedule) ;
        return true ;
    }

    boolean deleteBooked(Long res, Long Id){
        if(!sr.existsById(Id))
            return false ;

        CourtSchedule schedule = sr.getReferenceById(Id);
        schedule.getBookedReservations().remove(res) ;
        sr.save(schedule) ;
        return true ;
    }

    public List<Reservation> getScheduleBetween(Date fromD, Date toD, Time fromT, Time toT, Long CourtId){
        if(!sr.existsById(CourtId))
            return null;

        CourtSchedule schedule = sr.getReferenceById(CourtId);
        ArrayList<Reservation> res = new ArrayList<Reservation>() ;
        for(Long resId: schedule.getBookedReservations()){
            Reservation r =rr.getReferenceById(resId) ;
            if(r.getDate().compareTo(fromD) >= 0 && r.getDate().compareTo(toD) <= 0 && r.getTimeFrom().compareTo(fromT)>=0 && r.getTimeTo().compareTo(toT)>=0){
                res.add(r) ;
            }
        }
        for(Long resId: schedule.getPendingReservations()){
            Reservation r =rr.getReferenceById(resId) ;
            if(r.getDate().compareTo(fromD) >= 0 && r.getDate().compareTo(toD) <= 0 && r.getTimeFrom().compareTo(fromT)>=0 && r.getTimeTo().compareTo(toT)>=0){
                res.add(r) ;
            }
        }

        return res ;

    }


}
