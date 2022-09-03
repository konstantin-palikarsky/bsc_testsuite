package main.workflows.requests;

import main.apis.ThesisApi;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static main.workflows.requests.Commons.getStringHttpResponse;

public class SearchLabelsRequest {
    ThesisApi api;

    public SearchLabelsRequest(ThesisApi api) {
        this.api = api;
    }

    public HttpResponse<String> requestByLabel(String label) throws Exception {
        HttpRequest request;
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

        return getStringHttpResponse(request);
    }
}
