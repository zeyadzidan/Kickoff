package back.kickoff.kickoffback.repositories;

import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface PlayerRepositry extends JpaRepository<Player, Long> {
}
