package exercise.eventshuffle.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class VoteResultsDto {
    private long id;
    private String name;
    private List<VoteDto> suitableDates;

    public VoteResultsDto(long eventId, String eventName, List<VoteDto> voteDtoList) {
        this.id = eventId;
        this.name = eventName;
        this.suitableDates = voteDtoList;
    }
}
