package back.kickoff.kickoffback.model;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Setter
@Getter
@Table
@NoArgsConstructor
@Data
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
/*
    @ManyToMany(mappedBy = "playersID")
    @ToString.Exclude
    Set<Reservation> reservations ;
*/

}
