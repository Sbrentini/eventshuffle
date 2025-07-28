package exercise.eventshuffle.controller;

import exercise.eventshuffle.dto.CreateEventRequestDto;
import exercise.eventshuffle.dto.CreateVoteRequestDto;
import exercise.eventshuffle.dto.EventListResponseDto;
import exercise.eventshuffle.dto.EventDto;
import exercise.eventshuffle.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/event/list")
    public EventListResponseDto findEventList() {
        return new EventListResponseDto(eventService.findEventList());
    }

    @GetMapping("/event/{id}")
    public EventDto findEventById(@PathVariable int id) {
        return eventService.findById(id);
    }

    @PostMapping("/event")
    public Map<String, Integer> saveNewEvent(@RequestBody CreateEventRequestDto createEventRequestDto) {
        int id = eventService.save(createEventRequestDto);
        return Collections.singletonMap("id", id);
    }

    @PostMapping("/event/{id}/vote")
    public void saveNewVote(@PathVariable int id, @RequestBody CreateVoteRequestDto createVoteRequestDto) {
        eventService.saveNewVote(id, createVoteRequestDto);
    }

    @GetMapping("/event/{id}/results")
    public void findEventDatesSuitableForAllParticipants(@PathVariable int id) {

    }
}
