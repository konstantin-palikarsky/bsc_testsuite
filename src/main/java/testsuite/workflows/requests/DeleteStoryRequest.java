package testsuite.workflows.requests;

import testsuite.apis.ThesisApi;
import testsuite.dtos.TokenDto;
import testsuite.repositories.entities.RequestStatistics;
import testsuite.repositories.entities.RequestType;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static testsuite.workflows.requests.Commons.getStringHttpResponse;

public class DeleteStoryRequest {
    ThesisApi api;
    TokenDto authToken;

    public DeleteStoryRequest(ThesisApi api, TokenDto authToken) {
        this.api = api;
        this.authToken = authToken;
    }

    public HttpResponse<String> deleteById(long id) throws Exception {
        HttpRequest request;

        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(api.deleteStoryUrl(id)))
                    .DELETE()
                    .header("content-type","application/json")
                    .header("authorization", authToken.token())
                    .build();

        } catch (URISyntaxException e) {
            System.err.println("Error with url syntax");
            throw new URISyntaxException(e.getInput(), e.getReason());
        }
        var start = System.currentTimeMillis();

        var response = getStringHttpResponse(request);

        var end = Long.toString(System.currentTimeMillis() - start);

        api.saveStats(new RequestStatistics(LocalDateTime.now(), RequestType.DELETE_STORY, end));

        return response;
    }

}
