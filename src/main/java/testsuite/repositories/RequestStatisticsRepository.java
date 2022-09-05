package testsuite.repositories;

import testsuite.repositories.entities.RequestStatistics;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RequestStatisticsRepository {
    private final ConcurrentLinkedQueue<RequestStatistics> requestStatisticsList = new ConcurrentLinkedQueue<>();

    public synchronized void save(RequestStatistics req) {
        requestStatisticsList.add(req);
    }

    public void getRequestStatisticsList() throws IOException {
        FileWriter writer = new FileWriter("./stats.csv");

        var arrayListStats = requestStatisticsList.stream().toList();
        writer.write("Timestamp HH:MM:SS,Request Type,Execution time (in ms)\n");

        for (RequestStatistics stat : arrayListStats) {
            writer.write(stat.toString());
        }
        writer.close();
    }
}