package main.workflows.requests;

import main.apis.ThesisApi;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SearchStoriesRequest {

    public HttpResponse<String> send(ThesisApi api) throws Exception {
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(api.searchStoriesUrl(null, null)))
                    .GET()
                    .header("authorization", "")
                    .build();

        } catch (URISyntaxException e) {
            System.err.println("Error with url syntax");
            throw new URISyntaxException(e.getInput(), e.getReason());
        }

        var client = HttpClient.newHttpClient();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error with request sending");
            throw new Exception(e.getMessage());
        }

       return response;
    }
}
