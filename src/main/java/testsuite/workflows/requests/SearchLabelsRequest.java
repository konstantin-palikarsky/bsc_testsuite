package testsuite.workflows.requests;


import testsuite.apis.ThesisApi;
import testsuite.repositories.entities.RequestStatistics;
import testsuite.repositories.entities.RequestType;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static testsuite.workflows.requests.Commons.getStringHttpResponse;


public class SearchLabelsRequest {
    ThesisApi api;

    public SearchLabelsRequest(ThesisApi api) {
        this.api = api;
    }

    public HttpResponse<String> requestByLabel(String label) throws Exception {
        HttpRequest request;

        var start = System.currentTimeMillis();
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(api.searchLabelsUrl(label)))
                    .GET()
                    .header("authorization", "")
                    .build();

        } catch (URISyntaxException e) {
            System.err.println("Error with url syntax");
            throw new URISyntaxException(e.getInput(), e.getReason());
        }

        var response = getStringHttpResponse(request);

        var end = Long.toString(System.currentTimeMillis() - start);

        api.saveStats(new RequestStatistics(LocalDateTime.now(), RequestType.SEARCH_LABELS, end));

        return response;
    }
}
