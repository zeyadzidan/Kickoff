package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.model.Reservation;
import back.kickoff.kickoffback.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class RController {

    @Autowired
    ReservationService rs ;

    @Bean
    public void tryy(){
        rs.tryy();
    }
}
