package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.Reservation;
import back.kickoff.kickoffback.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository rr ;

    public void tryy(){
        Reservation r = new Reservation((long) 123,(long)12467,(long)124);
        rr.save(r) ;
        System.out.println("HAHAHAHAHAHAHAHAHA");
    }

}
