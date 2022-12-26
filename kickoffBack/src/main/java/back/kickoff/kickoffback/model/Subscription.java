package back.kickoff.kickoffback.model;

import back.kickoff.kickoffback.repositories.SubscriptionsRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Table
@NoArgsConstructor
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long pid;
    private Long coid;

    @Override
    public String toString() {
        return "id: " + id + ", pid: " + pid + ", coid: " + coid;
    }
}
