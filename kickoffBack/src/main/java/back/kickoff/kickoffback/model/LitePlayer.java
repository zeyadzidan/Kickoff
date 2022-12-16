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
public class LitePlayer implements PlayerI{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String Name;
    private String phoneNumber;
    private PlayerType  playerType = PlayerType.Lite ;



/*
    @ManyToMany(mappedBy = "playersID")
    @ToString.Exclude
    Set<Reservation> reservations ;
*/

}
