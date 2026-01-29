package it.spring.project.calendarslotfinder.DTO;

import java.util.List;

public class AvailabilityResponseDTO {
    private List<AvailabilityDTO> availableSlots;

    public AvailabilityResponseDTO(List<AvailabilityDTO> availableSlots) {
        this.availableSlots = availableSlots;
    }

    public List<AvailabilityDTO> getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(List<AvailabilityDTO> availableSlots) {
        this.availableSlots = availableSlots;
    }
}
