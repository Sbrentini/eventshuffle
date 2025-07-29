package exercise.eventshuffle.service;

import exercise.eventshuffle.dto.*;

import java.util.List;

public interface EventService {

    List<CommonEventDto> findEventList();

    EventDto findEventById(long eventId);

    long save(CreateEventDto createEventDto);

    EventDto saveNewVote(long eventId, CreateVoteDto createVoteDto);

    VoteResultsDto findVoteResults(long eventId);
}
