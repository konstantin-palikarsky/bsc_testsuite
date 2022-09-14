package testsuite.workflows.requests;

import org.json.JSONObject;
import testsuite.apis.ThesisApi;
import testsuite.dtos.StoryDto;
import testsuite.dtos.TokenDto;
import testsuite.repositories.entities.RequestStatistics;
import testsuite.repositories.entities.RequestType;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static testsuite.workflows.requests.Commons.getStringHttpResponse;

public class CreateStoryRequest {
    ThesisApi api;
    TokenDto authToken;

    public CreateStoryRequest(ThesisApi api, TokenDto authToken) {
        this.api = api;
        this.authToken = authToken;
    }

    public HttpResponse<String> create(StoryDto story) throws Exception {
        HttpRequest request;
        String body = storyToString(story);

        try {

            request = HttpRequest.newBuilder()
                    .uri(new URI(api.createStoryUrl()))
                    .header("content-type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("authorization", authToken.token())
                    .build();

        } catch (Exception e) {

             throw new Exception(e.getMessage());
        }
        var start = System.currentTimeMillis();

        var response = getStringHttpResponse(request);

        var end = Long.toString(System.currentTimeMillis() - start);

        api.saveStats(new RequestStatistics(LocalDateTime.now(), RequestType.CREATE_STORY, end));

        return response;
    }

    private String storyToString(StoryDto label) {

        return new JSONObject(label).toString();
    }

}
