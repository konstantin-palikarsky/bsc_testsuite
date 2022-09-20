package testsuite.repositories;

import testsuite.apis.ApiType;
import testsuite.repositories.entities.RequestStatistics;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RequestStatisticsRepository {
    private final ConcurrentLinkedQueue<RequestStatistics> requestStatisticsList = new ConcurrentLinkedQueue<>();

    public synchronized void save(RequestStatistics req) {
        requestStatisticsList.add(req);
    }

    public void getRequestStatisticsList(ApiType apiType, int capacity, int token_gen_rate, int workflow_run_rate,
                                         int max_user_runtime, boolean gradual) throws IOException {

        String fileName = "./stats_" + apiType + "_" + capacity + "_" + token_gen_rate + "_" +
                workflow_run_rate + "_" + max_user_runtime +
                (gradual ? "_gradual" : "_instant") + "_" + LocalDateTime.now() + ".csv";


        FileWriter writer = new FileWriter(fileName);

        var arrayListStats = requestStatisticsList.stream().toList();

        for (RequestStatistics stat : arrayListStats) {
            writer.write(stat.toString());
        }
        System.err.println("Stats log created");
        writer.close();
    }
}