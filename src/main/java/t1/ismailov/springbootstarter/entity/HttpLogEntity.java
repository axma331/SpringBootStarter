package t1.ismailov.springbootstarter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class HttpLogEntity {

    private long executionTime;
    private String method;
    private String uri;
    private Map<String, String> requestHeaders;
    private Map<String, String> responseHeaders;
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
