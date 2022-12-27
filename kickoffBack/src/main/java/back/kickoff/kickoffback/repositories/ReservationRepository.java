package back.kickoff.kickoffback.repositories;

import back.kickoff.kickoffback.model.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    public List<Reservation> findAllByPid(@Param("pid") Long pid);
//    public boolean existsById(Long id);
}
