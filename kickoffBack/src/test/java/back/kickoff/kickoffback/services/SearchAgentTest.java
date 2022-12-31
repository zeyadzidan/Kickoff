package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.FrontEnd.CourtOwnerFrontEnd;
import back.kickoff.kickoffback.Commands.Operation.CourtOwnerSearchCommand;
import back.kickoff.kickoffback.Commands.Operation.SearchCommand;
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
        SearchCommand searchCommand = new SearchCommand("", 22.0, 22.0);
        ArrayList<CourtOwnerSearchCommand> commands = new ArrayList<>();
        List<CourtOwner> foundCourtOwners = new ArrayList<>();
        Mockito.when(courtOwnerRepository.searchNearestCourtOwner(22.0, 22.0, "", "")).thenReturn(foundCourtOwners);
        String res = searchAgent.getNearestCourtOwners(searchCommand);
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
        Mockito.when(courtOwnerRepository.findById(11L)).thenReturn(Optional.of(courtOwner));
        String res = searchAgent.getCourtOwner(11L);
        CourtOwnerFrontEnd courtOwnerFrontEnd = new CourtOwnerFrontEnd(courtOwner);
        System.out.println(courtOwnerFrontEnd.toString());
        assertEquals(new Gson().toJson(courtOwnerFrontEnd), res);
    }
}