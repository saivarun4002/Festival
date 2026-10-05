package com.vinayakachaviti.festival.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import com.vinayakachaviti.festival.entity.Festival;

public interface FestivalRepository extends JpaRepository<Festival, Long> {
    Optional<Festival> findByYear(Integer year);
    boolean existsByYear(Integer year);

    @Query("SELECT f FROM Festival f WHERE CURRENT_DATE BETWEEN f.startDate AND f.endDate")
    Optional<Festival> findActiveFestival();

    @Query("SELECT f FROM Festival f WHERE f.endDate >= CURRENT_DATE ORDER BY f.startDate ASC")
    List<Festival> findUpcomingOrActiveFestivals(Pageable pageable);

    @Query("SELECT f FROM Festival f ORDER BY f.endDate DESC")
    List<Festival> findMostRecentFestivals(Pageable pageable);

    /**
     * Resolves the festival to show on the public site: prefers one currently
     * running (today between start/end), else the soonest upcoming one, else
     * falls back to the most recently ended one so the page never 404s.
     */
    default Optional<Festival> findCurrentFestival() {
        Optional<Festival> active = findActiveFestival();
        if (active.isPresent()) {
            return active;
        }
        List<Festival> upcoming = findUpcomingOrActiveFestivals(Pageable.ofSize(1));
        if (!upcoming.isEmpty()) {
            return Optional.of(upcoming.get(0));
        }
        List<Festival> recent = findMostRecentFestivals(Pageable.ofSize(1));
        return recent.isEmpty() ? Optional.empty() : Optional.of(recent.get(0));
    }
}