package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;

import com.google.gson.Gson;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LoginServiceTest {
    LoginService loginService;

    @Mock
    CourtOwnerRepository courtOwnerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        loginService = new LoginService(courtOwnerRepository);
    }

    @Test
    void courtOwnerLogin() throws JSONException {
        CourtOwner newCourtOwner = new CourtOwner("Nasr CLub", "nasrClub@gmail.com", "12345678900",
                "01206555589", 44.5, 44.5);
        newCourtOwner.setRating(0);
        newCourtOwner.setLocation("Nasr CLub");
        newCourtOwner.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png");
        List list = new ArrayList();
        newCourtOwner.setCourts(list);
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("email", "nasrClub@gmail.com");
        hm.put("password", "12345678900");
        String information = new Gson().toJson(hm);
        when(courtOwnerRepository.save(new CourtOwner())).thenReturn(new CourtOwner());
        when(courtOwnerRepository.findByEmail("nasrClub@gmail.com")).thenReturn(java.util.Optional.of(newCourtOwner));
        String result = loginService.courtOwnerLogin(information);

        Map<String, Object> res = new HashMap<>();
        res.put("id", newCourtOwner.getId());
        res.put("userName", newCourtOwner.getUserName());
        res.put("email", newCourtOwner.getEmail());
        res.put("location", newCourtOwner.getLocation());
        res.put("rating", String.valueOf(newCourtOwner.getRating()));
        res.put("image", newCourtOwner.getImage());
        res.put("phoneNumber", newCourtOwner.getPhoneNumber());
        res.put("xAxis", newCourtOwner.getXAxis());
        res.put("yAxis", newCourtOwner.getYAxis());


        assertEquals(new Gson().toJson(res), result);


    }
}