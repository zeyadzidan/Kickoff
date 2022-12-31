package back.kickoff.kickoffback.services;

import back.kickoff.kickoffback.Commands.CourtOwnerFrontEnd;
import back.kickoff.kickoffback.Commands.CourtOwnerSearchCommand;
import back.kickoff.kickoffback.Commands.SearchCommand;
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

    private int getDistance(Double a_longitude, Double a_latitude, Double b_longitude, Double b_latitude)
    {
        double distance = Math.sqrt(Math.pow(Math.sin((b_latitude -a_latitude)/2), 2) + Math.cos(a_latitude) * Math.cos(b_latitude)
        * Math.pow(Math.sin((b_longitude -a_longitude)/2), 2) );
        distance = 2 * 6371 * Math.asin(distance);
        int Distance = (int )distance;
        return Distance;
    }
    public String getNearestCourtOwners(SearchCommand searchCommand) throws JSONException {
//        System.out.println(information);
//        JSONObject jsonObject = new JSONObject(information);
        Double xAxis = searchCommand.getXAxis();
        Double yAxis = searchCommand.getYAxis();
        String keyword = searchCommand.getKeyword();
        String keywordBegin = keyword.toUpperCase().concat("%");
        keyword = "%".concat(keyword.concat("%"));
        System.out.println("keyword: " + keyword + " keywordBegin: "+ keywordBegin);
        List<CourtOwner> courtOwners =  courtOwnerRepository.searchNearestCourtOwner(xAxis, yAxis, keyword, keywordBegin);
        ArrayList<CourtOwnerSearchCommand> commands = new ArrayList<>();
        for(int i = 0; i < courtOwners.size(); i++)
        {
            System.out.println(courtOwners.get(i).getEmail() + " " + courtOwners.get(i).getId());
            CourtOwner courtOwner = courtOwners.get(i);
            int distance = getDistance(xAxis, yAxis, courtOwner.getXAxis(), courtOwner.getYAxis());
            commands.add(new CourtOwnerSearchCommand(courtOwner.getId(), courtOwner.getUserName(),
                    courtOwner.getImage(), distance, Double.valueOf(courtOwner.getRating())));
        }
        Collections.sort(commands);
//        for(int i = 0; i < commands.size(); i++)
//            System.out.println(commands.get(i).toString());
        return new Gson().toJson(commands);
    }


    public String getCourtOwner(Long courtOwnerID){
        Optional<CourtOwner> courtOwner = courtOwnerRepository.findById(courtOwnerID);
        if (courtOwner.isEmpty())
            return "Not found";

        CourtOwner co = courtOwner.get();
        CourtOwnerFrontEnd courtOwnerFrontEnd = new CourtOwnerFrontEnd(co);

        return new Gson().toJson(courtOwnerFrontEnd);

    }

}
