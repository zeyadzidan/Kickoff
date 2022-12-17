package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.CourtSchedule;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.CourtRepository;
import back.kickoff.kickoffback.repositories.ScheduleRepository;
import com.google.gson.Gson;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourtOwnerAgentTest {
    CourtOwnerAgent courtOwnerAgent;
    @Mock
    CourtOwnerRepository courtOwnerRepository;
    @Mock
    CourtRepository courtRepository;
    @Mock
    ScheduleRepository scheduleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courtOwnerAgent = new CourtOwnerAgent(courtOwnerRepository, courtRepository, scheduleRepository);
    }

    @Test
    void findCourtOwnerCourts() throws JSONException {
        Long id = 22L;
        CourtOwner courtOwner = new CourtOwner();
        courtOwner.setCourts(new ArrayList<Court>());
        Mockito.when(courtOwnerRepository.findById(id)).thenReturn(Optional.of(courtOwner));
        String res = courtOwnerAgent.findCourtOwnerCourts(id);
        assertEquals(res, "[]");
    }

    @Test
    void addImage() throws JSONException {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("ownerID", 1L);
        hm.put("imageURL", "thisIsAnImage.com");
        String information = new Gson().toJson(hm);
        CourtOwner courtOwner = new CourtOwner();
        Mockito.when(courtOwnerRepository.findById(1L)).thenReturn(Optional.of(courtOwner));
        Mockito.when(courtOwnerRepository.save(courtOwner)).thenReturn(courtOwner);

        String res = courtOwnerAgent.addImage(information);

        assertEquals(res, "Success");

    }

    @Test
    void createCourt() throws JSONException {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("ownerID", 1L);
        hm.put("courtName", "A");
        hm.put("description", "grass with 5 players in each team");
        hm.put("morningCost", 100);
        hm.put("nightCost", 150);
        hm.put("minBookingHours", 1);
        hm.put("startWorkingHours", 1);
        hm.put("finishWorkingHours", 21);
        String information = new Gson().toJson(hm);
        CourtOwner courtOwner = new CourtOwner();
        courtOwner.setCourts(new ArrayList<Court>());
        Mockito.when(courtOwnerRepository.findById(1L)).thenReturn(Optional.of(courtOwner));
        Mockito.when(scheduleRepository.save(new CourtSchedule())).thenReturn(new CourtSchedule());
        Mockito.when(courtRepository.save(new Court())).thenReturn(new Court());
        Mockito.when(courtOwnerRepository.save(courtOwner)).thenReturn(courtOwner);
        String res = courtOwnerAgent.createCourt(information);
        assertEquals(res, "Success");
    }
}