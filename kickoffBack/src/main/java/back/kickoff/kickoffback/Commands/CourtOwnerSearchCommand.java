package back.kickoff.kickoffback.Commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CourtOwnerSearchCommand {
    private Long id;
    private String courtOwnerName;
    private String courtOwnerPicture;
    private Double distance;
    private Double rating;

    public CourtOwnerSearchCommand(Long id, String courtOwnerName, String courtOwnerPicture, Double distance, Double rating) {
        this.courtOwnerName = courtOwnerName;
        this.courtOwnerPicture = courtOwnerPicture;
        this.distance = distance;
        this.rating = rating;
    }
}
