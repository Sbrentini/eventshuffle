package exercise.eventshuffle.controller;

import exercise.eventshuffle.entity.Event;
import exercise.eventshuffle.service.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/list")
    public List<Event> findEventList() {
        return eventService.findEventList();
    }
}
