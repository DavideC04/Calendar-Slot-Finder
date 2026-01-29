package it.spring.project.calendarslotfinder.controller;

import it.spring.project.calendarslotfinder.DTO.EventDTO;
import it.spring.project.calendarslotfinder.mapper.EventMapper;
import it.spring.project.calendarslotfinder.model.Event;
import it.spring.project.calendarslotfinder.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//REST controller per le operazioni CRUD
@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventRepository repo;

    @Autowired
    private EventMapper mapper;

    @GetMapping
    public List<EventDTO> getAllEvents() {
        return repo.findAll()
                .stream()
                .map(mapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable long id) {
        return repo.findById(id)
                .map(mapper::mapToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/new")
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO event) {
        Event e = mapper.toEntity(event);
        Event save = repo.save(e);
        return ResponseEntity.ok(mapper.mapToDTO(save));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @RequestBody EventDTO updatedEvent) {
        return repo.findById(id)
                .map(event -> {
                    event.setId(updatedEvent.getId());
                    event.setUserId(updatedEvent.getUserId());
                    event.setTitle(updatedEvent.getTitle());
                    event.setStartTime(updatedEvent.getStartTime());
                    event.setEndTime(updatedEvent.getEndTime());
                    Event save = repo.save(event);
                    return ResponseEntity.ok(mapper.mapToDTO(save));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
