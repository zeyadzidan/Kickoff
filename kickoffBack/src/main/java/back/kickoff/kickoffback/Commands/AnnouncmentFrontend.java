package back.kickoff.kickoffback.Commands;

import back.kickoff.kickoffback.model.Announcement;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Setter
@Getter
public
class AnnouncmentFrontend {
    Long id;
    Long courtOwnerId;
    String body;
    String cni; // Attachments
    String date;
    String time;
    String courtPic ;
    String name;


    public AnnouncmentFrontend(Announcement announcement) {
        this.id = announcement.getId();
        this.courtOwnerId = announcement.getCourtOwner().getId();
        this.body = announcement.getBody();
        this.cni = announcement.getImg();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(announcement.getDate());
        this.time = announcement.getTime().toString();
        this.courtPic = announcement.getCourtOwner().getImage() ;
        this.name = announcement.getCourtOwner().getUserName();
    }
}