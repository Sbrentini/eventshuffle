package exercise.eventshuffle.controller;

import exercise.eventshuffle.dto.CreateEventRequest;
import exercise.eventshuffle.dto.EventListResponse;
import exercise.eventshuffle.entity.Event;
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
    public EventListResponse findEventList() {
        return new EventListResponse(eventService.findEventList());
    }

    @GetMapping("/event/{id}")
    public Event findEvent(@PathVariable int id) {
        Event event = eventService.findById(id);

        if (event == null) {
            throw new RuntimeException("Event not found by Id: " + id);
        }
        return event;
    }

    @PostMapping("/event")
    public Map<String, Integer> saveNewEvent(@RequestBody CreateEventRequest createEventRequest) {
        int id = eventService.save(createEventRequest);
        return Collections.singletonMap("id", id);
    }
}
