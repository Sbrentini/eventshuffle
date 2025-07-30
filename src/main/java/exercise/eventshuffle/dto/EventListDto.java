package exercise.eventshuffle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * A wrapper that lists events as a response
 */
@Data
@AllArgsConstructor
public class EventListDto {
    private List<CommonEventDto> events;
}
