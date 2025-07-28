package exercise.eventshuffle.service;

import exercise.eventshuffle.dto.CreateEventRequest;
import exercise.eventshuffle.dto.CommonEventDto;
import exercise.eventshuffle.dto.EventDto;

import java.util.List;

public interface EventService {

    List<CommonEventDto> findEventList();

    EventDto findById(int id);

    int save(CreateEventRequest createEventRequest);

}
