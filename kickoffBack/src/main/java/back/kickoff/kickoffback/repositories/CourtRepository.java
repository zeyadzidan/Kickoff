package back.kickoff.kickoffback.repositories;

import back.kickoff.kickoffback.model.Court;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Component
@Repository
public interface CourtRepository extends CrudRepository<Court,Long> {


}
