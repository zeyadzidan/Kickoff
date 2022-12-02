package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.Court;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.model.CourtState;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import back.kickoff.kickoffback.repositories.CourtRepository;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class CourtOwnerAgent {
    private final CourtOwnerRepository courtOwnerRepository;
    private final CourtRepository courtRepository;

    public CourtOwnerAgent(CourtOwnerRepository courtOwnerRepository, CourtRepository courtRepository) {
        this.courtOwnerRepository = courtOwnerRepository;
        this.courtRepository = courtRepository;
    }
    private CourtOwner findCourtOwner(Long id)
    {
        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(id);
        if(!courtOwnerOptional.isPresent())
            throw new RuntimeException("CourtOwner Not Found");
        return courtOwnerOptional.get();
    }
    public String findCourtOwnerCourts(Long courtOwnerId)
    {
        CourtOwner source = findCourtOwner(courtOwnerId);
        if(source == null)
            return "CourtOwner Not Found" ;
        int n = source.getCourts().size();
        HashMap<String, Object> hmAns = new HashMap<>();
        hmAns.put("courtNumber", n);
        HashMap<String, Object> hmCourt = new HashMap<>();
        Court[] courtsArr = source.getCourts().toArray(new Court[n]);
        for(int i = 0; i < n; i++)
        {
            hmCourt.put(Integer.toString(i), courtsArr[i]);
        }
        hmAns.put("courts", hmCourt);
        return new Gson().toJson(hmAns);
    }
    public boolean createCourt(String information) throws JSONException {
        //schedule missing
        JSONObject jsonObject = new JSONObject(information);
        Long ownerId  =  jsonObject.getLong("ownerID");
        String courtName  =  jsonObject.getString("courtName");
        String type = jsonObject.getString("type");
        String discription = jsonObject.getString("discription");
        Optional<CourtOwner> courtOwnerOptional = courtOwnerRepository.findById(ownerId);
        if(courtOwnerOptional.isEmpty())
            return false;
        Court newCourt = new Court(courtName, courtOwnerOptional.get(), CourtState.Active, discription);
        courtRepository.save(newCourt);
        courtOwnerOptional.get().addCourt(newCourt);
        return true;
    }
}
