package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.model.CourtSchedule;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.Reservation;
import back.kickoff.kickoffback.model.ReservationState;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import back.kickoff.kickoffback.repositories.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;

@Service
public class ReservationService {


    public int calcTotalCost(Date stDay, Date endDay, Time stTime, Time endTime, Court court) {
        System.out.println("sd " + stDay + " ed " + endDay + " st " + stTime + " et " + endTime);
        CourtSchedule courtSchedule = court.getCourtSchedule();
        int mourningPrice = courtSchedule.getMorningCost();
        int nightPrice = courtSchedule.getNightCost();
        int totalCost;
        Time morning = courtSchedule.getEndMorning();
        System.out.println("morning " + morning);

        if (morning.after(stTime) && (morning.after(endTime) || morning.equals(endTime)) && stDay.equals(endDay)) { // morning
            System.out.println("morning");

            totalCost = mourningPrice * (endTime.getHours() - stTime.getHours());
        } else if ((morning.before(stTime) || morning.equals(stTime)) && morning.before(endTime) && endDay.equals(stDay)) {//night
            totalCost = nightPrice * (endTime.getHours() - stTime.getHours());
            System.out.println("night");
        } else {
            totalCost = mourningPrice * (morning.getHours() - stTime.getHours()) + nightPrice * (endTime.getHours() - morning.getHours());
            System.out.println("haf");
        }
        System.out.println(totalCost);

        return totalCost;
    }


/*
    public Long tryy(Long CourtId, Long courtOwnerId){
        LitePlayer p = new LitePlayer();
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


 */
}
