package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.model.CourtSchedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {
    ReservationService reservationService ;

    @BeforeEach
    void startUp(){
        reservationService = new ReservationService();
    }

    @Test
    void calcTotalCost() {
        Time startWorkingHours = new Time(9,0,0) ;
        Time endWorkingHours = new Time(23,0,0) ;
        Time endMorning = new Time(18,0,0) ;

        CourtSchedule courtSchedule = new CourtSchedule(startWorkingHours,endWorkingHours,endMorning,
                150, 200,1);
        Court court = new Court() ;
        court.setCourtSchedule(courtSchedule);

        Date d = new Date(2022,12,5) ;
        Time start = new Time(17,0,0) ;
        Time end = new Time(19,0,0) ;
        int cost = reservationService.calcTotalCost(d,d,start,end,court) ;

        assertEquals(cost, 150+200);
    }
}