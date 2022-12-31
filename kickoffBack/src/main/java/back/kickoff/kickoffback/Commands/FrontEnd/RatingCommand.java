package back.kickoff.kickoffback.Commands.FrontEnd;

import back.kickoff.kickoffback.model.Rating;
import lombok.Data;
import lombok.Getter;

@Data
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
