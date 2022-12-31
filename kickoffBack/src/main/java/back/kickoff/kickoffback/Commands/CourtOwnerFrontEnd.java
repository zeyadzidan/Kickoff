package back.kickoff.kickoffback.Commands;

import back.kickoff.kickoffback.model.CourtOwner;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class CourtOwnerFrontEnd {

    public Long id;
    public String name;
    public String email;
    public String location;
    public float rating;
    public String image;
    public String phoneNumber;
    public Double xAxis ;
    public Double yAxis;

    public CourtOwnerFrontEnd(CourtOwner courtOwner){
        id = courtOwner.getId();
        name = courtOwner.getUserName();
        email = courtOwner.getEmail();
        rating = courtOwner.getRating();
        image = courtOwner.getImage();
        location =courtOwner.getLocation();
        phoneNumber = courtOwner.getPhoneNumber();
        xAxis = courtOwner.getXAxis();
        yAxis = courtOwner.getYAxis();
    }

}
