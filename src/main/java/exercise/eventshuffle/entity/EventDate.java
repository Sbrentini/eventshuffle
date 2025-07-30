package exercise.eventshuffle.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
@Table(name="event_date")
public class EventDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    /**
     * What event is the date associated to
     */
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="event_id")
    private Event event;

    @Column(name="date")
    private LocalDate date;

    /**
     * All the people that have voted for this date to be suitable for them
     */
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name="event_date_vote",
                joinColumns = @JoinColumn(name="event_date_id"),
                inverseJoinColumns = @JoinColumn(name="voter_id"))
    private List<Person> voters;
}
