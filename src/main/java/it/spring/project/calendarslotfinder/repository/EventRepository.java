package it.spring.project.calendarslotfinder.repository;

import it.spring.project.calendarslotfinder.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    //Query Custom per filtrare gli eventi
    @Query("""
            SELECT e
            FROM Event  AS e
            WHERE e.userId IN :userIds
            AND e.endTime > :from
            AND e.startTime < :to
            ORDER BY e.startTime""")
    List<Event> findEvents(
            @Param("userIds") List<Long> userIds,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}