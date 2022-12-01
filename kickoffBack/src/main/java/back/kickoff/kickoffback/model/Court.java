package back.kickoff.kickoffback.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Setter
@Getter
@Entity
@EqualsAndHashCode(exclude = "courtOwner")
@Table
@NoArgsConstructor
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private CourtOwner courtOwner;
    private State state;

    private String discription;
    public Court(String name, CourtOwner courtOwner, State state, String discription) {
        this.name = name;
        this.courtOwner = courtOwner;
        this.state = state;
    }
}
