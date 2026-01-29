package it.spring.project.calendarslotfinder.DTO;

import java.time.LocalDateTime;

public class BreaksDTO {
    // Attributi
    private LocalDateTime startBreak;
    private LocalDateTime endBreak;

    // Costruttore
    public BreaksDTO(LocalDateTime startBreak, LocalDateTime endBreak) {
        this.startBreak = startBreak;
        this.endBreak = endBreak;
    }

    //Getters & Setters
    public LocalDateTime getStartBreak() {
        return startBreak;
    }

    public void setStartBreak(LocalDateTime startBreak) {
        this.startBreak = startBreak;
    }

    public LocalDateTime getEndBreak() {
        return endBreak;
    }

    public void setEndBreak(LocalDateTime endBreak) {
        this.endBreak = endBreak;
    }
}
