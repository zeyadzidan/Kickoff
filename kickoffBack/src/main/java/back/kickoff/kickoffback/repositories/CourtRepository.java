package back.kickoff.kickoffback.repositories;

import back.kickoff.kickoffback.model.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Component
@Repository
public interface CourtRepository extends JpaRepository<Court,Long> {

}
