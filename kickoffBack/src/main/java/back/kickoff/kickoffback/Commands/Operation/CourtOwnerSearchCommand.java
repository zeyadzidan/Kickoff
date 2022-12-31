package back.kickoff.kickoffback.Commands.Operation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class CourtOwnerSearchCommand implements Comparable<CourtOwnerSearchCommand>{
    private Long id;
    private String courtOwnerName;
    private String courtOwnerPicture;
    private int distance;
    private Double rating;

    public CourtOwnerSearchCommand(Long id, String courtOwnerName, String courtOwnerPicture, int distance, Double rating) {
        this.id = id;
        this.courtOwnerName = courtOwnerName;
        this.courtOwnerPicture = courtOwnerPicture;
        this.distance = distance;
        this.rating = rating;
    }

    @Override
    public int compareTo(CourtOwnerSearchCommand o) {
        if(this.distance > o.getDistance())
            return 1;
        else if(this.distance < o.getDistance())
            return -1;
        return  0;
    }
}
