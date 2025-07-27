package exercise.eventshuffle.dao;

import exercise.eventshuffle.entity.Event;

import java.util.List;

public interface EventDao {

    List<Event> findEventList();

    Event findById(int id);

    void save(Event event);
}
