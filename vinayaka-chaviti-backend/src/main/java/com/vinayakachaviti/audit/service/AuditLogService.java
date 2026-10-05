package com.vinayakachaviti.audit.service;

public interface AuditLogService {
    void record(String username, String method, String path, int statusCode, String ipAddress, long durationMs);
}