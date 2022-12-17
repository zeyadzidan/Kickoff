package back.kickoff.kickoffback.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class CourtOwner {
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
    private List<Court> courts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courtOwner")
    private List<Announcement> announcements;


    public CourtOwner(String userName, String email, String password, String phoneNumber,
                      Double xAxis, Double yAxis) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public CourtOwner addCourt(Court court) {
        court.setCourtOwner(this);
        courts.add(court);
        return this;
    }
}
