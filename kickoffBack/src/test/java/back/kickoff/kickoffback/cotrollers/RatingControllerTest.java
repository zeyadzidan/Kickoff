package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.Commands.AddRatingCommand;
import back.kickoff.kickoffback.Commands.CourtFrontEnd;
import back.kickoff.kickoffback.Commands.RatingCommand;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.Rating;
import back.kickoff.kickoffback.services.RatingService;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RatingControllerTest {
    RatingController ratingController;

    @Mock
    RatingService ratingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ratingController = new RatingController(ratingService) ;
    }

    Rating createRating(){
        Rating rating = new Rating();
        rating.setStars(3);
        rating.setReview("");
        CourtOwner courtOwner = new CourtOwner();
        courtOwner.setId(1L);
        courtOwner.setUserName("nameCO");
        rating.setCourtOwner(courtOwner);
        Player player = new Player();
        player.setId(1L);
        player.setName("gad");
        rating.setPlayer(player);
        return rating;
    }

    @Test
    void getRatingsTest() throws Exception {
        List<RatingCommand> ratings = new ArrayList<>() ;
        ratings.add(new RatingCommand(createRating()));

        Mockito.when(ratingService.getRatings(1L)).thenReturn(ratings);
        ResponseEntity<String> res = ratingController.getRatings("1");
        assertEquals(res, new ResponseEntity<>(new Gson().toJson(ratings), HttpStatus.OK));
    }
    String setUpAddRating() throws JSONException {
        JSONObject jsonObject = new JSONObject()
                .put("playerId", 1L)
                .put("courtOwnerId", 1L)
                .put("review", "")
                .put("stars", 3);
        return jsonObject.toString();
    }

    @Test
    void addRating() throws Exception {
        String info = setUpAddRating();
        AddRatingCommand command = new AddRatingCommand(info);

        Mockito.when(ratingService.addRating(command)).thenReturn(true);
        ResponseEntity<String> res = ratingController.addRating(info);
        assertEquals(res, new ResponseEntity<>("Success", HttpStatus.OK));

    }

    @Test
    void getPlayerRatings() throws Exception {
        RatingCommand ratings = new RatingCommand(createRating()) ;

        Mockito.when(ratingService.getPlayerRatings(1L,1L)).thenReturn(ratings);
        ResponseEntity<String> res = ratingController.getPlayerRatings("1", "1");
        assertEquals(res, new ResponseEntity<>(new Gson().toJson(ratings), HttpStatus.OK));

    }
}