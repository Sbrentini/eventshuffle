package exercise.eventshuffle.service;

import exercise.eventshuffle.dao.EventDao;
import exercise.eventshuffle.dao.EventDateDao;
import exercise.eventshuffle.dao.PersonDao;
import exercise.eventshuffle.dto.*;
import exercise.eventshuffle.entity.Event;
import exercise.eventshuffle.entity.EventDate;
import exercise.eventshuffle.entity.Person;
import exercise.eventshuffle.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    EventDao eventDao;

    @Mock
    EventDateDao eventDateDao;

    @Mock
    PersonDao personDao;

    @InjectMocks
    EventServiceImpl eventService;

    @Test
    void findEventList_success() {
        Event event1 = new Event();
        event1.setId(1L);
        event1.setName("Event A");

        Event event2 = new Event();
        event2.setId(2L);
        event2.setName("Event B");

        List<Event> events = List.of(event1, event2);

        Mockito.when(eventDao.findEventList()).thenReturn(events);
        List<CommonEventDto> result = eventService.findEventList();

        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting(CommonEventDto::getId, CommonEventDto::getName)
                .containsExactly(
                        tuple(1L, "Event A"),
                        tuple(2L, "Event B")
                );
    }

    @Test
    void findEventList_noEvents() {
        Mockito.when(eventDao.findEventList()).thenReturn(new ArrayList<>());
        List<CommonEventDto> result = eventService.findEventList();
        assertThat(result).hasSize(0);
    }

    @Test
    void findEventById_success() {
        Event event1 = new Event();
        event1.setId(1L);
        event1.setName("Event A");
        EventDate eventDate1 = new EventDate();
        eventDate1.setDate(java.time.LocalDate.of(2025, 12, 1));
        eventDate1.setVoters(List.of(new Person("Jerry"), new Person("Tom")));
        eventDate1.setEvent(event1);
        EventDate eventDate2 = new EventDate();
        eventDate2.setDate(java.time.LocalDate.of(2025, 12, 2));
        eventDate2.setEvent(event1);
        eventDate2.setVoters(List.of(new Person("Jerry"), new Person("Jane")));
        event1.setEventDateList(List.of(eventDate1, eventDate2));

        Mockito.when(eventDao.findById(1L)).thenReturn(event1);
        EventDto result = eventService.findEventById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Event A");

        assertThat(result.getVotes())
                .extracting(VoteDto::getDate, VoteDto::getPeople)
                .containsExactly(
                        tuple(LocalDate.of(2025, 12, 1), List.of("Jerry", "Tom")),
                        tuple(LocalDate.of(2025, 12, 2), List.of("Jerry", "Jane"))
                );
    }

    @Test
    void findEventById_notFound() {
        Mockito.when(eventDao.findById(1L)).thenReturn(null);
        NotFoundException exception = assertThrows(NotFoundException.class, () -> eventService.findEventById(1L));
        assertThat(exception.getMessage()).isEqualTo("Event not found by Id: 1");
    }

    @Test
    void saveEvent_validEvent() {
        CreateEventDto createEventDto = new CreateEventDto();
        createEventDto.setName("Isolation test party");
        createEventDto.setDates(List.of("2025-12-01", "2025-12-02"));

        Mockito.doAnswer(invocation -> {
            Event savedEvent = invocation.getArgument(0);
            savedEvent.setId(1L);
            return null;
        }).when(eventDao).save(Mockito.any(Event.class));

        long returnedId = eventService.saveEvent(createEventDto);

        assertEquals(1L, returnedId);
    }

    @Test
    void saveEvent_eventWithNoDates() {
        CreateEventDto createEventDto = new CreateEventDto();
        createEventDto.setName("Isolation test with no dates");
        createEventDto.setDates(List.of());

        Mockito.doAnswer(invocation -> {
            Event savedEvent = invocation.getArgument(0);
            savedEvent.setId(1L);
            return null;
        }).when(eventDao).save(Mockito.any(Event.class));

        long returnedId = eventService.saveEvent(createEventDto);
        // Saving new events without dates?
        assertEquals(1L, returnedId);
    }

    @Test
    void saveNewVote() {

    }

    @Test
    void findVoteResults() {

    }
}