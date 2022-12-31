package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.AddAnnouncementCommand;
import back.kickoff.kickoffback.Commands.AnnouncementFrontend;
import back.kickoff.kickoffback.model.Announcement;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Subscription;
import back.kickoff.kickoffback.repositories.AnnouncementRepository;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementService {

    private final CourtOwnerRepository courtOwnerRepository;
    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(CourtOwnerRepository courtOwnerRepository, AnnouncementRepository announcementRepository) {
        this.courtOwnerRepository = courtOwnerRepository;
        this.announcementRepository = announcementRepository;
    }

    public void addAnnouncement(AddAnnouncementCommand command) throws Exception {

        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(command.getCourtOwnerId());
        if (courtOwnerOptional.isEmpty()) {
            throw new Exception("Court Owner does not exist");
        }
        CourtOwner courtOwner = courtOwnerOptional.get();
        Announcement newAnnouncement = new Announcement();
        newAnnouncement.setBody(command.getBody());
        newAnnouncement.setImg(command.getAttachmentsURL());
        Date date;
        try {
            SimpleDateFormat obj = new SimpleDateFormat("MM/dd/yyyy");
            long date2 = obj.parse(command.getDateString()).getTime();
            date = new Date(date2);
        } catch (Exception e) {
            throw new Exception("");
        }
        newAnnouncement.setDate(date);
        LocalTime lt = LocalTime.now() ;
        Time time =  Time.valueOf (lt) ;
        newAnnouncement.setTime(time);

        newAnnouncement.setCourtOwner(courtOwner);
        courtOwner.getAnnouncements().add(newAnnouncement);

        announcementRepository.save(newAnnouncement);
        courtOwnerRepository.save(courtOwner);
    }

    public String deleteAnnouncement(Long courtOwnerId, String information) throws JSONException {
        JSONObject jsonObject = new JSONObject(information);
        Long id = jsonObject.getLong("id");

        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(courtOwnerId);
        if (courtOwnerOptional.isEmpty()) {
            return "CourtOwner do not exist";
        }
        CourtOwner courtOwner = courtOwnerOptional.get();

        Optional<Announcement> announcementOptional = announcementRepository.findById(id);
        if (announcementOptional.isEmpty()) {
            return "Announcement do not exist";
        }
        Announcement announcement = announcementOptional.get();

        if (!courtOwner.getId().equals(announcement.getCourtOwner().getId())) {
            return "Announcement do not belong to that CourtOwner";
        }
        courtOwner.getAnnouncements().remove(announcement);
        announcementRepository.deleteById(announcement.getId());
        courtOwnerRepository.save(courtOwner);
        return "Success";
    }

    public List<AnnouncementFrontend> getAnnouncement(Long courtOwnerId) throws Exception {
        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(courtOwnerId);
        if (courtOwnerOptional.isEmpty()) {
            throw new Exception("CourtOwner do not exist");
        }
        CourtOwner courtOwner = courtOwnerOptional.get();
        List<Announcement> announcements = courtOwner.getAnnouncements();
        List<AnnouncementFrontend> announcementFrontends = new ArrayList<AnnouncementFrontend>(announcements.size());
        for (Announcement a : announcements) {
            announcementFrontends.add(new AnnouncementFrontend(a));
        }
        return announcementFrontends;
    }

    public List<AnnouncementFrontend> getSubscriptionAnnouncements(List<Subscription> subscriptions) {
        if (subscriptions.isEmpty())
            return new ArrayList<>();
        Optional<CourtOwner> optionalCourtOwner;
        List<AnnouncementFrontend> announcements = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            optionalCourtOwner = courtOwnerRepository.findById(subscription.getCoid());
            if (optionalCourtOwner.isPresent()) {
                List<Announcement> announcementsBackEnd = optionalCourtOwner.get().getAnnouncements();
                for (Announcement announcement : announcementsBackEnd) {
                    announcements.add(new AnnouncementFrontend(announcement));
                }
            }
        }
        return announcements;
    }
}
