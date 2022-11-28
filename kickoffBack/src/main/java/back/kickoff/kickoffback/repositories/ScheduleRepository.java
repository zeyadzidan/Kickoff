package back.kickoff.kickoffback.repositories;

import back.kickoff.kickoffback.model.CourtSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<CourtSchedule, Long> {
}
