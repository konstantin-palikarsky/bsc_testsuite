package main.workflows.requests;

import main.apis.ThesisApi;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetStoryRequest {
    ThesisApi api;

    public GetStoryRequest(ThesisApi api) {
        this.api = api;
    }

    public HttpResponse<byte[]> requestById(long id) throws Exception {
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(api.pdfGetStoryUrl(id)))
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

        return response;
    }


}
