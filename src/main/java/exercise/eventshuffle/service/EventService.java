package exercise.eventshuffle.service;

import exercise.eventshuffle.dto.*;

import java.util.List;

public interface EventService {

    /**
     * Find all Events
     * @return list of ids and names of events
     */
    List<CommonEventDto> findEventList();

    /**
     * Find an event by id
     * @param eventId
     * @return EventDto
     */
    EventDto findEventById(long eventId);

    /**
     * save a new event
     * @param createEventDto
     * @return id of new event
     */
    long saveEvent(CreateEventDto createEventDto);

    /**
     * save a new vote to an existing event and date
     * @param eventId what date is the vote for
     * @param createVoteDto
     * @return EventDto
     */
    EventDto saveNewVote(long eventId, CreateVoteDto createVoteDto);

    /**
     * Find the dates of an event that all the voters voted for.
     * @param eventId
     * @return VoteResultsDto
     */
    VoteResultsDto findVoteResults(long eventId);
}
