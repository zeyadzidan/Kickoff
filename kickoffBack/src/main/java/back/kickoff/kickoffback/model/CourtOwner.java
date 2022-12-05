package back.kickoff.kickoffback.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class CourtOwner
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String email;
    private String password;
    private String location;
    private float rating;
    private String image;

    private String phoneNumber;
    private Double xAxis;
    private Double yAxis;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courtOwner")
    private Set<Court> courts = new HashSet<>();


    public CourtOwner(String userName, String email, String password, String phoneNumber,
                      Double xAxis, Double yAxis)
    {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public CourtOwner addCourt(Court court)
    {
        court.setCourtOwner(this);
        courts.add(court);
        return this;
    }
}
