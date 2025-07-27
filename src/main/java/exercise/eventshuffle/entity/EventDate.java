package exercise.eventshuffle.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@Table(name="event_date")
public class EventDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name="event_id")
    @JsonBackReference
    private Event event;

    @Column(name="date")
    private LocalDate date;
}
