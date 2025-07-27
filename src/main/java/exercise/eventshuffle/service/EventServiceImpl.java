package exercise.eventshuffle.service;

import exercise.eventshuffle.dao.EventDao;
import exercise.eventshuffle.entity.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private EventDao eventDao;

    public EventServiceImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public List<Event> findEventList() {
        return eventDao.findEventList();
    }
}
