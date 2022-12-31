package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.AddRatingCommand;
import back.kickoff.kickoffback.Commands.RatingCommand;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.Rating;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import back.kickoff.kickoffback.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    private final CourtOwnerRepository courtOwnerRepository;
    private final PlayerRepository playerRepository;
    private final RatingRepository ratingRepository;

    public RatingService(CourtOwnerRepository courtOwnerRepository, PlayerRepository playerRepository, RatingRepository ratingRepository) {
        this.courtOwnerRepository = courtOwnerRepository;
        this.playerRepository = playerRepository;
        this.ratingRepository = ratingRepository;
    }

    public List<RatingCommand> getRatings(long courtOwnerId) throws Exception {
        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(courtOwnerId) ;
        if (courtOwnerOptional.isEmpty()){
            throw new Exception("CourtOwner not found") ;
        }
        List<Rating> ratings = ratingRepository.findByCourtOwner(courtOwnerOptional.get()) ;
        List<RatingCommand> result = new ArrayList<>() ;

        for(Rating r: ratings){
            result.add(new RatingCommand(r)) ;
        }

        return result ;

    }

    public RatingCommand getPlayerRatings(long playerId, long courtOwnerId) throws Exception {
        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(courtOwnerId) ;
        if (courtOwnerOptional.isEmpty()){
            throw new Exception("CourtOwner not found") ;
        }
        Optional<Player> playerOptional = playerRepository.findById(playerId) ;
        if (playerOptional.isEmpty()){
            throw new Exception("CourtOwner not found") ;
        }

        List<Rating> ratingOptional = ratingRepository.findByPlayerAndCourtOwner(playerOptional.get(), courtOwnerOptional.get()) ;
        if (ratingOptional.isEmpty()){
            throw new Exception("rating not found") ;
        }

        return new RatingCommand(ratingOptional.get(0) ) ;

    }

    public boolean addRating(AddRatingCommand command) throws Exception {
        Optional<CourtOwner> courtOwner = courtOwnerRepository.findById(command.courtOwnerId);
        if (courtOwner.isEmpty()){
            throw new Exception("CourtOwner does not exist") ;
        }

        Optional<Player> player = playerRepository.findById(command.playerId);
        if (player.isEmpty()){
            throw new Exception("CourtOwner does not exist") ;
        }

        if(command.stars<0 || command.stars>5){
            throw new Exception("stars should be between 0-5") ;
        }
        Rating rating ;
        List<Rating> ratingOptional = ratingRepository.findByPlayerAndCourtOwner(player.get(), courtOwner.get()) ;
        if(!ratingOptional.isEmpty()){
            rating = ratingOptional.get(0) ;
            rating.setStars(command.stars);
            rating.setReview(command.review);
        }else {
            rating = new Rating(player.get(), courtOwner.get(), command.stars, command.review);
        }
        ratingRepository.save(rating) ;
        calculateCourtOwnerRating(courtOwner.get()) ;
        return true;
    }

    void calculateCourtOwnerRating(CourtOwner courtOwner){
        List<Rating> ratings = ratingRepository.findByCourtOwner(courtOwner) ;
        float rate = 0 ;
        for (Rating r: ratings){
            rate += r.getStars() ;
        }
        if(ratings.size()!= 0){
            rate/= ratings.size() ;
        }else {
            rate = 2.5F;
        }
        courtOwner.setRating(rate);
        courtOwnerRepository.save(courtOwner) ;
    }



}
