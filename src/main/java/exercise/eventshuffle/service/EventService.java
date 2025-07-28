package exercise.eventshuffle.service;

import exercise.eventshuffle.dto.CreateEventRequestDto;
import exercise.eventshuffle.dto.CommonEventDto;
import exercise.eventshuffle.dto.CreateVoteRequestDto;
import exercise.eventshuffle.dto.EventDto;

import java.util.List;

public interface EventService {

    List<CommonEventDto> findEventList();

    EventDto findById(int id);

    int save(CreateEventRequestDto createEventRequestDto);

    void saveNewVote(int eventId, CreateVoteRequestDto createVoteRequestDto);
}
