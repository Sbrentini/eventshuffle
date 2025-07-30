package exercise.eventshuffle.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EventDto extends CommonEventDto {
    List<LocalDate> dates;
    List<VoteDto> votes;
}
