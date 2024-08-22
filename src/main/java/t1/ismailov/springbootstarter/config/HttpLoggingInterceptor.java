package t1.ismailov.springbootstarter.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import t1.ismailov.springbootstarter.entity.HttpLogEntity;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@code HttpLoggingInterceptor} is a Spring MVC interceptor that logs HTTP requests and responses.
 * It captures details such as the request method, URI, headers, status code, and execution time.
 * This information can be logged in different formats (e.g., JSON) based on the configuration.
 */
@Slf4j
@RequiredArgsConstructor
public class HttpLoggingInterceptor implements HandlerInterceptor {

    private final HttpLoggingProperties loggingProperties;

    public static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * This method that logs the start time of the request processing and attaches it
     * to the request as an attribute.
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        log.debug("Request received: method={}, URI={}, startTime={}",
                request.getMethod(), request.getRequestURI(), startTime);
        return true;
    }

    /**
     * Logs the HTTP request and response details after the request has been processed.
     * If an exception occurred during processing, it logs the error.
     * This method captures the method, URI, headers, status code, and execution time
     * of the request and response.
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

        HttpLogEntity logData = new HttpLogEntity()
                .setExecutionTime(getExecutionTime(request))
                .setMethod(request.getMethod())
                .setUri(request.getRequestURI())
                .setRequestHeaders(getHeaders(
                        request.getHeaderNames() != null
                                ? Collections.list(request.getHeaderNames()).stream()
                                : Stream.empty(),
                        request::getHeader
                ))
                .setStatusCode(response.getStatus())
                .setResponseHeaders(getHeaders(
                        response.getHeaderNames().stream(), response::getHeader
                ));

        log.debug("Request processed: method={}, URI={}, status={}, executionTime={} ms",
                logData.getMethod(), logData.getUri(), logData.getStatusCode(), logData.getExecutionTime());

        if (ex != null) {
            log.error("Exception occurred during request processing: {}", ex.getMessage(), ex);
            return;
        } else {
            logging(logData);
        }
    }

    /**
     * Calculates the time taken to process the request by comparing the current time with the start time
     * stored in the request attributes.
     *
     * @param request the HTTP request
     * @return the execution time in milliseconds, or {@code -1} if the start time attribute is missing
     */
    protected static long getExecutionTime(HttpServletRequest request) {
        Object startTimeAttr = request.getAttribute("startTime");
        if (startTimeAttr instanceof Long) {
            return System.currentTimeMillis() - (long) startTimeAttr;
        }
        log.warn("Start time attribute is missing or incorrect, unable to calculate execution time.");
        return -1;
    }

    protected static Map<String, String> getHeaders(Stream<String> headerNames,
                                                  Function<String, String> headerValueFunction) {
        Map<String, String> headers = headerNames.collect(Collectors.toMap(Function.identity(), headerValueFunction));

        log.debug("Headers collected: {}", headers);
        return headers;
    }

    protected void logging(HttpLogEntity logData) {
        try {
            String data = "JSON".equalsIgnoreCase(loggingProperties.getFormat())
                    ? objectMapper.writeValueAsString(logData)
                    : logData.toString();

            log.info("Logging data: {}", data);
        } catch (JsonProcessingException ex) {
            log.error("Error occurred during logging: {}", ex.getMessage(), ex);
        }
    }

}
