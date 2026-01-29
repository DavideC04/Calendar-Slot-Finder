package it.spring.project.calendarslotfinder.mapper;

import it.spring.project.calendarslotfinder.DTO.EventDTO;
import it.spring.project.calendarslotfinder.model.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    // metodo per il passaggio dei dati dall'Entity al DTO
    public EventDTO mapToDTO(Event event) {
        return new EventDTO(
                event.getId(),
                event.getUserId(),
                event.getTitle(),
                event.getStartTime(),
                event.getEndTime());
    }

    //metodo per il passaggio dei dati dal DTO all'Entity
    public Event toEntity(EventDTO dto) {
        return new Event(
                dto.getUserId(),
                dto.getTitle(),
                dto.getStartTime(),
                dto.getEndTime()
        );
    }
}
