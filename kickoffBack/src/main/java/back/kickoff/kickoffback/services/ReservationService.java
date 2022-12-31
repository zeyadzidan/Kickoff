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
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReservationService {


    public int calcTotalCost(LocalDate stDay, LocalDate endDay, LocalTime stTime, LocalTime endTime, Court court) {
        System.out.println("sd " + stDay + " ed " + endDay + " st " + stTime + " et " + endTime);
        CourtSchedule courtSchedule = court.getCourtSchedule();
        int mourningPrice = courtSchedule.getMorningCost();
        int nightPrice = courtSchedule.getNightCost();
        int totalCost;
        LocalTime morning = courtSchedule.getEndMorning().toLocalTime();
        System.out.println("morning " + morning);

        DateTime st = new DateTime(stDay,stTime) ;
        DateTime mid = new DateTime(stDay,morning) ;
        DateTime end = new DateTime(endDay,endTime) ;

        if (st.compareTo(mid)<0 && end.compareTo(mid) <= 0) { // morning
            System.out.println("morning");
            totalCost = mourningPrice * (end.compareTo(st));

        } else if (st.compareTo(mid)>=0 && end.compareTo(mid) > 0) {//night
            totalCost = nightPrice * (end.compareTo(st));
            System.out.println("night");
        } else {
            totalCost = mourningPrice * (mid.compareTo(st)) + nightPrice * (end.compareTo(mid));
            System.out.println("haf");
        }
        System.out.println(totalCost);

        return totalCost;
    }
    
}
