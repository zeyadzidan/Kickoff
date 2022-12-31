package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.AddAnnouncementCommand;
import back.kickoff.kickoffback.Commands.AnnouncementFrontend;
import back.kickoff.kickoffback.model.Announcement;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.repositories.AnnouncementRepository;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.ScheduleRepository;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.sql.Date;
import java.util.*;

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
    void addAnnouncementTest() throws Exception {
        AddAnnouncementCommand command = new AddAnnouncementCommand(22L, "Welcoming", "Hello World",
                "image.png", "12/31/2023");
        CourtOwner courtOwner = new CourtOwner();
        courtOwner.setAnnouncements(new ArrayList<>());
        Mockito.when(courtOwnerRepository.findById(22L)).thenReturn(Optional.of(courtOwner));
        announcementService.addAnnouncement(command);
    }

    @Test
    void deleteAnnouncementTest() throws JSONException {
        Map<String, Object> hm = new HashMap<>();
        hm.put("id", 25L);
        CourtOwner courtOwner =new CourtOwner();
        courtOwner.setId(25L);
        courtOwner.setAnnouncements(new ArrayList<>());
        Announcement announcement = new Announcement();
        announcement.setCourtOwner(courtOwner);
        courtOwner.getAnnouncements().add(announcement);
        Mockito.when(courtOwnerRepository.findById(25L)).thenReturn(Optional.of(courtOwner));
        Mockito.when(announcementRepository.findById(25L)).thenReturn(Optional.of(announcement));
        String res = announcementService.deleteAnnouncement(25L, new Gson().toJson(hm));
        assertEquals("Success", res);
    }

    @Test
    void getAnnouncementTest() throws Exception {
        CourtOwner courtOwner = new CourtOwner();
        courtOwner.setAnnouncements(new ArrayList<>());
        Mockito.when(courtOwnerRepository.findById(25L)).thenReturn(Optional.of(courtOwner));
        List<AnnouncementFrontend> res = announcementService.getAnnouncement(25L);
        assertEquals(new ArrayList<>(), res);
    }

    @Test
    void getSubscriptionAnnouncementsTest() {
        List<AnnouncementFrontend> res = announcementService.getSubscriptionAnnouncements(new ArrayList<>());
        assertEquals(new ArrayList<>(), res);
    }
}