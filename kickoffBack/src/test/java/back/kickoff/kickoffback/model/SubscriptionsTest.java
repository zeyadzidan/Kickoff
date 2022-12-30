package back.kickoff.kickoffback.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubscriptionsTest {
    Subscription subscription;

    @BeforeEach
    void setUp() {
        subscription = new Subscription();
    }

    @Test
    void testId() {
        Long id = 5L;
        subscription.setId(id);
        assertEquals(subscription.getId(), id);
    }

    @Test
    void testPid() {
        Long pid = 5L;
        subscription.setPid(pid);
        assertEquals(subscription.getPid(), pid);
    }

    @Test
    void testCoid() {
        Long coid = 5L;
        subscription.setCoid(coid);
        assertEquals(subscription.getCoid(), coid);
    }

    @Test
    void testEqualsAndHashCode() {
        Object obj = subscription;
        assertEquals(subscription.getClass(), obj.getClass());
        assertTrue(subscription.hashCode() == obj.hashCode());
    }
}