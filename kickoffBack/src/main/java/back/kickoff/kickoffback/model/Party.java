package back.kickoff.kickoffback.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Setter
@Getter
@NoArgsConstructor
@Table
@Entity
@Data
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long PartyId;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="player_party", joinColumns = @JoinColumn(name="party_id"),inverseJoinColumns = @JoinColumn(name="player_id"))
    List<Player> playerJoined;
    String neededNumbers;
    String availableNumbers;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_id")
    Reservation reservation;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "court_owner_id")
    private CourtOwner courtOwner;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "court_id")
    private Court court;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_created_id")
    private Player playerCreated;

    public Party(String neededNumbers, String availableNumbers, Reservation reservation, CourtOwner courtOwner,Court court, Player playerCreated) {
        this.neededNumbers = neededNumbers;
        this.availableNumbers = availableNumbers;
        this.reservation = reservation;
        this.courtOwner = courtOwner;
        this.court =court;
        this.playerCreated = playerCreated;
        playerJoined = new ArrayList<>();
    }

    public void addJoinedPlayers(Player PlayerJoined) {
        playerJoined.add(PlayerJoined);
    }
    public void leaveJoinedPlayers(Player PlayerJoined) {
        playerJoined.remove(PlayerJoined);
    }
    public void incrementneededPlayer(String NeededNumbers) {
        int number = Integer.parseInt(NeededNumbers);
        number++;
        neededNumbers = Integer.toString(number);
    }
    public void dectementneededPlayer(String NeededNumbers) {
        int number = Integer.parseInt(NeededNumbers);
        number--;
        neededNumbers = Integer.toString(number);
    }
    public void incrementavaiblePlayer(String AvailableNumbers) {
        int number = Integer.parseInt(AvailableNumbers);
        number++;
        availableNumbers = Integer.toString(number);
    }
    public void decrementavaiblePlayer(String AvailableNumbers) {
        int number = Integer.parseInt(AvailableNumbers);
        number--;
        availableNumbers = Integer.toString(number);
    }
}
