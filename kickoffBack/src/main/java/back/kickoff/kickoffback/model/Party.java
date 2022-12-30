package back.kickoff.kickoffback.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Setter
@Getter
@NoArgsConstructor
@Table
@Entity
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    Long courtOwnerId;
    @ManyToOne
    Long courtId;
    @ManyToOne
    Long playerCreatedId;
    @ManyToMany(fetch = FetchType.LAZY)
    List<Long> playerJoinedId;
    String neededNumbers;
    String availableNumbers;
    Long ReservationId;

    @ManyToOne
    @JoinColumn(name = "court_owner_id")
    private CourtOwner courtOwner;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "player_created_id")
    private Player playerCreated;

    @ManyToOne
    @JoinColumn(name = "player_joined_id")
    private Player playerJoined;

    public Party(Long courtOwnerId, Long courtId, String neededNumbers, String availableNumbers,
                Long playerCreatedId, Long ReservationId) {
        this.courtOwnerId = courtOwnerId;
        this.courtId = courtId;
        this.playerCreatedId = playerCreatedId;
        this.availableNumbers = availableNumbers;
        this.neededNumbers = neededNumbers;
        this.ReservationId = ReservationId;
    }
}
