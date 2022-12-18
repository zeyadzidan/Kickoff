package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.CourtSchedule;
import back.kickoff.kickoffback.model.Reservation;
import back.kickoff.kickoffback.model.ReservationState;
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
    ScheduleRepository sr;

    @Autowired
    ReservationRepository rr;

    public ScheduleAgent(ScheduleRepository sr, ReservationRepository rr) {
        this.rr = rr;
        this.sr = sr;
    }


    public List<Reservation> getScheduleOverlapped(Date fromD, Date toD, Time fromT, Time toT, CourtSchedule schedule){
        ArrayList<Reservation> res = new ArrayList<Reservation>() ;
        DateTime start = new DateTime(fromD, fromT) ;
        DateTime end = new DateTime(toD, toT) ;

        for(Reservation r: schedule.getBookedReservations()){
            DateTime resStart = new DateTime(r.getStartDate(), r.getTimeFrom()) ;
            DateTime resEnd = new DateTime(r.getEndDate(), r.getTimeTo()) ;

            if((resStart.compareTo(start) >= 0 && resEnd.compareTo(end)<=0)
                    || (resStart.compareTo(start) <= 0 && resEnd.compareTo(start)>0)
                    || (resStart.compareTo(end) < 0 && resEnd.compareTo(end)>=0) ){
                res.add(r) ;
            }

        }
        for(Reservation r: schedule.getPendingReservations()){
            if(!checkPendingConstraint(r)){
                schedule.getPendingReservations().remove(r) ;
                schedule.getHistory().add(r) ;
                rr.save(r) ;
                sr.save(schedule) ;
                continue;
            }

            DateTime resStart = new DateTime(r.getStartDate(), r.getTimeFrom()) ;
            DateTime resEnd = new DateTime(r.getEndDate(), r.getTimeTo()) ;

            if((resStart.compareTo(start) >= 0 && resEnd.compareTo(end)<=0)
                    || (resStart.compareTo(start) <= 0 && resEnd.compareTo(start)>0)
                    || (resStart.compareTo(end) < 0 && resEnd.compareTo(end)>=0) ){
                res.add(r) ;
            }
        }

        return res;

    }


    boolean checkPendingConstraint(Reservation r){
        if(r.getState() == ReservationState.Expired)
            return false ;

        DateTime reserved = new DateTime(r.getDateReserved(), r.getTimeReserved()) ;
        DateTime start = new DateTime(r.getStartDate(), r.getTimeFrom()) ;
        DateTime now = new DateTime(new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis())) ;

        int diff = start.compareTo(reserved) ;
        int tonow = now.compareTo(reserved) ;

        if(tonow > 0.30* diff){
            r.setState(ReservationState.Expired);
            return false ;
        }
        return true ;

    }

}
