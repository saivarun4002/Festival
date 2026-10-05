package com.vinayakachaviti.audit;

import com.vinayakachaviti.audit.service.AuditLogService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * US-AUDIT: Logs every mutating (non-GET) /api/** request made by an
 * authenticated user, capturing who/what/when/outcome for traceability and
 * production observability. Read-only GET requests are skipped to keep the
 * audit table focused on state-changing admin actions.
 */
@Component
@RequiredArgsConstructor
public class AuditLoggingFilter extends OncePerRequestFilter {

    private final AuditLogService auditLogService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        long start = System.currentTimeMillis();
        String method = request.getMethod();
        String path = request.getRequestURI();

        filterChain.doFilter(request, response);

        boolean isMutating = !"GET".equalsIgnoreCase(method) && !"OPTIONS".equalsIgnoreCase(method);
        boolean isApiPath = path != null && path.startsWith("/api/");
        if (isMutating && isApiPath) {
            long durationMs = System.currentTimeMillis() - start;
            String username = resolveUsername();
            String ip = request.getRemoteAddr();
            auditLogService.record(username, method, path, response.getStatus(), ip, durationMs);
        }
    }

    private String resolveUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return auth.getName();
        }
        return "anonymous";
    }
}
