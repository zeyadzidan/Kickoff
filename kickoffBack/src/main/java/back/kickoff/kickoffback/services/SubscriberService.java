package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.Subscription;
import back.kickoff.kickoffback.repositories.SubscriptionsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.util.List;

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
            if (subscriptionsRepository.findByPid(String.valueOf(subscription.getPid())).contains(subscription) &&
                    subscriptionsRepository.findByCoid(String.valueOf(subscription.getCoid())).contains(subscription))
                return false;
            subscriptionsRepository.save(subscription);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public boolean unsubscribe(String jsonSubscription) {
        try {
            Subscription subscription = new ObjectMapper().readValue(jsonSubscription, Subscription.class);
            // Checking whether a subscription is present or not.
            if (subscriptionsRepository.findByPid(String.valueOf(subscription.getPid())).contains(subscription) &&
                    subscriptionsRepository.findByCoid(String.valueOf(subscription.getCoid())).contains(subscription)) {
                subscriptionsRepository.deleteById(subscription.getId());
                return true;
            } else return false;
        } catch (Exception ignored) {
            return false;
        }
    }

    public String getPlayerSubscriptions(String pid) {
        return new Gson().toJson(subscriptionsRepository.findByPid(pid));
    }

    public List<Subscription> getSubscriptionAnnouncements(String pid) {
        return subscriptionsRepository.findByPid(pid);
    }

    public boolean isSubscriber(String coid, String pid) {
        return subscriptionsRepository.isSubscriber(coid, pid);
    }
}
