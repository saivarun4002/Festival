package com.vinayakachaviti.audit.service;

import com.vinayakachaviti.audit.entity.AuditLog;
import com.vinayakachaviti.audit.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository repository;

    @Override
    @Async
    public void record(String username, String method, String path, int statusCode, String ipAddress, long durationMs) {
        AuditLog log = AuditLog.builder()
                .username(username)
                .method(method)
                .path(path)
                .statusCode(statusCode)
                .ipAddress(ipAddress)
                .durationMs(durationMs)
                .build();
        repository.save(log);
    }
}