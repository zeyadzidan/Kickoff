package back.kickoff.kickoffback.Commands;

import back.kickoff.kickoffback.model.Rating;
import lombok.Getter;

@Getter
public class RatingCommand {
    int stars ;
    String review ;
    String playerName ;
    String courtOwnerName ;

    public RatingCommand(Rating rating){
        stars = rating.getStars() ;
        review = rating.getReview() ;
        playerName = rating.getPlayer().getName() ;
        courtOwnerName = rating.getCourtOwner().getUserName() ;
    }

}
