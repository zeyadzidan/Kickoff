package back.kickoff.kickoffback.repositories;

import back.kickoff.kickoffback.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    public boolean existsById(Long id);
}
