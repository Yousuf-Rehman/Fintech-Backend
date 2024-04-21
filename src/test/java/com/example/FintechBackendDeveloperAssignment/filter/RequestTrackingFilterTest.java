package com.example.FintechBackendDeveloperAssignment.filter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.MDC;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;


import java.io.IOException;

public class RequestTrackingFilterTest {

    @Mock
    private MDC mdc;

    private RequestTrackingFilter filter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        filter = new RequestTrackingFilter();
    }

    @Test
    public void testDoFilter() throws IOException, ServletException {
        // Create mock objects
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        // Set up expectations
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            String value = invocation.getArgument(1);
            // Verify that the key is "traceId" and value is not null
            assert key.equals("traceId");
            assert value != null;
            return null;
        }).when(mdc).put(any(), any());

        // Call the filter
        filter.doFilter(request, response, filterChain);

        // Verify that MDC.put() is called with the correct key-value pair
        verify(mdc).put(eq("traceId"), any(String.class));

        // Verify that the filter chain is called
        verify(filterChain).doFilter(request, response);
    }
}
