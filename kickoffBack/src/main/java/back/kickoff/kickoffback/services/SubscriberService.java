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

    public boolean subscribe(Subscription subscription) {
        try {
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

    public boolean unsubscribe(Subscription subscription) {
        Long coid = subscription.getCoid();
        Long pid = subscription.getPid();
        Optional<Subscription> optional = subscriptionsRepository.findByCoidAndPid(coid, pid);
        // Checking whether a subscription is present or not.
        if (optional.isPresent()) {
            subscriptionsRepository.deleteById(optional.get().getId());
            return true;
        } else return false;
    }

    public String getPlayerSubscriptions(Long pid) {
        return new Gson().toJson(subscriptionsRepository.findByPid(pid));
    }

    public List<Subscription> getSubscriptionAnnouncements(Long pid) {
        return subscriptionsRepository.findByPid(pid);

    }

    public Integer getSubscribersCount(Long coid) {
        return subscriptionsRepository.countByCoid(coid);
    }

    public Boolean isSubscriber(Subscription subscription) {
        return subscriptionsRepository.existsByCoidAndPid(subscription.getCoid(), subscription.getPid());
    }
}
