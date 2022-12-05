package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import com.google.gson.Gson;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SignupServiceTest {

    SignupService signupService;

    @Mock
    CourtOwnerRepository courtOwnerRepository;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        signupService = new SignupService(courtOwnerRepository);
    }
    @Test
    void courtOwnerSignup() throws JSONException {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("email", "nasrClub@gmail.com");
        hm.put("password", "12345678900");
        hm.put("username", "Nasr CLub");
        hm.put("location", "Nasr CLub");
        hm.put("phoneNumber", "01206555589");
        hm.put("xAxis", 44.5);
        hm.put("yAxis", 44.5);
        String information = new Gson().toJson(hm);
        when(courtOwnerRepository.save(new CourtOwner())).thenReturn(new CourtOwner());
        //abdelaziz
        String res = signupService.courtOwnerSignup(information);
        assertEquals(res, "Succes");
    }
}
