package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.AddRatingCommand;
import back.kickoff.kickoffback.Commands.RatingCommand;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.Rating;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import back.kickoff.kickoffback.repositories.RatingRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RatingServiceTest {
    RatingService ratingService ;

    @Mock
    CourtOwnerRepository courtOwnerRepository;
    @Mock
    PlayerRepository playerRepository;
    @Mock
    RatingRepository ratingRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ratingService = new RatingService(courtOwnerRepository, playerRepository, ratingRepository) ;

    }

    Rating createRating(long playerId, CourtOwner courtOwner){
        Rating rating = new Rating();
        rating.setId(playerId);
        rating.setPlayer(new Player());
        rating.getPlayer().setId(playerId);
        rating.setCourtOwner(courtOwner);
        rating.setReview("");
        rating.setStars(3);
        return rating;
    }

    List<Rating> setUpRatings(){
        CourtOwner courtOwner = new CourtOwner() ;
        courtOwner.setId(1L);
        List<Rating> ratings = new ArrayList<>() ;
        ratings.add(createRating(1L, courtOwner)) ;
        ratings.add(createRating(2L, courtOwner)) ;
        ratings.get(1).setStars(4);

        return ratings;
    }

    @Test
    void getRatingsTest() {
        List<Rating> ratings = setUpRatings() ;
        CourtOwner courtOwner = ratings.get(0).getCourtOwner() ;
        List<RatingCommand> actual = new ArrayList<>() ;
        for(Rating r: ratings){
            actual.add(new RatingCommand(r));
        }

        when(courtOwnerRepository.findById(1L)).thenReturn(Optional.of(courtOwner));
        when(ratingRepository.findByCourtOwner(courtOwner)).thenReturn(ratings);

        List<RatingCommand> res = new ArrayList<>();
        String message = "" ;
        try {
            res = ratingService.getRatings(1L) ;

        } catch (Exception e) {
            message = e.getMessage();
        }
        assertEquals(message,"");
        assertEquals(res, actual);
    }

    @Test
    void getPlayerRatingsTest() {
        List<Rating> rating = setUpRatings();
        CourtOwner courtOwner = rating.get(0).getCourtOwner() ;
        rating.remove(1) ;
        RatingCommand actual = new RatingCommand(rating.get(0)) ;

        when(courtOwnerRepository.findById(1L)).thenReturn(Optional.of(courtOwner));
        when(playerRepository.findById(1L)).thenReturn(Optional.of(rating.get(0).getPlayer()));
        when(ratingRepository.findByPlayerAndCourtOwner(rating.get(0).getPlayer(),courtOwner)).thenReturn(rating);

        RatingCommand res = null;
        String message = "" ;
        try {
            res = ratingService.getPlayerRatings(1L, 1L) ;

        } catch (Exception e) {
            message = e.getMessage();
        }
        assertEquals(message,"");
        assertEquals(res, actual);
    }

    AddRatingCommand setUpAddRating() throws Exception {
        JSONObject jsonObject = new JSONObject()
                .put("courtOwnerId", 1L)
                .put("playerId", 1L)
                .put("review", "")
                .put("stars", 4) ;
        return new AddRatingCommand(jsonObject.toString()) ;
    }

    @Test
    void addRatingTest() throws Exception {
        List<Rating> ratings = setUpRatings();
        AddRatingCommand command = setUpAddRating();
        CourtOwner courtOwner = ratings.get(0).getCourtOwner() ;
        Player player = ratings.get(0).getPlayer() ;

        when(courtOwnerRepository.findById(1L)).thenReturn(Optional.of(courtOwner));
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(ratingRepository.findByPlayerAndCourtOwner(player,courtOwner)).thenReturn(new ArrayList<>());
        when(ratingRepository.findByCourtOwner(courtOwner)).thenReturn(ratings);
        when(playerRepository.save(player)).thenReturn(player);
        when(courtOwnerRepository.save(courtOwner)).thenReturn(courtOwner);

        ratingService.addRating(command);

        assertEquals(courtOwner.getRating(), 3.5);
    }
    @Test
    void addRatingTest2() throws Exception {
        List<Rating> ratings = setUpRatings();
        List<Rating> oneRating =  new ArrayList<>();
        oneRating.add(ratings.get(0)) ;
        AddRatingCommand command = setUpAddRating();
        CourtOwner courtOwner = ratings.get(0).getCourtOwner() ;
        Player player = ratings.get(0).getPlayer() ;

        when(courtOwnerRepository.findById(1L)).thenReturn(Optional.of(courtOwner));
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(ratingRepository.findByPlayerAndCourtOwner(player,courtOwner)).thenReturn(oneRating);
        when(ratingRepository.findByCourtOwner(courtOwner)).thenReturn(ratings);
        when(playerRepository.save(player)).thenReturn(player);
        when(courtOwnerRepository.save(courtOwner)).thenReturn(courtOwner);

        ratingService.addRating(command);

        assertEquals(courtOwner.getRating(), 4.0);
    }

    @Test
    void calculateCourtOwnerRatingTest() {
        List<Rating> ratings = setUpRatings();
        CourtOwner courtOwner = ratings.get(0).getCourtOwner() ;

        when(courtOwnerRepository.findById(1L)).thenReturn(Optional.of(courtOwner));
        when(ratingRepository.findByCourtOwner(courtOwner)).thenReturn(ratings);
        when(courtOwnerRepository.save(courtOwner)).thenReturn(courtOwner);

        ratingService.calculateCourtOwnerRating(courtOwner);

        assertEquals(courtOwner.getRating(), 3.5);
    }

    @Test
    void calculateCourtOwnerRatingTest2() {
        CourtOwner courtOwner = new CourtOwner() ;

        when(courtOwnerRepository.findById(1L)).thenReturn(Optional.of(courtOwner));
        when(ratingRepository.findByCourtOwner(courtOwner)).thenReturn(new ArrayList<>());
        when(courtOwnerRepository.save(courtOwner)).thenReturn(courtOwner);

        ratingService.calculateCourtOwnerRating(courtOwner);

        assertEquals(courtOwner.getRating(), 2.5);
    }
}