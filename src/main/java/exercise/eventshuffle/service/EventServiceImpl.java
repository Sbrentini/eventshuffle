package exercise.eventshuffle.service;

import exercise.eventshuffle.dao.EventDao;
import exercise.eventshuffle.dto.CreateEventRequest;
import exercise.eventshuffle.dto.EventDto;
import exercise.eventshuffle.entity.Event;
import exercise.eventshuffle.entity.EventDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private EventDao eventDao;

    public EventServiceImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public List<EventDto> findEventList() {
        List<Event> eventList = eventDao.findEventList();
        List<EventDto> eventDtoList = new ArrayList<>();
        for (Event event : eventList) {
            EventDto dto = new EventDto(event.getId(), event.getName());
            eventDtoList.add(dto);
        }
        return eventDtoList;
    }

    @Override
    public Event findById(int id) {
        return eventDao.findById(id);
    }

    @Override
    @Transactional
    public int save(CreateEventRequest createEventRequest) {
        Event event = new Event();
        event.setName(createEventRequest.getName());

        List<EventDate> eventDates = new ArrayList<>();

        for (String dateString : createEventRequest.getDates()) {
            EventDate eventDate = new EventDate();
            eventDate.setEvent(event);
            eventDate.setDate(LocalDate.parse(dateString));
            eventDates.add(eventDate);
        }

        event.setEventDateList(eventDates);
        eventDao.save(event);

        return event.getId();
    }
}
