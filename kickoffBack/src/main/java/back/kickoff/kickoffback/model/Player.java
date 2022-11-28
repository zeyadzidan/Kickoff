package back.kickoff.kickoffback.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id ;

    @ManyToMany(mappedBy = "playersID")
    Set<Reservation> reservations ;


}
