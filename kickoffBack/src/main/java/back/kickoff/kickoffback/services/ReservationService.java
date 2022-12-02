package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.Reservation;
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
        Reservation r = new Reservation(p,CourtId,courtOwnerId);
        r.setDate(d);
        r.setTimeFrom(from);
        r.setTimeTo(to);
        r.setState("booked");
        r.setMoneyPayed(50);
        r.setTotalCost(200) ;

        rr.save(r) ;
        System.out.println(r.toString());
        return r.getId();
    }

}
