package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.model.CourtSchedule;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.Reservation;
import back.kickoff.kickoffback.repositories.CourtRepository;
import back.kickoff.kickoffback.repositories.PlayerRepositry;
import back.kickoff.kickoffback.repositories.ReservationRepository;
import back.kickoff.kickoffback.repositories.ScheduleRepository;
import back.kickoff.kickoffback.services.ReservationService;
import back.kickoff.kickoffback.services.ScheduleAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class RController {

    @Autowired
    ReservationRepository rr ;
    @Autowired
    CourtRepository cr ;
    @Autowired
    PlayerRepositry pr ;
    @Autowired
    ScheduleRepository sr ;

    public RController(ReservationRepository rr, CourtRepository cr, PlayerRepositry pr, ScheduleRepository sr){
        this.rr =rr;
        this.cr = cr;
        this.pr = pr ;
        this.sr = sr ;
    }


    @Bean
    public void tryy(){


        //try {


            Court c = new Court((long) 1243, 1, "5");
            cr.save(c);

            ReservationService rs = new ReservationService(rr, pr);
            Long id = rs.tryy(c.id, c.Court_Owner_id);
            Optional<Reservation> reservation = rr.findById(id);
            System.out.println(reservation.get().toString());

            Time startWorkingHours = new Time(10, 0, 0);
            Time endWorkingHours = new Time(23, 0, 0);
            Time endMorning = new Time(16, 0, 0);
            CourtSchedule courtSchedule = new CourtSchedule(c.id, startWorkingHours, endWorkingHours, endMorning, 200, 200);
            sr.save(courtSchedule);

            ScheduleAgent scheduleAgent = new ScheduleAgent(sr, rr);
            Date df = new Date(2022, 12, 1);
            Date dt = new Date(2022, 12, 2);

            List<Reservation> res = scheduleAgent.getScheduleBetween(df, dt, startWorkingHours, endWorkingHours, c.id);
            for (Reservation r : res) {
                System.out.println(r.toString());
            }
        //}catch (Exception e){
          //  System.out.println(e);
        //}



    }

}
