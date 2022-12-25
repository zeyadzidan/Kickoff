package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.model.Subscription;
import back.kickoff.kickoffback.services.SubscriberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
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

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestBody String jsonSubscription) throws JsonProcessingException {
        return (subscriberService.subscribe(jsonSubscription))
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(@RequestBody String jsonSubscription) throws JsonProcessingException {
        return (subscriberService.unsubscribe(jsonSubscription))
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/playerSubscriptions/{pid}")
    public ResponseEntity<String> getPlayerSubscriptions(@PathVariable String pid) {
        List<Subscription> subscriptions = subscriberService.getCourtOwnerSubscriptions(pid);
        return (subscriberService.getPlayerSubscriptions(pid) != null)
                ? new ResponseEntity<>(new Gson().toJson(subscriptions), HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/courtOwnerSubscription/{coid}")
    public ResponseEntity<String> getCourtOwnerSubscriptions(@PathVariable String coid) {
        List<Subscription> subscriptions = subscriberService.getCourtOwnerSubscriptions(coid);
        return (subscriptions != null)
                ? new ResponseEntity<>(new Gson().toJson(subscriptions), HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/isSubscriber")
    public ResponseEntity<Boolean> isSubscriber(String sub) throws JSONException {
        JSONObject subscription = new JSONObject(sub);
        String coid = subscription.getString("coid");
        String pid = subscription.getString("pid");
        return new ResponseEntity<>(subscriberService.isSubscriber(coid, pid), HttpStatus.OK);
    }

    /*
    @PostMapping("/getSubscriptionAnnouncements")
    public ResponseEntity<String> getSubscriptionAnnouncements(String sub) throws JSONException {
        JSONObject subscription = new JSONObject(sub);
        String coid = subscription.getString("coid");
        String pid = subscription.getString("pid");
        return (subscriberService.getSubscriptionAnnouncements(subscription) != null)
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    */
}
