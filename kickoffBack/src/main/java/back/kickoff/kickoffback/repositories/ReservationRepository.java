package back.kickoff.kickoffback.repositories;

import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Component
@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

//    public boolean existsById(Long id);
}
