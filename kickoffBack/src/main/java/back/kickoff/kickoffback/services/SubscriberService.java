package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.Subscription;
import back.kickoff.kickoffback.repositories.SubscriptionsRepository;
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
        // Checking whether a subscription is present or not.
        if (subscriptionsRepository.findByCoidAndPid(subscription.getCoid(), subscription.getPid()).isPresent())
            return false;
        subscriptionsRepository.save(subscription);
        return true;
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

    public List<Subscription> getPlayerSubscriptions(Long pid) {
        return subscriptionsRepository.findByPid(pid);
    }

    public Integer getSubscribersCount(Long coid) {
        return subscriptionsRepository.countByCoid(coid);
    }

    public Boolean isSubscriber(Subscription subscription) {
        return subscriptionsRepository.existsByCoidAndPid(subscription.getCoid(), subscription.getPid());
    }
}
