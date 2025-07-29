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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public EventDto findEventById(long eventId) {
        Event event = eventDao.findById(eventId);

        if (event == null) {
            throw new RuntimeException("Event not found by Id: " + eventId);
        }

        EventDto dto = new EventDto();
        dto.setId(event.getId());
        dto.setName(event.getName());
        List<LocalDate> localDates = event.getEventDateList().stream().map(EventDate::getDate).toList();
        dto.setDates(localDates);

        List<VoteDto> voteDtoList = initVotes(event.getEventDateList());

        dto.setVotes(voteDtoList);
        return dto;
    }

    private static List<VoteDto> initVotes(List<EventDate> eventDateList) {
        List<VoteDto> voteDtoList = new ArrayList<>();
        for (EventDate eventDate : eventDateList) {
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
    public long save(CreateEventDto createEventDto) {
        Event event = new Event();
        event.setName(createEventDto.getName());

        List<EventDate> eventDates = new ArrayList<>();

        for (String dateString : createEventDto.getDates()) {
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
    public EventDto saveNewVote(long eventId, CreateVoteDto createVoteDto) {
        Person person = new Person(createVoteDto.getName());

        List<EventDate> eventDateList = eventDateDao.findByEventIdAndDate(eventId, createVoteDto.getVotes());
        person.setSuitableEventDates(eventDateList);

        for (EventDate eventDate : eventDateList) {
            eventDate.getVoters().add(person);
        }

        personDao.save(person);

        return findEventById(eventId);
    }

    @Override
    public VoteResultsDto findVoteResults(long eventId) {
        List<EventDate> eventDateList = eventDateDao.findEventDateByEventId(eventId);

        Set<Long> allVoterIds = new HashSet<>();
        for (EventDate eventDate : eventDateList) {
            for (Person person : eventDate.getVoters()) {
                allVoterIds.add(person.getId());
            }
        }

        List<VoteDto> voteDtoList = new ArrayList<>();

        if (!allVoterIds.isEmpty()) {
            for (EventDate eventDate : eventDateList) {
                Set<Long> currentVoterIds = new HashSet<>();
                for (Person person : eventDate.getVoters()) {
                    currentVoterIds.add(person.getId());
                }
                if (currentVoterIds.containsAll(allVoterIds)) {
                    VoteDto voteDto = new VoteDto();
                    voteDto.setDate(eventDate.getDate());
                    List<String> voterNames = eventDate.getVoters().stream().map(Person::getName).toList();
                    voteDto.setPeople(voterNames);
                    voteDtoList.add(voteDto);
                }
            }
        }

        String eventName = eventDao.findNameByEventId(eventId);
        return new VoteResultsDto(eventId, eventName, voteDtoList);
    }
}
