package testsuite.repositories.entities;

import java.time.LocalDateTime;

public record RequestStatistics(LocalDateTime requestTimestamp, RequestType requestType, String responseTime) {

    @Override
    public String toString() {
        return requestTimestamp.getHour() + ":" + requestTimestamp.getMinute() + ":" + requestTimestamp.getSecond() + ","
                + requestType + "," + responseTime + "\n";
    }
}
