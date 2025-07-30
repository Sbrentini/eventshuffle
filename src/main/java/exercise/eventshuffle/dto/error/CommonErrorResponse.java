package exercise.eventshuffle.dto.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter @Setter
public class CommonErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timeStamp;

    public CommonErrorResponse(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }
}