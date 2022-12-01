package back.kickoff.kickoffback.repositories;

import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.model.CourtOwner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CourtOwnerRepository extends CrudRepository<CourtOwner, Long> {
    Optional<CourtOwner> findByEmail(String email);
//    List<CourtOwner> getCourtOwnersByCourts(Set<Court> courts);
}
