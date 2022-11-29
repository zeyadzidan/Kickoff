package back.kickoff.kickoffback.repositories;

import back.kickoff.kickoffback.model.CourtOwner;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CourtOwnerRepository extends CrudRepository<CourtOwner, Long> {
    Optional<CourtOwner> findByEmail(String email);
}
