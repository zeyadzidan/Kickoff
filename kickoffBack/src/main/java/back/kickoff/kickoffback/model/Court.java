package back.kickoff.kickoffback.model;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.Hibernate;

import java.util.Objects;

@ToString
@RequiredArgsConstructor
@Setter
@Getter
@Table
@Entity
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long Court_Owner_id;
    public String Court_Number;
    public String Court_Type;
        @ManyToOne
    private CourtOwner courtOwner;
    private State state;

    private String discription;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_court_schedule")
    public CourtSchedule courtSchedule;

    public Court(String name, CourtOwner courtOwner, State state, String discription)
    {
      this.Court_Owner_id=Court_Owner_id;
      this.Court_Number=name;
      this.Court_Type=Court_Type;

      this.courtOwner = courtOwner;
       this.state = state;
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
