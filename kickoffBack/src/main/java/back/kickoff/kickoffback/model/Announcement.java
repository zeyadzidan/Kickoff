package back.kickoff.kickoffback.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@ToString
@Setter
@Getter
@NoArgsConstructor
@Table
@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    CourtOwner courtOwner;
    String title ;
    String body ;
    String img; // Attachments

    Date date ;

}
