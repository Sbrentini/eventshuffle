package exercise.eventshuffle.dao;

import exercise.eventshuffle.entity.EventDate;

import java.time.LocalDate;
import java.util.List;

public interface EventDateDao {
    List<EventDate> findByEventIdAndDate(long eventId, List<LocalDate> voteDates);

    List<EventDate> findEventDateByEventId(long eventId);
}