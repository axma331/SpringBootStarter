package t1.ismailov.springbootstarter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * {@code HttpLogEntity} is a data structure that represents the details of an HTTP request and response.
 * It is used for logging purposes, capturing essential information such as request method, URI,
 * headers, status code, and execution time.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class HttpLogEntity {

    /** The time taken to execute the HTTP request, in milliseconds. */
    private long executionTime;
    /** The HTTP method used for the request (e.g., GET, POST). */
    private String method;
    /** The URI of the HTTP request. */
    private String uri;
    /** The headers sent with the HTTP request. */
    private Map<String, String> requestHeaders;
    /** The headers returned in the HTTP response. */
    private Map<String, String> responseHeaders;
    /** The HTTP status code of the response. */
    private int statusCode;

    @Override
    public String toString() {
        return "HttpLogEntity{" +
                "executionTime=" + executionTime +
                ", method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                ", requestHeaders=" + requestHeaders +
                ", responseHeaders=" + responseHeaders +
                ", statusCode=" + statusCode +
                '}';
    }

}
