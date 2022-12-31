package back.kickoff.kickoffback.repositories;

import back.kickoff.kickoffback.model.Party;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface PartyRepository extends CrudRepository<Party, Long> {

    boolean existsByReservation(Reservation reservation);
    boolean existsByPlayerJoined(Player player);
}
