package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.model.CourtSchedule;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.Reservation;
import back.kickoff.kickoffback.model.ReservationState;
import back.kickoff.kickoffback.repositories.PlayerRepositry;
import back.kickoff.kickoffback.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;

@Service
public class ReservationService {

    @Autowired
    private final ReservationRepository rr ;

    @Autowired
    private final PlayerRepositry pr ;


    public int calcTotalCost(Date stDay,Date endDay, Time stTime, Time endTime, Court court)
    {
        CourtSchedule courtSchedule = court.getCourtSchedule();
        int mourningPrice = courtSchedule.getMorningCost();
        int nightPrice = courtSchedule.getNightCost();
        int totalCost = 0;
        Time morning  = courtSchedule.getEndMorning();
        if(morning.compareTo(stTime) > 0 && morning.compareTo(endTime) >= 0 && stDay.equals(endDay)){ // morning
            totalCost = mourningPrice * (endTime.getHours()-stTime.getHours()) ;
        }else if(morning.compareTo(stTime) <= 0 && (morning.compareTo(endTime) < 0 || !endDay.equals(stDay)) ) {//night
            totalCost = nightPrice* ((endTime.getHours()-stTime.getHours())+24)%24 ;
        }else{
            totalCost = mourningPrice * (morning.getHours()-stTime.getHours()) + nightPrice*((endTime.getHours()-morning.getHours())+24)%24 ;
        }

        return totalCost ;
    }


    public ReservationService(ReservationRepository rr, PlayerRepositry pr) {
        this.rr = rr;
        this.pr = pr;
    }

    public Long tryy(Long CourtId, Long courtOwnerId){
        Player p = new Player();
        pr.save(p) ;
        Date d = new Date(2022,12,1) ;
        Time from = new Time(10,0,0);
        Time to = new Time(12,0,0);
        Reservation r = new Reservation(p, CourtId, courtOwnerId);
        r.setStartDate(d);
        r.setTimeFrom(from);
        r.setTimeTo(to);
        r.setState(ReservationState.Booked);
        r.setMoneyPayed(50);
        r.setTotalCost(200) ;

        rr.save(r) ;
        System.out.println(r.toString());
        return r.getId();
    }

}
