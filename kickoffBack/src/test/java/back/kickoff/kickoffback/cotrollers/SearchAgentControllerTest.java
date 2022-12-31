package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.Commands.Operation.CourtOwnerSearchCommand;
import back.kickoff.kickoffback.Commands.Operation.SearchCommand;
import back.kickoff.kickoffback.services.SearchAgent;
import com.google.gson.Gson;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SearchAgentControllerTest {

    SearchAgentController searchAgentController;
    @Mock
    SearchAgent searchAgent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searchAgentController = new SearchAgentController(searchAgent);
    }

    @Test
    void searchNearestCourtOwner() throws JSONException {
        SearchCommand searchCommand = new SearchCommand("", 22.0, 22.0);
        ArrayList<CourtOwnerSearchCommand> commands = new ArrayList<>();
        Mockito.when(searchAgent.getNearestCourtOwners(searchCommand)).thenReturn(String.valueOf(new Gson().toJson(commands)));
        ResponseEntity<String> res = searchAgentController.searchNearestCourtOwner(searchCommand);
        assertEquals(res, new ResponseEntity<>(new Gson().toJson(commands), HttpStatus.OK));
    }

    @Test
    void getCourtOwner() {
        Long l = 11L;
        Mockito.when(searchAgent.getCourtOwner(l)).thenReturn("Not found");
        ResponseEntity<String> res = searchAgentController.getCourtOwner(l.toString());
        assertEquals(new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND), res);
    }
}