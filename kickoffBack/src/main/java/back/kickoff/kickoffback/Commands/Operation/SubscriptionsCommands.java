package back.kickoff.kickoffback.Commands.Operation;

import back.kickoff.kickoffback.model.Subscription;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SubscriptionsCommands {

    private SubscriptionsCommands() {}

    public static Subscription constructSubscription(String request) {
        try {
            return new ObjectMapper().readValue(request, Subscription.class);
        } catch (Exception ignored) {
            return null;
        }
    }
}
