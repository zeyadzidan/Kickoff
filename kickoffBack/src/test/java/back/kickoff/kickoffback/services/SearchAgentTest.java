package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.CourtOwnerSearchCommand;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import com.google.gson.Gson;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SearchAgentTest {
    SearchAgent searchAgent;
    @Mock
    CourtOwnerRepository courtOwnerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searchAgent = new SearchAgent(courtOwnerRepository);
    }

    @Test
    void getNearestCourtOwners() throws JSONException {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("xAxis", 22);
        hm.put("yAxis", 22);
        hm.put("keyword", "");
        String information = new Gson().toJson(hm);
        ArrayList<CourtOwnerSearchCommand> commands = new ArrayList<>();
        List<CourtOwner> foundCourtOwners = new ArrayList<>();
        Mockito.when(courtOwnerRepository.searchNearestCourtOwner(22.0, 22.0, "", "")).thenReturn(foundCourtOwners);
        String res = searchAgent.getNearestCourtOwners(information);
        assertEquals(new Gson().toJson(commands), res);
    }

    @Test
    void getCourtOwner() {
        Long l = 11L;
        CourtOwner courtOwner = new CourtOwner("Nasr Club", "nasrClub@gmail.com", "012345678",
                "01244666699", 12.0, 22.0);
        courtOwner.setLocation("Green Plaza");
        courtOwner.setId(11L);
        courtOwner.setImage("image.png");
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("id", courtOwner.getId());
        hm.put("name", "Nasr Club");
        hm.put("email", "nasrClub@gmail.com");
        hm.put("location", "Green Plaza");
        hm.put("rating", String.valueOf(0.0));
        hm.put("image", "image.png");
        hm.put("phoneNumber", "01244666699");
        hm.put("xAxis", 12.0);
        hm.put("yAxis", 22.0);
        Mockito.when(courtOwnerRepository.findById(11L)).thenReturn(Optional.of(courtOwner));
        String res = searchAgent.getCourtOwner(11L);
        assertEquals(new Gson().toJson(hm), res);
    }
}