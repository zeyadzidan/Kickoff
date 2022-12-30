package back.kickoff.kickoffback.Commands;

import back.kickoff.kickoffback.model.CourtOwner;

public class CourtOwnerFrontEnd {

    public Long courtOwnerId;
    public String name;
    public String email;
    public String location;
    public float rating;
    public String image;
    public String phoneNumber;

    public CourtOwnerFrontEnd(CourtOwner courtOwner){
//        courtOwnerId = courtOwner.getId();
        name = courtOwner.getUserName();
//        email = courtOwner.getEmail();
//        rating = courtOwner.getRating();
//        image = courtOwner.getImage();
//        phoneNumber = courtOwner.getPhoneNumber();
    }

}
