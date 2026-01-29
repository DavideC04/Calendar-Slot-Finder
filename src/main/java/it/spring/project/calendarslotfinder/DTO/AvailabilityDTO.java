package it.spring.project.calendarslotfinder.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class AvailabilityDTO {

    //Attributi
    private List<Long> userIds;
    private LocalDateTime from;
    private LocalDateTime to;
    private int duration;
    private int buffer;
    private int results;
    private LocalDateTime workStart;
    private LocalDateTime workEnd;
    private List<BreaksDTO> breaks;

    public AvailabilityDTO(List<Long> userIds, LocalDateTime from, LocalDateTime to, int duration, int buffer, int results, LocalDateTime workStart, LocalDateTime workEnd, List<BreaksDTO> breaks) {
        this.userIds = userIds;
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.buffer = buffer;
        this.results = results;
        this.workStart = workStart;
        this.workEnd = workEnd;
        this.breaks = breaks;
    }

    //Getters & Setters

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getBuffer() {
        return buffer;
    }

    public void setBuffer(int buffer) {
        this.buffer = buffer;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public LocalDateTime getWorkStart() {
        return workStart;
    }

    public void setWorkStart(LocalDateTime workStart) {
        this.workStart = workStart;
    }

    public LocalDateTime getWorkEnd() {
        return workEnd;
    }

    public void setWorkEnd(LocalDateTime workEnd) {
        this.workEnd = workEnd;
    }

    public List<BreaksDTO> getBreaks() {
        return breaks;
    }

    public void setBreaks(List<BreaksDTO> breaks) {
        this.breaks = breaks;
    }
}
