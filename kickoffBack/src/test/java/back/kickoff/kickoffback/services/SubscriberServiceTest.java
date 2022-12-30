package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.Subscription;
import back.kickoff.kickoffback.repositories.AnnouncementRepository;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.SubscriptionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubscriberServiceTest {
    SubscriberService subscriberService;

    AnnouncementService announcementService;

    @Mock
    SubscriptionsRepository subscriptionsRepository;

    @Mock
    AnnouncementRepository announcementRepository;

    @Mock
    CourtOwnerRepository courtOwnerRepository;

    @Mock
    Subscription subscription;

    @Mock
    List<Subscription> subscriptions;

    @BeforeEach
    void setSubscriberService() {
        MockitoAnnotations.openMocks(this);
        subscriberService = new SubscriberService(subscriptionsRepository);
        announcementService = new AnnouncementService(courtOwnerRepository, announcementRepository);
    }

    @BeforeEach
    void setMockSubscription() {
        Mockito.when(subscription.getId()).thenReturn(77L);
        Mockito.when(subscription.getPid()).thenReturn(77L);
        Mockito.when(subscription.getCoid()).thenReturn(77L);
    }

    @BeforeEach
    void setMockSubscriptions() {
        subscriptions = new ArrayList<>();
        subscriptions.add(new Subscription());
    }

    /*
     * Subscribe Tests -------------------------------------------------------------------------------------------------
     * */

    /**
     * Test the subscription of a non-existing subscription
     */
    @Test
    void subscribeSuccessTest() {
        Boolean actual = subscriberService.subscribe(subscription);
        assertEquals(Boolean.TRUE, actual);
    }

    /**
     * Test the subscription of an already existing subscription
     */
    @Test
    void subscribeInstanceExistsTest() {
        Mockito.when(subscriptionsRepository.findByCoidAndPid(subscription.getCoid(), subscription.getPid())).thenReturn(Optional.of(new Subscription()));
        Boolean actual = subscriberService.subscribe(subscription);
        assertEquals(Boolean.FALSE, actual);
    }

    /*
     * Unsubscribe Tests -----------------------------------------------------------------------------------------------
     * */

    /**
     * Test a successful unsubscribe request
     */
    @Test
    void unsubscribeSuccessTest() {
        Mockito.when(subscriptionsRepository.findByCoidAndPid(subscription.getCoid(), subscription.getPid())).thenReturn(Optional.of(subscription));
        Boolean actual = subscriberService.unsubscribe(subscription);
        assertEquals(Boolean.TRUE, actual);
    }

    /**
     * Test a non-existing subscription for unsubscribe request.
     */
    @Test
    void notFoundUnsubscribeTest() {
        Mockito.when(subscriptionsRepository.findByCoidAndPid(subscription.getCoid(), subscription.getPid())).thenReturn(Optional.empty());
        Boolean actual = subscriberService.unsubscribe(subscription);
        assertEquals(Boolean.FALSE, actual);
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
        Mockito.when(subscriptionsRepository.existsByCoidAndPid(subscription.getCoid(), subscription.getPid())).thenReturn(isSubscriber);
        assertEquals(isSubscriber, subscriberService.isSubscriber(subscription));
    }

    /*
     * getPlayerSubscriptions Tests ------------------------------------------------------------------------------------
     * */

    /**
     * Test a successful get player subscriptions
     */
    @Test
    void getPlayerSubscriptionsSuccessTest() {
        Mockito.when(subscriptionsRepository.findByPid(subscription.getPid())).thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<>(), subscriberService.getPlayerSubscriptions(subscription.getPid()));
    }

    /*
     * getSubscribersCount Tests ---------------------------------------------------------------------------------------
     * */

    /**
     * Test a successful get-subscribers-count request
     */
    @Test
    void getSubscribersCountTest() {
        Mockito.when(subscriptionsRepository.countByCoid(subscription.getCoid())).thenReturn(50);
        assertEquals(50, subscriberService.getSubscribersCount(subscription.getCoid()));
    }
}
