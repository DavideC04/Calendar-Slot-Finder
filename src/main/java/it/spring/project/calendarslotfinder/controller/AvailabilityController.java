package it.spring.project.calendarslotfinder.controller;

import it.spring.project.calendarslotfinder.DTO.AvailabilityDTO;
import it.spring.project.calendarslotfinder.DTO.SlotDTO;
import it.spring.project.calendarslotfinder.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//REST Controller per trovare gli slot liberi
@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    private EventService service;

    @PostMapping("/search")
    public ResponseEntity<List<SlotDTO>> getAvailableSlots(@RequestBody AvailabilityDTO availability) {
        List<SlotDTO> slots = service.getSlots(availability);
        return ResponseEntity.ok(slots);
    }
}
