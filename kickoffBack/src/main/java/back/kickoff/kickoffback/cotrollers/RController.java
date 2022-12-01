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


}
