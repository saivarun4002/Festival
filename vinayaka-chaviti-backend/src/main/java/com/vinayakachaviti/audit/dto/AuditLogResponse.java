package com.vinayakachaviti.audit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponse {
    private Long id;
    private String username;
    private String method;
    private String path;
    private int statusCode;
    private String ipAddress;
    private Long durationMs;
    private OffsetDateTime createdAt;
}
