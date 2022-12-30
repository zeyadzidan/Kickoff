package back.kickoff.kickoffback.Commands;

import back.kickoff.kickoffback.model.Subscription;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class SearchCommand {
    @NotNull
    String keyword;
    @NotNull
    Double xAxis;
    @NotNull
    Double yAxis;
}
