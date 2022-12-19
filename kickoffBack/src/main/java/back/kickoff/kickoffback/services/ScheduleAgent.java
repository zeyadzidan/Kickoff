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
        System.out.println("start " + start.toString());
        System.out.println("end " + end.toString());

        for(Reservation r: schedule.getBookedReservations()){
            DateTime resStart = new DateTime(r.getStartDate(), r.getTimeFrom()) ;
            DateTime resEnd = new DateTime(r.getEndDate(), r.getTimeTo()) ;
            System.out.println("resStart " + resStart.toString());
            System.out.println("resEnd " + resEnd.toString());
            System.out.println("resStart.compareTo(start) " + resStart.compareTo(start));
            System.out.println("resEnd.compareTo(end) " + resEnd.compareTo(end));
            System.out.println("resEnd.compareTo(start) " + resEnd.compareTo(start));
            System.out.println("resStart.compareTo(end) " + resStart.compareTo(end));


            if((resStart.compareTo(start) >= 0 && resEnd.compareTo(end)<=0)
                    || (resStart.compareTo(start) <= 0 && resEnd.compareTo(start)>0)
                    || (resStart.compareTo(end) < 0 && resEnd.compareTo(end)>=0) ){
                res.add(r) ;
            }

        }
        ArrayList<Reservation> toRemove = new ArrayList<Reservation>() ;
        for(Reservation r: schedule.getPendingReservations()){
            if(!checkPendingConstraint(r)){
                System.out.println("baaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa ");

                //toRemove.add(r) ;
                //continue;
            }

            DateTime resStart = new DateTime(r.getStartDate(), r.getTimeFrom()) ;
            DateTime resEnd = new DateTime(r.getEndDate(), r.getTimeTo()) ;

            System.out.println("resStart " + resStart.toString());
            System.out.println("resEnd " + resEnd.toString());
            System.out.println("resStart.compareTo(start) " + resStart.compareTo(start));
            System.out.println("resEnd.compareTo(end) " + resEnd.compareTo(end));
            System.out.println("resEnd.compareTo(start) " + resEnd.compareTo(start));
            System.out.println("resStart.compareTo(end) " + resStart.compareTo(end));

            if((resStart.compareTo(start) >= 0 && resEnd.compareTo(end)<=0)
                    || (resStart.compareTo(start) <= 0 && resEnd.compareTo(start)>0)
                    || (resStart.compareTo(end) < 0 && resEnd.compareTo(end)>=0) ){
                res.add(r) ;
            }
        }

        for(Reservation r: toRemove){
            schedule.getPendingReservations().remove(r) ;
            schedule.getHistory().add(r) ;
            rr.save(r) ;
            sr.save(schedule) ;
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
