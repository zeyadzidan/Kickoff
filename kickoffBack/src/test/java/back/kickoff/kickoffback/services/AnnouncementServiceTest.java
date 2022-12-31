package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.AddAnnouncementCommand;
import back.kickoff.kickoffback.repositories.AnnouncementRepository;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.ScheduleRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class AnnouncementServiceTest {
    AnnouncementService announcementService;

    @Mock
    CourtOwnerRepository courtOwnerRepository;
    @Mock
    AnnouncementRepository announcementRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        announcementService = new AnnouncementService(courtOwnerRepository, announcementRepository);
    }

    @Test
    void addAnnouncementTest() {
    }

    @Test
    void deleteAnnouncementTest() {
    }

    @Test
    void getAnnouncementTest() {
    }

    @Test
    void getSubscriptionAnnouncementsTest() {
    }
}