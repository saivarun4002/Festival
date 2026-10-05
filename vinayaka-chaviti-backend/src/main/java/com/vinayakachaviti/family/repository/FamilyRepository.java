package com.vinayakachaviti.family.repository;

import com.vinayakachaviti.family.entity.Family;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<Family, Long> {
    Page<Family> findByPublishedTrue(Pageable pageable);
    long countByPublishedTrue();
}
