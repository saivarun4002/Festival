package com.vinayakachaviti.event.entity;

import com.vinayakachaviti.common.enums.EventCategory;
import com.vinayakachaviti.festival.entity.Festival;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "events", indexes = {
    @Index(name = "idx_events_festival_id", columnList = "festival_id"),
    @Index(name = "idx_events_date", columnList = "event_date"),
    @Index(name = "idx_events_category", columnList = "category"),
    @Index(name = "idx_events_published", columnList = "published")
})
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class FestivalEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "festival_id", nullable = false, foreignKey = @ForeignKey(name = "fk_events_festival"))
    private Festival festival;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "category", nullable = false, length = 20)
    private EventCategory category;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "published", nullable = false)
    private boolean published;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    @Builder
    public FestivalEvent(Festival festival, String title, String description, EventCategory category,
                          LocalDate eventDate, LocalTime startTime, LocalTime endTime, String location,
                          String imageUrl, boolean published, boolean completed) {
        this.festival = festival;
        this.title = title;
        this.description = description;
        this.category = category;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.imageUrl = imageUrl;
        this.published = published;
        this.completed = completed;
    }
}