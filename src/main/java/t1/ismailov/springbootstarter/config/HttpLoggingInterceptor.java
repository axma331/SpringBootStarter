package t1.ismailov.springbootstarter.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import t1.ismailov.springbootstarter.entity.HttpLogEntity;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpLoggingInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(HttpLoggingInterceptor.class);
    private final static HttpLoggingProperties httpLoggingProperties = new HttpLoggingProperties();
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        request.setAttribute("startTime", System.nanoTime());
        return true;
    }

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
                        Collections.list(request.getHeaderNames()).stream(), request::getHeader
                ))
                .setStatusCode(response.getStatus())
                .setResponseHeaders(getHeaders(
                        response.getHeaderNames().stream(), response::getHeader
                ));

        if (ex != null) {
            log.error("Exception occurred during request processing: {}", ex.getMessage(), ex);
        } else {
            logging(logData);
        }
    }


    private static long getExecutionTime(HttpServletRequest request) {
        Object startTimeAttr = request.getAttribute("startTime");
        if (startTimeAttr instanceof Long) {
            return (System.nanoTime() - (long) startTimeAttr) / 1_000_000;
        }
        log.warn("Start time attribute is missing or incorrect, unable to calculate execution time.");
        return -1;
    }

    private static Map<String, String> getHeaders(Stream<String> headerNames,
                                                  Function<String, String> headerValueFunction) {
        return headerNames.collect(Collectors.toMap(Function.identity(), headerValueFunction));
    }

    private void logging(HttpLogEntity logData) {
        try {
            String message = "JSON".equalsIgnoreCase(httpLoggingProperties.getFormat())
                    ? objectMapper.writeValueAsString(logData)
                    : logData.toString();
            log.info(message);
        } catch (JsonProcessingException ex) {
            log.error("Error occurred during logging: {}", ex.getMessage(), ex);
        }
    }

}
