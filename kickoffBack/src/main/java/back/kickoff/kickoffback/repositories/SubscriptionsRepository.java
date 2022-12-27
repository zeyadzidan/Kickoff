package back.kickoff.kickoffback.repositories;

import back.kickoff.kickoffback.model.Subscription;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionsRepository extends CrudRepository<Subscription, Long> {

    List<Subscription> findByPid(@Param("pid") Long pid);

    List<Subscription> findByCoid(@Param("coid") Long coid);

    Optional<Subscription> findByCoidAndPid(@Param("coid") Long coid, @Param("pid") Long pid);

    Boolean existsByCoidAndPid(@Param("coid") Long coid, @Param("pid") Long pid);

    Integer countByCoid(@Param("coid") Long coid);
}
