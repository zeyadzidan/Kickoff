package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.Commands.FrontEnd.AnnouncementFrontend;
import back.kickoff.kickoffback.Commands.Operation.SubscriptionsCommands;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubscriberControllerTest {
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

    /**
     * Auxiliary method to construct requests
     */
    String constructRequest(Long pid, Long coid) {
        HashMap<String, Long> subscription = new HashMap<>();
        subscription.put("pid", pid);
        subscription.put("coid", coid);
        return new Gson().toJson(subscription);
    }

    /*
     * Subscribe Tests -------------------------------------------------------------------------------------------------
     * */

    @Test
    void subscribeSuccessTest() {
        Long pid = 77L, coid = 77L;
        String request = constructRequest(pid, coid);
        Subscription subscription = SubscriptionsCommands.constructSubscription(request);
        assert subscription != null;
        Mockito.when(subscriberService.subscribe(subscription)).thenReturn(true);
        ResponseEntity<Boolean> response = subscriberController.subscribe(request);
        assertEquals(new ResponseEntity<>(Boolean.TRUE, HttpStatus.CREATED), response);
    }

    /**
     * Test the subscription of an already existing subscription
     */
    @Test
    void subscribeInstanceExistsTest() {
        String request = constructRequest(77L, 77L);
        Subscription subscription = SubscriptionsCommands.constructSubscription(request);
        assert subscription != null;
        Mockito.when(subscriberService.subscribe(subscription)).thenReturn(false);
        ResponseEntity<Boolean> response = subscriberController.subscribe(request);
        assertEquals(new ResponseEntity<>(Boolean.FALSE, HttpStatus.CONFLICT), response);
    }

    /**
     * Test the subscription of a missing pid request
     */
    @Test
    void missingPidSubscribeTest() {
        Long pid = null;
        Long coid = 77L;
        String request = constructRequest(pid, coid);
        missingParamSubscribe(request);
    }

    /**
     * Test the subscription of a missing coid request
     */
    @Test
    void missingCoidSubscribeTest() {
        Long pid = 77L;
        Long coid = null;
        String request = constructRequest(pid, coid);
        missingParamSubscribe(request);
    }

    /**
     * Auxiliary method to help with subscribe using missing parameters test
     */
    void missingParamSubscribe(String request) {
        Subscription subscription = SubscriptionsCommands.constructSubscription(request);
        assert subscription != null;
        Mockito.when(subscriberService.subscribe(subscription)).thenThrow(new RuntimeException());
        ResponseEntity<Boolean> response = subscriberController.subscribe(request);
        assertEquals(new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR), response);
    }


    /*
     * Unsubscribe Tests -----------------------------------------------------------------------------------------------
     * */

    /**
     * Test a successful unsubscribe request
     */
    @Test
    void unsubscribeSuccessTest() {
        String request = constructRequest(77L, 77L);
        Subscription subscription = SubscriptionsCommands.constructSubscription(request);
        assert subscription != null;
        Mockito.when(subscriberService.unsubscribe(subscription)).thenReturn(true);
        ResponseEntity<Boolean> response = subscriberController.unsubscribe(request);
        assertEquals(new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK), response);
    }

    /**
     * Test a non-existing subscription for unsubscribe request.
     */
    @Test
    void notFoundUnsubscribeTest() {
        String request = constructRequest(66L, 71L);
        Subscription subscription = SubscriptionsCommands.constructSubscription(request);
        assert subscription != null;
        Mockito.when(subscriberService.unsubscribe(subscription)).thenReturn(false);
        ResponseEntity<Boolean> response = subscriberController.unsubscribe(request);
        assertEquals(new ResponseEntity<>(Boolean.FALSE, HttpStatus.NOT_FOUND), response);
    }

    /**
     * Test a malicious unsubscribe request (missing pid or coid)
     */
    @Test
    void maliciousUnsubscribeTest() {
        String request = constructRequest(null, null);
        Subscription subscription = SubscriptionsCommands.constructSubscription(request);
        assert subscription != null;
        Mockito.when(subscriberService.unsubscribe(subscription)).thenThrow(new RuntimeException());
        ResponseEntity<Boolean> response = subscriberController.unsubscribe(request);
        assertEquals(new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR), response);
    }

    /*
     * isSubscriber Tests ----------------------------------------------------------------------------------------------
     * */

    /**
     * Test if a player is a subscriber to court owner.
     */
    @Test
    void isSubscriberTest() {
        isSubscriberAux(Boolean.TRUE);
    }

    /**
     * Test if a player is NOT a subscriber to court owner.
     */
    @Test
    void isNotSubscriberTest() {
        isSubscriberAux(Boolean.FALSE);
    }

    /**
     * Auxiliary method to help with isSubscriberTest
     */
    void isSubscriberAux(Boolean isSubscriber) {
        String request = constructRequest(77L, 77L);
        Subscription subscription = SubscriptionsCommands.constructSubscription(request);
        assert subscription != null;
        Mockito.when(subscriberService.isSubscriber(subscription)).thenReturn(isSubscriber);
        ResponseEntity<Boolean> response = subscriberController.isSubscriber(request);
        assertEquals(new ResponseEntity<>(isSubscriber, HttpStatus.OK), response);
    }

    /**
     * Test a malicious isSubscriber request (missing pid or coid)
     */
    @Test
    void isSubscriberMaliciousTest() {
        String request = constructRequest(null, null);
        Subscription subscription = SubscriptionsCommands.constructSubscription(request);
        assert subscription != null;
        Mockito.when(subscriberService.isSubscriber(subscription)).thenThrow(new RuntimeException());
        ResponseEntity<Boolean> response = subscriberController.isSubscriber(request);
        assertEquals(new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR), response);
    }

    /*
     * getPlayerSubscriptions Tests ------------------------------------------------------------------------------------
     * */

    /**
     * Test a successful get player subscriptions request
     */
    @Test
    void getPlayerSubscriptionsSuccessTest() {
        Long pid = 77L;
        List<Subscription> subscriptions = new ArrayList<>();
        Subscription subscription = new Subscription();
        subscriptions.add(subscription);
        Mockito.when(subscriberService.getPlayerSubscriptions(pid)).thenReturn(subscriptions);
        ResponseEntity<Object> response = subscriberController.getPlayerSubscriptions(pid);
        assertEquals(new ResponseEntity<>(new Gson().toJson(subscriptions), HttpStatus.OK), response);
    }

    /**
     * Test a successful empty get player subscriptions request
     */
    @Test
    void getPlayerSubscriptionsEmptyTest() {
        Long pid = 77L;
        Mockito.when(subscriberService.getPlayerSubscriptions(pid)).thenReturn(new ArrayList<>());
        ResponseEntity<Object> response = subscriberController.getPlayerSubscriptions(pid);
        assertEquals(new ResponseEntity<>(Boolean.FALSE, HttpStatus.NOT_FOUND), response);
    }

    /**
     * Test a malicious get player subscriptions request
     */
    @Test
    void maliciousGetPlayerSubscriptionsTest() {
        Long pid = null;
        Mockito.when(subscriberService.getPlayerSubscriptions(pid)).thenThrow(new RuntimeException());
        ResponseEntity<Object> response = subscriberController.getPlayerSubscriptions(pid);
        assertEquals(new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR), response);
    }

    /*
     * getSubscribersCount Tests ---------------------------------------------------------------------------------------
     * */

    /**
     * Test a successful get-subscribers-count request
     */
    @Test
    void getSubscribersCountTest() {
        Long coid = 77L;
        Mockito.when(subscriberService.getSubscribersCount(coid)).thenReturn(50);
        ResponseEntity<Integer> response = subscriberController.getSubscribersCount(coid);
        assertEquals(new ResponseEntity<>(50, HttpStatus.OK), response);
    }

    /**
     * Test a malicious get-subscribers-count request
     */
    @Test
    void maliciousGetSubscribersCountTest() {
        Long coid = null;
        Mockito.when(subscriberService.getSubscribersCount(coid)).thenThrow(new RuntimeException());
        ResponseEntity<Integer> response = subscriberController.getSubscribersCount(coid);
        assertEquals(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR), response);
    }

    /*
     * getAnnouncementsBySubscriptions Tests ---------------------------------------------------------------------------
     * */

    /**
     * Test an empty get-subscriptions-by-announcements request response
     */
    @Test
    void getAnnouncementsSuccessfulTest() {
        Long pid = 77L;
        List<Subscription> subscriptions = new ArrayList<>();
        List<AnnouncementFrontend> announcements = new ArrayList<>();
        announcements.add(new AnnouncementFrontend());
        Mockito.when(announcementService.getSubscriptionAnnouncements(subscriptions)).thenReturn(announcements);
        ResponseEntity<Object> response = subscriberController.getSubscriptionAnnouncements(pid);
        assertEquals(new ResponseEntity<>(new Gson().toJson(announcements), HttpStatus.OK), response);
    }

    /**
     * Test an empty get-subscriptions-by-announcements request response
     */
    @Test
    void getAnnouncementsEmptyTest() {
        Long pid = 77L;
        Mockito.when(announcementService.getSubscriptionAnnouncements(new ArrayList<>())).thenReturn(new ArrayList<>());
        ResponseEntity<Object> response = subscriberController.getSubscriptionAnnouncements(pid);
        assertEquals(new ResponseEntity<>(new Gson().toJson(new ArrayList<>()), HttpStatus.NOT_FOUND), response);
    }

    /**
     * Test a malicious get-subscriptions-by-announcements request
     */
    @Test
    void getAnnouncementsMaliciousTest() {
        Long pid = null;
        Mockito.when(announcementService.getSubscriptionAnnouncements(new ArrayList<>())).thenThrow(new RuntimeException());
        ResponseEntity<Object> response = subscriberController.getSubscriptionAnnouncements(pid);
        assertEquals(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR), response);
    }
}
