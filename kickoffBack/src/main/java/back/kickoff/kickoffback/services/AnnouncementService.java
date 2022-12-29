package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.AddAnnouncmentCommand;
import back.kickoff.kickoffback.Commands.AnnouncmentFrontend;
import back.kickoff.kickoffback.model.Announcement;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.Subscription;
import back.kickoff.kickoffback.repositories.AnnouncementRepository;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
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

    public void addAnnouncement(AddAnnouncmentCommand command) throws Exception {

        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(command.getCourtOwnerId());
        if (courtOwnerOptional.isEmpty()) {
            throw  new Exception("Court Owner does not exist");
        }
        CourtOwner courtOwner = courtOwnerOptional.get();
        Announcement newAnnouncement = new Announcement();
        newAnnouncement.setTitle(command.getTitle());
        newAnnouncement.setBody(command.getBody());
        newAnnouncement.setImg(command.getAttachmentsURL());
        Date date;
        try {
            SimpleDateFormat obj = new SimpleDateFormat("MM/dd/yyyy");
            long date2 = obj.parse(command.getDateString()).getTime();
            date = new Date(date2);
        } catch (Exception e) {
            throw  new Exception("");
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

    public List<AnnouncmentFrontend> getAnnouncement(Long courtOwnerId) throws Exception {
        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(courtOwnerId);
        if (courtOwnerOptional.isEmpty()) {
            throw new Exception("CourtOwner do not exist");
        }
        CourtOwner courtOwner = courtOwnerOptional.get();
        List<Announcement> announcements = courtOwner.getAnnouncements();
        List<AnnouncmentFrontend> announcmentFrontends = new ArrayList<AnnouncmentFrontend>(announcements.size());
        for (Announcement a : announcements) {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String strDate = dateFormat.format(a.getDate());
            announcmentFrontends.add(new AnnouncmentFrontend(a.getId(), a.getCourtOwner().getId(), a.getTitle(), a.getBody(), a.getImg(), strDate));
        }
        return announcmentFrontends;
    }

    public String getSubscriptionAnnouncements(List<Subscription> subscriptions) {
        if (subscriptions.isEmpty())
            return "No subscriptions";
        Optional<CourtOwner> optionalCourtOwner;
        List<AnnouncmentFrontend> announcements = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            optionalCourtOwner = courtOwnerRepository.findById(subscription.getCoid());
            if (optionalCourtOwner.isPresent()) {
                List<Announcement> announcementsBackEnd = optionalCourtOwner.get().getAnnouncements();
                for (Announcement announcement : announcementsBackEnd) {
                    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    String strDate = dateFormat.format(announcement.getDate());
                    announcements.add(new AnnouncmentFrontend(
                            announcement.getId(),
                            announcement.getCourtOwner().getId(),
                            announcement.getTitle(),
                            announcement.getBody(),
                            announcement.getImg(), strDate));
                }
            }
        }
        return new Gson().toJson(announcements);
    }

}
