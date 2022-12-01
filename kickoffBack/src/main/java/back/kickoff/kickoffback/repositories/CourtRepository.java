package back.kickoff.kickoffback.repositories;

import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.model.CourtOwner;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CourtRepository extends CrudRepository<Court, Long> {
}
