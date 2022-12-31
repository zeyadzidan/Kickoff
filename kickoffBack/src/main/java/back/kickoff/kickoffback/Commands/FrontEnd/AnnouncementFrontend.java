package back.kickoff.kickoffback.Commands.FrontEnd;

import back.kickoff.kickoffback.model.Announcement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Setter
@Getter
@NoArgsConstructor
public
class AnnouncementFrontend {
    Long id;
    Long courtOwnerId;
    String body;
    String cni; // Attachments
    String date;
    String time;
    String courtPic;
    String name;

    public AnnouncementFrontend(Announcement announcement) {
        this.id = announcement.getId();
        this.courtOwnerId = announcement.getCourtOwner().getId();
        this.body = announcement.getBody();
        this.cni = announcement.getImg();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(announcement.getDate());
        this.time = announcement.getTime().toLocalTime().toString();
        this.courtPic = announcement.getCourtOwner().getImage();
        this.name = announcement.getCourtOwner().getUserName();
    }
}