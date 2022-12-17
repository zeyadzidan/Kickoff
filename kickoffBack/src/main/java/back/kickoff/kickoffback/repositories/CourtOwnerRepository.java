package back.kickoff.kickoffback.repositories;

import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.model.CourtOwner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CourtOwnerRepository extends CrudRepository<CourtOwner, Long> {
    Optional<CourtOwner> findByEmail(String email);
//    @Query("select c from CourtOwner c where c.userName = ?1")
@Query("select c from CourtOwner c where c.userName like ?1")
Optional<CourtOwner> findByUserNameLike(String x);
    @Query("select  c from CourtOwner c where c.userName like ?3 or c.userName like ?4 ORDER BY (sqrt(power(c.xAxis - ?1 , 2) + power(c.yAxis - ?2 , 2))) ASC")
    List<CourtOwner> searchNearestCourtOwner(Double x_axis, Double y_axis, String keyword, String beginKeyword);
//    List<CourtOwner> getCourtOwnersByCourts(Set<Court> courts);
}
