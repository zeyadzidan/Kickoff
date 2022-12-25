package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.Announcement;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.Subscription;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import back.kickoff.kickoffback.repositories.SubscriptionsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriberService {
    private final SubscriptionsRepository subscriptionsRepository;
    private final PlayerRepository playerRepository;
    private final CourtOwnerRepository courtOwnerRepository;

    public SubscriberService(SubscriptionsRepository subscriptionsRepository,
                             PlayerRepository playerRepository,
                             CourtOwnerRepository courtOwnerRepository) {
        this.subscriptionsRepository = subscriptionsRepository;
        this.playerRepository = playerRepository;
        this.courtOwnerRepository = courtOwnerRepository;
    }

    public boolean subscribe(String jsonSubscription) throws JsonProcessingException {
        Subscription subscription = new ObjectMapper().readValue(jsonSubscription, Subscription.class);
        // Checking whether a subscription is present or not.
        if (subscriptionsRepository.findByPid(String.valueOf(subscription.getPid())).contains(subscription) &&
            subscriptionsRepository.findByCoid(String.valueOf(subscription.getCoid())).contains(subscription))
            return false;
        subscriptionsRepository.save(subscription);
        return true;
    }

    public boolean unsubscribe(String jsonSubscription) throws JsonProcessingException {
        Subscription subscription = new ObjectMapper().readValue(jsonSubscription, Subscription.class);
        // Checking whether a subscription is present or not.
        if (subscriptionsRepository.findByPid(String.valueOf(subscription.getPid())).contains(subscription) &&
            subscriptionsRepository.findByCoid(String.valueOf(subscription.getCoid())).contains(subscription)) {
            subscriptionsRepository.deleteById(subscription.getId());
            return true;
        } else return false;
    }

    public List<Subscription> getPlayerSubscriptions(String pid) {
        return subscriptionsRepository.findByPid(pid);
    }

    public List<Subscription> getCourtOwnerSubscriptions(String coid) {
        return subscriptionsRepository.findByCoid(coid);
    }

    public boolean isSubscriber(String coid, String pid) {
        return subscriptionsRepository.isSubscriber(coid, pid);
    }
}
