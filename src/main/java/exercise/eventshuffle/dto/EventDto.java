package exercise.eventshuffle.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDto {
    private int id;
    private String name;

    public EventDto(int id, String name) {
        this.id = id;
        this.name=name;
    }
}