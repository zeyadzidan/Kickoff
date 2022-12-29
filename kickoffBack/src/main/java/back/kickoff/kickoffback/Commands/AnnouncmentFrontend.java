package back.kickoff.kickoffback.Commands;

import lombok.*;

@Setter
@Getter
public
class AnnouncmentFrontend {
    Long id;
    Long courtOwnerId;
    String title;
    String body;
    String cni; // Attachments
    String date;

    public AnnouncmentFrontend(Long id, Long courtOwnerId, String title, String body, String cni, String date) {
        this.id = id;
        this.courtOwnerId = courtOwnerId;
        this.title = title;
        this.body = body;
        this.cni = cni;
        this.date = date;
    }
}