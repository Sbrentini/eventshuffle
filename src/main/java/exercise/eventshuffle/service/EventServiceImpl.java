package exercise.eventshuffle.service;

import exercise.eventshuffle.dao.EventDao;
import exercise.eventshuffle.dto.CreateEventRequest;
import exercise.eventshuffle.dto.CommonEventDto;
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
    public List<CommonEventDto> findEventList() {
        List<Event> eventList = eventDao.findEventList();
        List<CommonEventDto> commonEventDtoList = new ArrayList<>();
        for (Event event : eventList) {
            CommonEventDto dto = new CommonEventDto(event.getId(), event.getName());
            commonEventDtoList.add(dto);
        }
        return commonEventDtoList;
    }

    @Override
    public EventDto findById(int id) {
        Event event = eventDao.findById(id);

        if (event == null) {
            throw new RuntimeException("Event not found by Id: " + id);
        }

        EventDto dto = new EventDto();
        dto.setId(event.getId());
        dto.setName(event.getName());
        List<LocalDate> localDates = event.getEventDateList().stream().map(EventDate::getDate).toList();
        dto.setDates(localDates);
        dto.setVotes(new ArrayList<>());

        return dto;
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
