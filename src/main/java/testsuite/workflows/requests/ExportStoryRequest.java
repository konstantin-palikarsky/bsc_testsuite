package testsuite.workflows.requests;


import testsuite.apis.ThesisApi;
import testsuite.repositories.entities.RequestStatistics;
import testsuite.repositories.entities.RequestType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

public class ExportStoryRequest {
    ThesisApi api;

    public ExportStoryRequest(ThesisApi api) {
        this.api = api;
    }

    public HttpResponse<byte[]> requestById(long id) throws Exception {
        HttpRequest request;

        var start = System.currentTimeMillis();
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(api.exportStoryUrl(id)))
                    .GET()
                    .header("authorization", "")
                    .build();

        } catch (URISyntaxException e) {
            System.err.println("Error with url syntax");
            throw new URISyntaxException(e.getInput(), e.getReason());
        }

        var client = HttpClient.newHttpClient();
        HttpResponse<byte[]> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error with request sending");
            throw new Exception(e.getMessage());
        }

        var end = Long.toString(System.currentTimeMillis() - start);

        api.saveStats(new RequestStatistics(LocalDateTime.now(), RequestType.EXPORT_STORY, end));

        return response;
    }


}
