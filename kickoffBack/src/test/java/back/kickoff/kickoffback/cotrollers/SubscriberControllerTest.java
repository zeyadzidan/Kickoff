package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.Commands.SubscriptionsCommands;
import back.kickoff.kickoffback.model.Subscription;
import back.kickoff.kickoffback.services.AnnouncementService;
import back.kickoff.kickoffback.services.SubscriberService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubscriberControllerTest {
    SubscriberController subscriberController;
    @Mock
    SubscriberService subscriberService;
    @Mock
    AnnouncementService announcementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        subscriberController = new SubscriberController(subscriberService, announcementService);
    }

    @Test
    void subscribeSuccess() {
        Long id = 77L, pid = 77L, coid = 77L;
        String request = constructRequest(id, pid, coid);
        Subscription subscription = SubscriptionsCommands.constructSubscription(request);
        Mockito.when(subscriberService.subscribe(subscription)).thenReturn(true);
        ResponseEntity<Boolean> response = subscriberController.subscribe(request);
        assertEquals(new ResponseEntity<>(Boolean.TRUE, HttpStatus.CREATED), response);
    }

    String constructRequest(Long id, Long pid, Long coid) {
        HashMap<String, Long> subscription = new HashMap<>();
        subscription.put("id", id);
        subscription.put("pid", pid);
        subscription.put("coid", coid);
        return new Gson().toJson(subscription);
    }

    @Test
    void subscribeFailure() {

    }
}
