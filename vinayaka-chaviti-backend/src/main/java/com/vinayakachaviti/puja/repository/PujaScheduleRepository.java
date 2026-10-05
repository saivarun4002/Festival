package com.vinayakachaviti.puja.repository;

import com.vinayakachaviti.puja.entity.PujaSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PujaScheduleRepository extends JpaRepository<PujaSchedule, Long> {

    Page<PujaSchedule> findByPublishedTrueOrderByScheduledDateAscScheduledTimeAsc(Pageable pageable);

    @Query("SELECT p FROM PujaSchedule p WHERE p.published = true AND p.scheduledDate = :date ORDER BY p.scheduledTime ASC")
    List<PujaSchedule> findTodaysSchedule(@Param("date") LocalDate date);

    Page<PujaSchedule> findAllByOrderByScheduledDateAscScheduledTimeAsc(Pageable pageable);
}
