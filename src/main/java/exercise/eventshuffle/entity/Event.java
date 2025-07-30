package exercise.eventshuffle.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name="event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    /**
     * The name of the event
     */
    @Column(name="name")
    private String name;

    /**
     * A list of possible dates when an event could be held
     */
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventDate> eventDateList;
}
