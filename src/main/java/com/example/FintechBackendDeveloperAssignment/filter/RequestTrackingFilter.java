package com.example.FintechBackendDeveloperAssignment.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.UUID;

@Component
public class RequestTrackingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RequestTrackingFilter.class);
    private static final String TRACE_ID_HEADER = "X-Trace-Id";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("RequestTrackingFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String traceId = request.getHeader(TRACE_ID_HEADER);

        if (traceId == null || traceId.isEmpty()) {
            traceId = UUID.randomUUID().toString();
            MDC.put("traceId", traceId);
        }

        try {
            logger.info("Incoming request: {} {}", request.getMethod(), request.getRequestURI());
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.addHeader(TRACE_ID_HEADER, traceId);
            filterChain.doFilter(servletRequest, servletResponse);
            logger.info("Outgoing response: {} {}", response.getStatus(), request.getRequestURI());
        } finally {
            MDC.remove("traceId");
        }
    }

    @Override
    public void destroy() {
        logger.info("RequestTrackingFilter destroyed");
    }
}