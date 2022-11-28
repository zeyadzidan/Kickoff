package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.Reservation;
import back.kickoff.kickoffback.repositories.PlayerRepositry;
import back.kickoff.kickoffback.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void tryy(){
        Player p = new Player();
        pr.save(p) ;
        Reservation r = new Reservation(p,(long)12467,(long)124);
        rr.save(r) ;
        System.out.println(r.toString());
    }

}
