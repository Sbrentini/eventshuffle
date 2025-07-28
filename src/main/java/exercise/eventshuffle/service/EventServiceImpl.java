package exercise.eventshuffle.service;

import exercise.eventshuffle.dao.EventDao;
import exercise.eventshuffle.dao.EventDateDao;
import exercise.eventshuffle.dao.PersonDao;
import exercise.eventshuffle.dto.*;
import exercise.eventshuffle.entity.Event;
import exercise.eventshuffle.entity.EventDate;
import exercise.eventshuffle.entity.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private EventDao eventDao;

    private EventDateDao eventDateDao;

    private PersonDao personDao;

    public EventServiceImpl(EventDao eventDao,
                            EventDateDao eventDateDao,
                            PersonDao personDao) {
        this.eventDao = eventDao;
        this.eventDateDao = eventDateDao;
        this.personDao = personDao;
    }

    @Override
    public List<CommonEventDto> findEventList() {
        List<Event> eventList = eventDao.findEventList();
        List<CommonEventDto> commonEventDtoList = new ArrayList<>();
        for (Event event : eventList) {
            CommonEventDto dto = new CommonEventDto(event.getId(), event.getName());
            commonEventDtoList.add(dto);
        }
        return commonEventDtoList;
    }

    @Override
    public EventDto findById(int id) {
        Event event = eventDao.findById(id);

        if (event == null) {
            throw new RuntimeException("Event not found by Id: " + id);
        }

        EventDto dto = new EventDto();
        dto.setId(event.getId());
        dto.setName(event.getName());
        List<LocalDate> localDates = event.getEventDateList().stream().map(EventDate::getDate).toList();
        dto.setDates(localDates);

        List<VoteDto> voteDtoList = initVotes(event);

        dto.setVotes(voteDtoList);
        return dto;
    }

    private static List<VoteDto> initVotes(Event event) {
        List<VoteDto> voteDtoList = new ArrayList<>();
        for (EventDate eventDate : event.getEventDateList()) {
            VoteDto voteDto = new VoteDto();
            voteDto.setDate(eventDate.getDate());
            List<String> voterNames = eventDate.getVoters().stream().map(Person::getName).toList();
            voteDto.setPeople(voterNames);
            if (!voteDto.getPeople().isEmpty()) {
                voteDtoList.add(voteDto);
            }
        }
        return voteDtoList;
    }

    @Override
    @Transactional
    public int save(CreateEventRequestDto createEventRequestDto) {
        Event event = new Event();
        event.setName(createEventRequestDto.getName());

        List<EventDate> eventDates = new ArrayList<>();

        for (String dateString : createEventRequestDto.getDates()) {
            EventDate eventDate = new EventDate();
            eventDate.setEvent(event);
            eventDate.setDate(LocalDate.parse(dateString));
            eventDates.add(eventDate);
        }

        event.setEventDateList(eventDates);
        eventDao.save(event);

        return event.getId();
    }

    @Override
    @Transactional
    public void saveNewVote(int eventId, CreateVoteRequestDto createVoteRequestDto) {
        Person person = new Person(createVoteRequestDto.getName());

        List<EventDate> eventDateList = eventDateDao.findByEventIdAndDate(eventId, createVoteRequestDto.getVotes());
        person.setSuitableEventDates(eventDateList);

        for (EventDate eventDate : eventDateList) {
            eventDate.getVoters().add(person);
        }

        personDao.save(person);
    }
}
