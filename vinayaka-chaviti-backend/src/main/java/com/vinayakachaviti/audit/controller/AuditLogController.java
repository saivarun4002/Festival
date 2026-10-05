package com.vinayakachaviti.audit.controller;

import com.vinayakachaviti.audit.dto.AuditLogResponse;
import com.vinayakachaviti.audit.entity.AuditLog;
import com.vinayakachaviti.audit.repository.AuditLogRepository;
import com.vinayakachaviti.common.dto.ApiResponse;
import com.vinayakachaviti.common.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * US-AUDIT: Admin-only endpoint to review the audit trail of mutating
 * admin actions (who/what/when/outcome).
 */
@RestController
@RequestMapping("/api/admin/audit-logs")
@RequiredArgsConstructor
@Tag(name = "Audit Logs", description = "API for reviewing the admin action audit trail")
public class AuditLogController {

    private final AuditLogRepository repository;

    @GetMapping
    @Operation(summary = "List audit log entries, most recent first (admin only)")
    public ResponseEntity<ApiResponse<PageResponse<AuditLogResponse>>> getLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<AuditLogResponse> response = PageResponse.from(
                repository.findAllByOrderByCreatedAtDesc(pageable).map(this::toResponse)
        );
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    private AuditLogResponse toResponse(AuditLog log) {
        return new AuditLogResponse(
                log.getId(),
                log.getUsername(),
                log.getMethod(),
                log.getPath(),
                log.getStatusCode(),
                log.getIpAddress(),
                log.getDurationMs(),
                log.getCreatedAt()
        );
    }
}
