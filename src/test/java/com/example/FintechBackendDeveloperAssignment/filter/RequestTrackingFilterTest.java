package com.example.FintechBackendDeveloperAssignment.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RequestTrackingFilterTest {

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private FilterChain mockFilterChain;

    @Test
    public void testDoFilter_NoTraceIdHeader() throws Exception {
        // Initialize mocks
        MockitoAnnotations.initMocks(this);

        // Create filter instance
        RequestTrackingFilter filter = new RequestTrackingFilter();

        // Mock request without traceId header
        when(mockRequest.getHeader("X-Trace-Id")).thenReturn(null);

        // Invoke filter's doFilter method
        filter.doFilter(mockRequest, mockResponse, mockFilterChain);

        // Verify behavior
        verify(mockResponse).addHeader(eq("X-Trace-Id"), anyString()); // Use eq() for direct values
        verify(mockFilterChain).doFilter(any(ServletRequest.class), any(ServletResponse.class)); // Use any() for direct values
        verify(mockResponse).getStatus();
    }


    @Test
    public void testDoFilter_WithTraceIdHeader() throws Exception {
        // Initialize mocks
        MockitoAnnotations.initMocks(this);

        // Create filter instance
        RequestTrackingFilter filter = new RequestTrackingFilter();

        // Mock request with traceId header
        String traceId = UUID.randomUUID().toString();
        when(mockRequest.getHeader("X-Trace-Id")).thenReturn(traceId);

        // Invoke filter's doFilter method
        filter.doFilter(mockRequest, mockResponse, mockFilterChain);

        // Verify behavior
        verify(mockResponse).addHeader("X-Trace-Id", traceId);
        verify(mockFilterChain).doFilter(mockRequest, mockResponse);
        verify(mockResponse).getStatus();
    }
}
