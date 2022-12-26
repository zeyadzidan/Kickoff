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

    @Query("SELECT S FROM Subscriptions S WHERE S.pid =: pid")
    List<Subscription> findByPid(@Param("pid") Long pid);

    @Query("SELECT S FROM Subscriptions S WHERE S.coid =: coid")
    List<Subscription> findByCoid(@Param("coid") Long coid);

    @Query("SELECT S FROM Subscriptions S WHERE S.coid =: coid AND S.pid =: pid")
    Optional<Subscription> findByCoidAndPid(@Param("coid") Long coid, @Param("pid") Long pid);
}
