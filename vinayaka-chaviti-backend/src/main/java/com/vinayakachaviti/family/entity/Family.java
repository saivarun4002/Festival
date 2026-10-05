package com.vinayakachaviti.family.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

/**
 * US-COMMUNITY, PRD-FAMILY: Participating families directory. Deliberately
 * minimal fields — only what's needed for the public Community directory;
 * no sensitive personal information is stored/exposed.
 */
@Entity
@Table(name = "families", indexes = {
    @Index(name = "idx_families_published", columnList = "published")
})
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "family_name", nullable = false, length = 150)
    private String familyName;

    @Column(name = "representative_name", length = 150)
    private String representativeName;

    @Column(name = "published", nullable = false)
    private boolean published;

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
    public Family(String familyName, String representativeName, boolean published) {
        this.familyName = familyName;
        this.representativeName = representativeName;
        this.published = published;
    }
}
