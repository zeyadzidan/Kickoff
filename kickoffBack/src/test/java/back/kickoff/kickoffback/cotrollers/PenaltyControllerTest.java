package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.Commands.AddPenaltyCommand;
import back.kickoff.kickoffback.services.PenaltyService;
import com.google.gson.Gson;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PenaltyControllerTest {
    @Mock
    PenaltyService penaltyService;
    PenaltyController penaltyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        penaltyController = new PenaltyController(penaltyService);
    }

    @Test
    void addReportFromCourtOwner() throws Exception {
        Map<String, Object> hm = new HashMap<>();
        hm.put("coid", 25L);
        hm.put("pid", 14L);
        String information = new Gson().toJson(hm);
        AddPenaltyCommand command = new AddPenaltyCommand(information, true);
        Mockito.when(penaltyService.addReport(command)).thenReturn(true);
        ResponseEntity<String> response = penaltyController.addReportFromCourtOwner(information);
        assertEquals(new ResponseEntity<>(String.valueOf(true), HttpStatus.OK), response);
    }

    @Test
    void addReportFromPlayer() throws Exception {
        Map<String, Object> hm = new HashMap<>();
        hm.put("pid1", 10L);
        hm.put("pid2", 14L);
        String information = new Gson().toJson(hm);
        AddPenaltyCommand command = new AddPenaltyCommand(information, false);
        Mockito.when(penaltyService.addReport(command)).thenReturn(true);
        ResponseEntity<String> response = penaltyController.addReportFromPlayer(information);
        assertEquals(new ResponseEntity<>(String.valueOf(true), HttpStatus.OK), response);
    }
}