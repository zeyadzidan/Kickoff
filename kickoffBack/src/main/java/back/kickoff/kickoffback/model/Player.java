package back.kickoff.kickoffback.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.Set;

@ToString
@RequiredArgsConstructor
@Setter
@Getter
@Table
@NoArgsConstructor
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id ;

    @ManyToMany(mappedBy = "playersID")
    @ToString.Exclude
    Set<Reservation> reservations ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Player player = (Player) o;
        return Id != null && Objects.equals(Id, player.Id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
