package t1.ismailov.springbootstarter.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import t1.ismailov.springbootstarter.entity.HttpLogEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class HttpLoggingInterceptorTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpLoggingProperties loggingProperties;

    @InjectMocks
    private HttpLoggingInterceptor interceptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPreHandle() {
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("/test");

        boolean result = interceptor.preHandle(request, response, new Object());

        assertTrue(result);
        verify(request).setAttribute(eq("startTime"), anyLong());
        verify(request).getMethod();
        verify(request).getRequestURI();
    }

    @Test
    void testAfterCompletionWithSuccess() {
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("/test");
        when(request.getAttribute("startTime")).thenReturn(System.currentTimeMillis() - 100);
        when(response.getStatus()).thenReturn(200);
        when(loggingProperties.getFormat()).thenReturn("JSON");

        when(request.getHeaderNames()).thenReturn(Collections.enumeration(Collections.emptyList()));
        when(response.getHeaderNames()).thenReturn(Collections.emptyList());

        interceptor.afterCompletion(request, response, new Object(), null);

        verify(response).getStatus();
        verify(loggingProperties).getFormat();
    }

    @Test
    void testAfterCompletionWithException() {
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("/test");
        when(request.getAttribute("startTime")).thenReturn(System.currentTimeMillis() - 100);
        when(response.getStatus()).thenReturn(500);

        Exception ex = new RuntimeException("Test exception");

        interceptor.afterCompletion(request, response, new Object(), ex);

        verify(response).getStatus();
    }

    @Test
    void testGetExecutionTimeWithValidStartTime() {
        when(request.getAttribute("startTime")).thenReturn(System.currentTimeMillis() - 100);

        long executionTime = HttpLoggingInterceptor.getExecutionTime(request);

        assertEquals(100, executionTime, 10); // We allow a small inaccuracy
    }

    @Test
    void testGetExecutionTime_withInvalidStartTime() {
        when(request.getAttribute("startTime")).thenReturn(null);

        long executionTime = HttpLoggingInterceptor.getExecutionTime(request);

        assertEquals(-1, executionTime);
    }

    @Test
    void testLogging_withJsonFormat() throws JsonProcessingException {
        HttpLogEntity logEntity = new HttpLogEntity()
                .setMethod("GET")
                .setUri("/test");

        when(loggingProperties.getFormat()).thenReturn("JSON");

        ObjectMapper objectMapper = spy(new ObjectMapper());
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(loggingProperties);
        interceptor = Mockito.spy(interceptor);
        interceptor.objectMapper = objectMapper;

        interceptor.logging(logEntity);

        verify(objectMapper).writeValueAsString(logEntity);
    }

    @Test
    void testLogging_withTextFormat() {
        HttpLogEntity logEntity = new HttpLogEntity()
                .setMethod("GET")
                .setUri("/test");

        when(loggingProperties.getFormat()).thenReturn("TEXT");

        interceptor.logging(logEntity);

        verifyNoInteractions(request);  // No JSON serialization, just calling toString()
    }
}
