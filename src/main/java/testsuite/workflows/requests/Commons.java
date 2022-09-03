package testsuite.workflows.requests;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Commons {
    static HttpResponse<String> getStringHttpResponse(HttpRequest request) throws Exception {
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
