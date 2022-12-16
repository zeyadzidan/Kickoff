package back.kickoff.kickoffback.repositories;

import back.kickoff.kickoffback.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {
    Optional<Player> findByEmail(String email);
}
