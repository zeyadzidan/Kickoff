package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.services.LoginService;
import back.kickoff.kickoffback.services.SignupService;

import com.google.gson.Gson;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LoginControllerTest {
    LoginController Controller;
    @Mock
    LoginService loginService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Controller = new LoginController(loginService);
    }

    @Test
    void courtOwnerLoginRequest() throws JSONException {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("email", "nasrClub@gmail.com");
        hm.put("password", "12345678900");
        String information = new Gson().toJson(hm);
        CourtOwner newCourtOwner = new CourtOwner("Nasr CLub", "nasrClub@gmail.com", "12345678900",
                "01206555589", 44.5, 44.5);
        newCourtOwner.setRating(0);
        newCourtOwner.setLocation("Nasr CLub");
        newCourtOwner.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png");
        List list = new ArrayList();
        newCourtOwner.setCourts(list);
        when(loginService.courtOwnerLogin(information)).thenReturn(new Gson().toJson(newCourtOwner));
        ResponseEntity<String> res = Controller.courtOwnerLoginRequest(information);
        assertEquals(res, new ResponseEntity<>(new Gson().toJson(newCourtOwner), HttpStatus.OK));
    }
}