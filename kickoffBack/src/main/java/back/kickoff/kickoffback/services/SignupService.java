package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.FrontEnd.CourtOwnerFrontEnd;
import back.kickoff.kickoffback.Commands.FrontEnd.PlayerFrontEnd;
import back.kickoff.kickoffback.Commands.Add.SignupCommand;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Player;
import back.kickoff.kickoffback.model.PlayerType;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class SignupService {
    private final CourtOwnerRepository courtOwnerRepository;
    private final PlayerRepository playerRepository;

    public SignupService(CourtOwnerRepository courtOwnerRepository, PlayerRepository playerRepository) {
        this.courtOwnerRepository = courtOwnerRepository;
        this.playerRepository = playerRepository;
    }

    public CourtOwnerFrontEnd courtOwnerSignup(SignupCommand command) throws Exception {

        Optional<CourtOwner> courtOwner = courtOwnerRepository.findByEmail(command.email);
        String regex = "^(.+)@(.+)$";
        boolean isvalid = Pattern.compile(regex).matcher(command.email).matches();
        if (courtOwner.isPresent())
            throw new Exception("Email exist");
        if (!isvalid)
            throw new Exception("invalid");
        CourtOwner newCourtOwner = new CourtOwner(command.username, command.email, command.password, command.phoneNumber, command.xAxis, command.yAxis);
        newCourtOwner.setRating(0);
        newCourtOwner.setLocation(command.location);
        courtOwnerRepository.save(newCourtOwner);

        return new CourtOwnerFrontEnd(newCourtOwner);
    }

    public PlayerFrontEnd playerSignup(SignupCommand command) throws Exception
    {
        Optional<Player> player = playerRepository.findByEmail(command.email);
        String regex = "^(.+)@(.+)$";
        boolean valid =Pattern.compile(regex).matcher(command.email).matches();
        System.out.println("youssryTaha 2adwtna");
        if(player.isPresent())
            throw new Exception("Email exist");
        if (!valid)
            throw new Exception("invalid");
        Player newPlayer = new Player(command.username, command.email, command.phoneNumber,command.password, command.location, command.xAxis, command.yAxis);
        newPlayer.setPlayerType(PlayerType.Registered);
        playerRepository.save(newPlayer);


        return new PlayerFrontEnd(newPlayer);
    }
}
