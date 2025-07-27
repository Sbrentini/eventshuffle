package exercise.eventshuffle.dto;

import lombok.Data;

import java.util.List;

/**
 * Used for JSON request body that is used to save a new event
 */
@Data
public class CreateEventRequest {
    private String name;
    private List<String> dates;
}