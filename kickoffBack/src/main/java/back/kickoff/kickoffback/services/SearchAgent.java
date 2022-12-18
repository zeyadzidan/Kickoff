package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.CourtOwnerSearchCommand;
import back.kickoff.kickoffback.model.CourtOwner;
import back.kickoff.kickoffback.repositories.CourtOwnerRepository;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class SearchAgent {
    private final CourtOwnerRepository courtOwnerRepository;

    public SearchAgent(CourtOwnerRepository courtOwnerRepository) {
        this.courtOwnerRepository = courtOwnerRepository;
    }

    private Double getDistance(Double a_longitude, Double a_latitude, Double b_longitude, Double b_latitude)
    {
        Double distance = Math.sqrt(Math.pow(Math.sin((b_latitude -a_latitude)/2), 2) + Math.cos(a_latitude) * Math.cos(b_latitude)
        * Math.pow(Math.sin((b_longitude -a_longitude)/2), 2) );
        distance = 2 * 6371 * Math.asin(distance);
        return distance;
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
        ArrayList<CourtOwnerSearchCommand> commands = new ArrayList<>();
        for(int i = 0; i < courtOwners.size(); i++)
        {
            System.out.println(courtOwners.get(i).getEmail() + " " + courtOwners.get(i).getId());
            CourtOwner courtOwner = courtOwners.get(i);
            Double distance = getDistance(xAxis, yAxis, courtOwner.getXAxis(), courtOwner.getYAxis());
            commands.add(new CourtOwnerSearchCommand(courtOwner.getId(), courtOwner.getUserName(),
                    courtOwner.getImage(), distance, Double.valueOf(courtOwner.getRating())));
        }
//        for(int i = 0; i < commands.size(); i++)
//            System.out.println(commands.get(i).toString());
        return new Gson().toJson(commands);
    }


    public String getCourtOwner(Long courtOwnerID){
        Optional<CourtOwner> courtOwner = courtOwnerRepository.findById(courtOwnerID);
        if (courtOwner.isEmpty())
            return "Not found";

        Map<String, Object> res = new HashMap<>();
        CourtOwner co = courtOwner.get();
        res.put("id", co.getId());
        res.put("userName", co.getUserName());
        res.put("email", co.getEmail());
        res.put("location", co.getLocation());
        res.put("rating", String.valueOf(co.getRating()));
        res.put("image", co.getImage());
        res.put("phoneNumber", co.getPhoneNumber());
        res.put("xAxis", co.getXAxis());
        res.put("yAxis", co.getYAxis());

        return new Gson().toJson(res);

    }

}
