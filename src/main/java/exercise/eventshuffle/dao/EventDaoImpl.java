package exercise.eventshuffle.dao;

import exercise.eventshuffle.entity.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventDaoImpl implements EventDao {

    private EntityManager entityManager;

    @Autowired
    public EventDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Event> findEventList() {
        TypedQuery<Event> query = entityManager.createQuery("from Event", Event.class);
        return query.getResultList();
    }

    @Override
    public Event findById(long id) {
        return entityManager.find(Event.class, id);
    }

    @Override
    public String findNameByEventId(long eventId) {
        TypedQuery<String> query = entityManager.createQuery("select e.name from Event e where e.id=:eventId", String.class);
        query.setParameter("eventId", eventId);
        return query.getSingleResult();
    }

    @Override
    public void save(Event event) {
        entityManager.persist(event);
    }
}
