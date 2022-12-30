package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.Commands.SubscriptionsCommands;
import back.kickoff.kickoffback.model.Subscription;
import back.kickoff.kickoffback.services.AnnouncementService;
import back.kickoff.kickoffback.services.SubscriberService;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/subscriber")
public class SubscriberController {
    private final SubscriberService subscriberService;

    private final AnnouncementService announcementService;

    public SubscriberController(SubscriberService subscriberService, AnnouncementService announcementService) {
        this.subscriberService = subscriberService;
        this.announcementService = announcementService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Boolean> subscribe(@RequestBody String request) {
        Subscription subscription = SubscriptionsCommands.constructSubscription(request);
        if (subscriberService.subscribe(subscription))
                return new ResponseEntity<>(Boolean.TRUE, HttpStatus.CREATED);
        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<Boolean> unsubscribe(@RequestBody String request) {
        Subscription subscription = SubscriptionsCommands.constructSubscription(request);
        assert subscription != null;
        return (subscriberService.unsubscribe(subscription))
                ? new ResponseEntity<>(Boolean.TRUE, HttpStatus.CREATED)
                : new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/isSubscriber")
    public ResponseEntity<Boolean> isSubscriber(@RequestBody String request) {
        Subscription subscription = SubscriptionsCommands.constructSubscription(request);
        assert subscription != null;
        return new ResponseEntity<>(subscriberService.isSubscriber(subscription), HttpStatus.OK);
    }

    @GetMapping("/playerSubscriptions/{pid}")
    public ResponseEntity<Object> getPlayerSubscriptions(@PathVariable Long pid) {
        String subscriptions = subscriberService.getPlayerSubscriptions(pid);
        return (subscriberService.getPlayerSubscriptions(pid) != null)
                ? new ResponseEntity<>(new Gson().toJson(subscriptions), HttpStatus.CREATED)
                : new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getSubscribersCount/{coid}")
    public ResponseEntity<Integer> getSubscribersCount(@PathVariable Long coid) {
        return new ResponseEntity<>(subscriberService.getSubscribersCount(coid), HttpStatus.OK);
    }

    @GetMapping("/getAnnouncementsBySubscriptions/{pid}")
    public ResponseEntity<Object> getSubscriptionAnnouncements(@PathVariable Long pid) {
        List<Subscription> subscriptions = subscriberService.getSubscriptionAnnouncements(pid);
        String body = announcementService.getSubscriptionAnnouncements(subscriptions);
        return (!body.equals("No subscriptions"))
                ? new ResponseEntity<>(body, HttpStatus.OK)
                : new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
    }
}
