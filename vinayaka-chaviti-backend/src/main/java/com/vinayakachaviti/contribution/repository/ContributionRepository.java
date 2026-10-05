package com.vinayakachaviti.contribution.repository;

import com.vinayakachaviti.common.enums.ContributionCategory;
import com.vinayakachaviti.contribution.entity.Contribution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributionRepository extends JpaRepository<Contribution, Long> {

    Page<Contribution> findByCategoryOrderByDisplayOrderAscIdAsc(ContributionCategory category, Pageable pageable);

    Page<Contribution> findByCategoryAndDonorNameContainingIgnoreCaseOrderByDisplayOrderAscIdAsc(
            ContributionCategory category, String donorName, Pageable pageable);

    long countByCategory(ContributionCategory category);
}
