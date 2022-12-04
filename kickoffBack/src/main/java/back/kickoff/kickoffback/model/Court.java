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
        @ManyToOne
    private CourtOwner courtOwner;
    private CourtState state;

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_court_schedule")
    public CourtSchedule courtSchedule;

    public Court(String name, CourtOwner courtOwner, CourtState state, String description, Time startWorkingHours,
                 Time endWorkingHours, Time endMorning,Integer morningCost, Integer nightCost, Integer minBookingHours,CourtSchedule courtSchedule)
    {
      this.courtName =name;
      this.courtOwner = courtOwner;
      this.state = state;
      this.description=description;
      this.courtSchedule = courtSchedule ;

      }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Court court = (Court) o;
        return id != null && Objects.equals(id, court.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
