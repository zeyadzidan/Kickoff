package back.kickoff.kickoffback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@Table
@Entity
@NoArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    Player player ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "court_owner_id")
    CourtOwner courtOwner ;

    int stars ;
    String review;

    public Rating(Player player, CourtOwner courtOwner, int stars, String review) {
        this.player = player;
        this.courtOwner = courtOwner;
        this.stars = stars;
        this.review = review;
    }
}
