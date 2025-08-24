package exercise.eventshuffle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.eventshuffle.dto.CreateEventDto;
import exercise.eventshuffle.dto.EventDto;
import exercise.eventshuffle.dto.VoteDto;
import exercise.eventshuffle.exception.NotFoundException;
import exercise.eventshuffle.service.EventService;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static exercise.eventshuffle.util.TestJsonHelper.EVENT_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = EventController.class)
public class EventControllerTests {

    @Autowired
    private MockMvc mockMvc;

    // TODO refactor MockBean away
    @MockBean
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    @Getter
    @Setter
    private EventDto eventDto;

    @BeforeEach
    void setup() {
        initEventDto();
    }

    private void initEventDto() {
        eventDto = new EventDto();
        eventDto.setId(1L);
        eventDto.setName("Jake's secret party");
        eventDto.setDates(List.of(
                LocalDate.of(2014, 1, 1),
                LocalDate.of(2014, 1, 5),
                LocalDate.of(2014, 1, 12)
        ));
        VoteDto vote = new VoteDto();
        vote.setDate(LocalDate.of(2014, 1, 1));
        vote.setPeople(List.of("John", "Julia", "Paul", "Daisy"));
        eventDto.setVotes(List.of(vote));
    }

    /**
     * Test for checking JSON response.
     */
    @Test
    void testGetEventByIdJsonResponse() throws Exception {
        Mockito.when(eventService.findEventById(eventDto.getId())).thenReturn(eventDto);
        mockMvc.perform(get("/api/v1/event/" + eventDto.getId()))
                .andExpect(status().isOk()).andExpect(content().json(EVENT_JSON));
    }

    /**
     * TestCase to check an invalid id. The expected JSON response is
     * {
     *     "status": 404,
     *     "message": "Event not found by Id: {invalidId}",
     *     "timeStamp": {the current time}
     * }
     */
    @Test
    void testGetEvent_NotFound() throws Exception {
        long notFoundId = 999L;

        Mockito.when(eventService.findEventById(notFoundId))
                .thenThrow(new NotFoundException("Event not found by Id: " + notFoundId));

        mockMvc.perform(get("/api/v1/event/" + notFoundId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Event not found by Id: 999"));
    }

    /**
     * Test for Post method to see if id is returned.
     */
    @Test
    void testPostEvent() throws Exception {
        long expectedId = 111L;

        CreateEventDto createEventDto = new CreateEventDto();
        createEventDto.setName("Amusement park");
        createEventDto.setDates(List.of("2025-08-01", "2025-08-02", "2025-08-03"));

        Mockito.when(eventService.saveEvent(createEventDto)).thenReturn(expectedId);

        mockMvc.perform(post("/api/v1/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createEventDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expectedId));
    }
}
