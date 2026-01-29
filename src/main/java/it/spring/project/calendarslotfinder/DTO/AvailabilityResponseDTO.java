package it.spring.project.calendarslotfinder.DTO;

import java.util.List;

public class AvailabilityResponseDTO {

    //Attributi
    private List<AvailabilityDTO> availableSlots;

    //Costruttore
    public AvailabilityResponseDTO(List<AvailabilityDTO> availableSlots) {
        this.availableSlots = availableSlots;
    }

    //Getter & Setter
    public List<AvailabilityDTO> getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(List<AvailabilityDTO> availableSlots) {
        this.availableSlots = availableSlots;
    }
}
