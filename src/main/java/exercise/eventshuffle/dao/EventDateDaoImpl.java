package exercise.eventshuffle.dao;

import exercise.eventshuffle.entity.EventDate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class EventDateDaoImpl implements EventDateDao{

    private EntityManager entityManager;

    @Autowired
    public EventDateDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<EventDate> findByEventIdAndDate(int eventId, List<LocalDate> voteDates) {
        String queryString = "select ed from EventDate ed where ed.event.id=:eventId and ed.date in :voteDates";

        TypedQuery<EventDate> query = entityManager.createQuery(queryString, EventDate.class);
        query.setParameter("eventId", eventId)
                .setParameter("voteDates", voteDates);

        return query.getResultList();
    }
}