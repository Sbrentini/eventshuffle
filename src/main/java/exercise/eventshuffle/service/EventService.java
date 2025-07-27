package exercise.eventshuffle.service;

import exercise.eventshuffle.dto.CreateEventRequest;
import exercise.eventshuffle.dto.EventDto;
import exercise.eventshuffle.entity.Event;

import java.util.List;

public interface EventService {

    List<EventDto> findEventList();

    Event findById(int id);

    int save(CreateEventRequest createEventRequest);

}
