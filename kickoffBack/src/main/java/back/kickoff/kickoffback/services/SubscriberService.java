package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.Subscription;
import back.kickoff.kickoffback.repositories.SubscriptionsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriberService {
    private final SubscriptionsRepository subscriptionsRepository;

    public SubscriberService(SubscriptionsRepository subscriptionsRepository) {
        this.subscriptionsRepository = subscriptionsRepository;
    }

    public boolean subscribe(String jsonSubscription) {
        try {
            Subscription subscription = new ObjectMapper().readValue(jsonSubscription, Subscription.class);
            // Checking whether a subscription is present or not.
            if (subscriptionsRepository.findByPid(subscription.getPid()).contains(subscription) &&
                    subscriptionsRepository.findByCoid(subscription.getCoid()).contains(subscription))
                return false;
            subscriptionsRepository.save(subscription);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public boolean unsubscribe(String jsonSubscription) {
        Subscription subscription = getSubscription(jsonSubscription);
        // Checking whether a subscription is present or not.
        if (subscription != null) {
            subscriptionsRepository.deleteById(subscription.getId());
            return true;
        } else return false;
    }

    private Subscription getSubscription(String json) {
        try {
            Subscription subscription = new ObjectMapper().readValue(json, Subscription.class);
            Long coid = subscription.getCoid();
            Long pid = subscription.getPid();
            Optional<Subscription> optional = subscriptionsRepository.findByCoidAndPid(coid, pid);
            if (optional.isPresent())
                return optional.get();
        } catch (Exception ignored) {
            return null;
        }
        return null;
    }

    public String getPlayerSubscriptions(Long pid) {
        return new Gson().toJson(subscriptionsRepository.findByPid(pid));
    }

    public List<Subscription> getSubscriptionAnnouncements(Long pid) {
        return subscriptionsRepository.findByPid(pid);
    }

    public Boolean isSubscriber(Long coid, Long pid) {
        return subscriptionsRepository.findByCoidAndPid(coid, pid).isPresent();
    }
}
