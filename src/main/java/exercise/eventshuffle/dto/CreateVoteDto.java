package exercise.eventshuffle.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Used for JSON request body that is used to save a new vote to an event's date
 */
@Data
public class CreateVoteDto {
    private String name;
    private List<LocalDate> votes;
}