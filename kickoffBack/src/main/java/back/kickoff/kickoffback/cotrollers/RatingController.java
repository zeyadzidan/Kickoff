package back.kickoff.kickoffback.cotrollers;

import back.kickoff.kickoffback.Commands.AddRatingCommand;
import back.kickoff.kickoffback.Commands.RatingCommand;
import back.kickoff.kickoffback.services.RatingService;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/rating")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/getRatings/{courtOwnerId}")
    public ResponseEntity<String> getRatings(@PathVariable String courtOwnerId) {
        try {
            List<RatingCommand> ratings = ratingService.getRatings(Long.parseLong(courtOwnerId)) ;
            return new ResponseEntity<>(new Gson().toJson(ratings) , HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addRating")
    public ResponseEntity<String> addRating(@RequestBody String information) {
        try {
            AddRatingCommand command = new AddRatingCommand(information) ;
            ratingService.addRating(command);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }
    }

    @GetMapping("/getRatings/{playerId}/{courtOwnerId}")
    public ResponseEntity<String> getPlayerRatings(@PathVariable String playerId, @PathVariable String courtOwnerId ) {
        try {
            RatingCommand rating = ratingService.getPlayerRatings(Long.parseLong(playerId), Long.parseLong(courtOwnerId)) ;
            return new ResponseEntity<>(new Gson().toJson(rating) , HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
