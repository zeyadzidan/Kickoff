package back.kickoff.kickoffback.model;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.Hibernate;

import java.sql.Time;
import java.util.Objects;

@ToString
@Setter
@Getter
@NoArgsConstructor
@Table
@Entity
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String courtName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_court_schedule")
    public CourtSchedule courtSchedule;
    @ManyToOne
    private CourtOwner courtOwner;
    private CourtState state;
    private String description;

    public Court(String name, CourtOwner courtOwner, CourtState state, String description, CourtSchedule courtSchedule) {
        this.courtName = name;
        this.courtOwner = courtOwner;
        this.state = state;
        this.description = description;
        this.courtSchedule = courtSchedule;

    }

}
