package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.Commands.AddImageCommand;
import back.kickoff.kickoffback.Commands.CourtFrontEnd;
import back.kickoff.kickoffback.Commands.CreateCourtCommand;
import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.services.AnnouncementService;
import back.kickoff.kickoffback.services.CourtOwnerAgent;
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
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourtOwnerAgentControllerTest {
    CourtOwnerAgentController courtOwnerAgentController;
    @Mock
    CourtOwnerAgent courtOwnerAgent;

    @Mock
    AnnouncementService announcementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courtOwnerAgentController = new CourtOwnerAgentController(courtOwnerAgent, announcementService);
    }

    @Test
    void listCourts() throws Exception {
        Mockito.when(courtOwnerAgent.findCourtOwnerCourts(1L)).thenReturn(new ArrayList<CourtFrontEnd>());
        ResponseEntity<String> res = courtOwnerAgentController.listCourts("1");
        assertEquals(res, new ResponseEntity<>("[]", HttpStatus.OK));
    }



    @Test
    void createCourt() throws Exception {
        CreateCourtCommand createCourtCommand = new CreateCourtCommand(1L, "A",
                "grass with 5 players in each team", 100, 150,
                1, 1 ,21);
        ResponseEntity<String> res = courtOwnerAgentController.createCourt(createCourtCommand);
        assertEquals(res, new ResponseEntity<>("Success", HttpStatus.CREATED));
    }

    @Test
    void addImage() throws JSONException {
        AddImageCommand command = new AddImageCommand(1L, "thisIsAnImage.com");
//        Mockito.when(courtOwnerAgent.addImage(command)).thenReturn("Success");

        ResponseEntity<String> res = courtOwnerAgentController.addImage(command);

        assertEquals(res, new ResponseEntity<>("Success", HttpStatus.OK));
    }

}