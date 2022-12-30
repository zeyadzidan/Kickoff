package back.kickoff.kickoffback.repositories;

import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {

    //@Query("SELECT r FROM rating r WHERE r.court_owner_id = coid")
    List<Rating> findByCourtOwner(CourtOwner courtOwner) ;


    //@Query("SELECT r FROM rating r WHERE r.player_id = pid AND r.court_owner_id = coid")
    List<Rating> findByPlayerAndCourtOwner(Player player,  CourtOwner courtOwner);

}
