package com.vinayakachaviti.contribution.entity;

import com.vinayakachaviti.common.enums.ContributionCategory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;

/**
 * US-CONTRIBUTIONS: A single in-kind or special contribution entry (e.g. a
 * donated Ganesh idol, puja oil, or an Annaprasada Vitharana sponsorship)
 * shown in a searchable table on the public Donations page, grouped by
 * category tab.
 */
@Entity
@Table(name = "contributions", indexes = {
    @Index(name = "idx_contributions_category", columnList = "category"),
    @Index(name = "idx_contributions_display_order", columnList = "display_order")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(nullable = false, length = 20)
    private ContributionCategory category;

    @Column(name = "event_label", length = 100)
    private String eventLabel;

    @Column(name = "item_name", nullable = false, length = 200)
    private String itemName;

    @Column(name = "donor_name", nullable = false, length = 150)
    private String donorName;

    @Column(length = 100)
    private String designation;

    @Column(length = 50)
    private String quantity;

    @Column(name = "display_order", nullable = false)
    @Builder.Default
    private Integer displayOrder = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
