package testsuite.repositories.entities;

import java.time.LocalDateTime;

public class RequestStatistics {
    private final LocalDateTime requestTimestamp;
    private final RequestType requestType;
    private final String responseTime;


    public RequestStatistics(LocalDateTime requestTimestamp, RequestType requestType, String responseTime) {
        this.requestTimestamp = requestTimestamp;
        this.requestType = requestType;
        this.responseTime = responseTime;
    }

    @Override
    public String toString() {
        return requestTimestamp + "," + requestType + "," + responseTime + "\n";
    }

    public LocalDateTime getRequestTimestamp() {
        return requestTimestamp;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getResponseTime() {
        return responseTime;
    }
}
