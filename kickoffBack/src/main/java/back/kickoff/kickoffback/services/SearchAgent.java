package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SearchAgent {
    private final CourtOwnerRepository courtOwnerRepository;

    public SearchAgent(CourtOwnerRepository courtOwnerRepository) {
        this.courtOwnerRepository = courtOwnerRepository;
    }
    public String getNearestCourtOwners(String information) throws JSONException {
        System.out.println(information);
        JSONObject jsonObject = new JSONObject(information);
        Double xAxis = jsonObject.getDouble("xAxis");
        Double yAxis = jsonObject.getDouble("yAxis");
        String keyword = jsonObject.getString("keyword");
        String keywordBegin = keyword.toUpperCase().concat("%");
        keyword = "%".concat(keyword.concat("%"));
        System.out.println("keyword: " + keyword + " keywordBegin: "+ keywordBegin);
        List<CourtOwner> courtOwners =  courtOwnerRepository.searchNearestCourtOwner(xAxis, yAxis, keyword, keywordBegin);
        HashMap<String, Object> hm = new HashMap<>();
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<Long> ids = new ArrayList<>();
            ArrayList<String> images = new ArrayList<>();
        for(int i = 0; i < Math.min(courtOwners.size(), 5); i++)
        {
            System.out.println(courtOwners.get(i).getEmail() + " " + courtOwners.get(i).getId());
            usernames.add(courtOwners.get(i).getUserName());
            ids.add(courtOwners.get(i).getId());
            images.add(courtOwners.get(i).getImage());
        }
        hm.put("N", courtOwners.size());
        hm.put("UserNames", usernames);
        hm.put("Ids", ids);
        hm.put("images", images);
        return new Gson().toJson(hm);
    }
}
