package exercise.eventshuffle.controller;

import exercise.eventshuffle.dto.*;
import exercise.eventshuffle.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/list")
    public EventListDto findEventList() {
        return new EventListDto(eventService.findEventList());
    }

    @GetMapping("/{id}")
    public EventDto findEventById(@PathVariable long id) {
        return eventService.findEventById(id);
    }

    @PostMapping
    public Map<String, Long> saveNewEvent(@RequestBody CreateEventDto createEventDto) {
        long eventId = eventService.saveEvent(createEventDto);
        return Collections.singletonMap("id", eventId);
    }

    @PostMapping("/{id}/vote")
    public EventDto saveNewVote(@PathVariable long id, @RequestBody CreateVoteDto createVoteDto) {
        return eventService.saveNewVote(id, createVoteDto);
    }

    @GetMapping("/{id}/results")
    public VoteResultsDto findEventDatesSuitableForAllParticipants(@PathVariable long id) {
        return eventService.findVoteResults(id);
    }
}
