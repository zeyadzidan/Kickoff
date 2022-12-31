package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.Commands.FrontEnd.AnnouncementFrontend;
import back.kickoff.kickoffback.Commands.Operation.SubscriptionsCommands;
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
        try {
            Subscription subscription = SubscriptionsCommands.constructSubscription(request);
            assert subscription != null;
            return subscriberService.subscribe(subscription)
                    ? new ResponseEntity<>(Boolean.TRUE, HttpStatus.CREATED)
                    : new ResponseEntity<>(Boolean.FALSE, HttpStatus.CONFLICT);
        } catch (Exception ignored) {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<Boolean> unsubscribe(@RequestBody String request) {
        try {
            Subscription subscription = SubscriptionsCommands.constructSubscription(request);
            assert subscription != null;
            return (subscriberService.unsubscribe(subscription))
                    ? new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK)
                    : new ResponseEntity<>(Boolean.FALSE, HttpStatus.NOT_FOUND);
        } catch (Exception ignored) {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/isSubscriber")
    public ResponseEntity<Boolean> isSubscriber(@RequestBody String request) {
        try {
            Subscription subscription = SubscriptionsCommands.constructSubscription(request);
            assert subscription != null;
            return new ResponseEntity<>(subscriberService.isSubscriber(subscription), HttpStatus.OK);
        } catch (Exception ignored) {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/playerSubscriptions/{pid}")
    public ResponseEntity<Object> getPlayerSubscriptions(@PathVariable Long pid) {
        try {
            List<Subscription> subscriptions = subscriberService.getPlayerSubscriptions(pid);
            return (!subscriptions.isEmpty())
                    ? new ResponseEntity<>(new Gson().toJson(subscriptions), HttpStatus.OK)
                    : new ResponseEntity<>(Boolean.FALSE, HttpStatus.NOT_FOUND);
        } catch (Exception ignored) {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getSubscribersCount/{coid}")
    public ResponseEntity<Integer> getSubscribersCount(@PathVariable Long coid) {
        try {
            return new ResponseEntity<>(subscriberService.getSubscribersCount(coid), HttpStatus.OK);
        } catch (Exception ignored) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAnnouncementsBySubscriptions/{pid}")
    public ResponseEntity<Object> getSubscriptionAnnouncements(@PathVariable Long pid) {
        try {
            List<Subscription> subscriptions = subscriberService.getPlayerSubscriptions(pid);
            List<AnnouncementFrontend> announcements = announcementService.getSubscriptionAnnouncements(subscriptions);
            return (!announcements.isEmpty())
                    ? new ResponseEntity<>(new Gson().toJson(announcements), HttpStatus.OK)
                    : new ResponseEntity<>(new Gson().toJson(announcements), HttpStatus.NOT_FOUND);
        } catch (Exception ignored) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
