package it.spring.project.calendarslotfinder.service;

import it.spring.project.calendarslotfinder.DTO.AvailabilityDTO;
import it.spring.project.calendarslotfinder.DTO.BreaksDTO;
import it.spring.project.calendarslotfinder.DTO.SlotDTO;
import it.spring.project.calendarslotfinder.model.Event;
import it.spring.project.calendarslotfinder.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public List<SlotDTO> getSlots(AvailabilityDTO search) {
        List<Event> events = eventRepository.findEvents(
                search.getUserIds(),
                search.getFrom(),
                search.getTo()
        );
        List<Event> bufferEvents = applyBuffer(events, search.getBuffer());
        return calculateSlots(bufferEvents, search);
    }

    //metodo per il buffer
    private List<Event> applyBuffer(List<Event> events, int buffer) {
        return events.stream()
                .map(e -> new Event(
                        e.getUserId(),
                        e.getTitle(),
                        e.getStartTime().minusMinutes(buffer),
                        e.getEndTime().plusMinutes(buffer)
                ))
                .sorted(Comparator.comparing(Event::getStartTime))
                .toList();
    }

    //metodo per calcolare gli slot
    private List<SlotDTO> calculateSlots(List<Event> events, AvailabilityDTO search) {

        //definisco gli intervalli di ricerca per gli slot
        List<SlotDTO> slots = new ArrayList<>();
        LocalDateTime start = search.getFrom();
        LocalDateTime end = search.getTo();

        //condizione per rispettare gli orari lavorativi
        if (search.getWorkStart() != null && start.isBefore(search.getWorkStart())) {
            start = search.getWorkStart();
        }
        if (search.getWorkEnd() != null && end.isAfter(search.getWorkEnd())) {
            end = search.getWorkEnd();
        }

        //durata per ogni slot
        Duration duration = Duration.ofMinutes(search.getDuration());

        //ciclo for:each sugli eventi
        for (Event event : events) {

            //controlla se c'è spazio prima dell'evento per uno slot
            //con il metodo skipBreaks salta eventuali pause
            while (Duration.between(start, event.getStartTime()).compareTo(duration) >= 0) {
                LocalDateTime slotStart = skipBreaks(start, search.getBreaks());
                LocalDateTime slotEnd = slotStart.plus(duration);

                //esclude slot che superano l'intervallo della richiesta
                if (!slotEnd.isAfter(slotStart)) {
                    break;
                }

                if (slotEnd.isAfter(end))
                    return slots;

                //controllo che lo slot non entri in una pausa
                if (!checkBreaks(slotStart, slotEnd, search.getBreaks())) {
                    slots.add(new SlotDTO(slotStart, slotEnd));
                    if (search.getResults() > 0 && slots.size() >= search.getResults()) {
                        return slots;
                    }
                }

                //aggiorna lo start per non sovrapporre gli slot
                start = slotEnd;
            }
            //aggiorna start alla fine dell'evento, se necessario
            if (event.getEndTime().isAfter(start)) {
                start = event.getEndTime();
            }
        }

        //controlla slot disponibili dopo l'ultimo evento
        while (Duration.between(start, end).compareTo(duration) >= 0) {
            LocalDateTime slotStart = skipBreaks(start, search.getBreaks());
            LocalDateTime slotEnd = slotStart.plus(duration);

            if (!slotEnd.isAfter(slotStart)) {
                break;
            }
            if (slotEnd.isAfter(end))
                break;

            if (!checkBreaks(slotStart, slotEnd, search.getBreaks())) {
                slots.add(new SlotDTO(slotStart, slotEnd));
                if (search.getResults() > 0 && slots.size() >= search.getResults()) {
                    break;
                }
            }
            start = slotEnd; //slot non sovrapposti
        }
        return slots;
    }

    //metodo di controllo se uno slot va parzialmente o totalmente in una pausa
    private boolean checkBreaks(LocalDateTime start, LocalDateTime end, List<BreaksDTO> breaks) {
        if (breaks == null) {
            return false;
        }
        for (BreaksDTO b : breaks) {
            if (start.isBefore(b.getEndBreak()) && end.isAfter(b.getStartBreak())) {
                return true; // se c'è sovrapposizione, restituisce true
            }
        }
        return false;

    }

    //metodo per spostare lo start dello slot, se cade all'interno di una pausa, alla fine della stessa.
    private LocalDateTime skipBreaks(LocalDateTime start, List<BreaksDTO> breaks) {
        if (breaks == null) return start;

        for (BreaksDTO b : breaks) {
            if (!start.isBefore(b.getStartBreak()) && start.isBefore(b.getEndBreak())) {
                return b.getEndBreak();
            }
        }
        return start;
    }
}
