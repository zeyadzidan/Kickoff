package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.model.CourtSchedule;
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
    
}
