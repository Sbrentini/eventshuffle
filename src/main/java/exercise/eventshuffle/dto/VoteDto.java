package exercise.eventshuffle.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class VoteDto {
    private LocalDate date;
    private List<String> people;
}