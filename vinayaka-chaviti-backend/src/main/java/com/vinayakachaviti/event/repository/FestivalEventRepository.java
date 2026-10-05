package com.vinayakachaviti.event.repository;

import com.vinayakachaviti.common.enums.EventCategory;
import com.vinayakachaviti.event.entity.FestivalEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FestivalEventRepository extends JpaRepository<FestivalEvent, Long> {

    @Query("""
            SELECT e FROM FestivalEvent e
            WHERE (:festivalId IS NULL OR e.festival.id = :festivalId)
              AND (:category IS NULL OR e.category = :category)
              AND (:date IS NULL OR e.eventDate = :date)
              AND (:publishedOnly = false OR e.published = true)
              AND (:search IS NULL OR LOWER(e.title) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<FestivalEvent> search(
            @Param("festivalId") Long festivalId,
            @Param("category") EventCategory category,
            @Param("date") LocalDate date,
            @Param("publishedOnly") boolean publishedOnly,
            @Param("search") String search,
            Pageable pageable
    );

    @Query("""
            SELECT e FROM FestivalEvent e
            WHERE e.eventDate = :date AND e.published = true
            ORDER BY e.startTime ASC
            """)
    List<FestivalEvent> findTodaysEvents(@Param("date") LocalDate date);

    @Query("""
            SELECT e FROM FestivalEvent e
            WHERE e.published = true AND (e.eventDate > :today OR (e.eventDate = :today))
            ORDER BY e.eventDate ASC, e.startTime ASC
            """)
    List<FestivalEvent> findUpcomingEvents(@Param("today") LocalDate today, Pageable pageable);
}
