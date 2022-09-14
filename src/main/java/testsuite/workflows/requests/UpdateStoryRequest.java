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

public class UpdateStoryRequest {
    ThesisApi api;
    TokenDto authToken;

    public UpdateStoryRequest(ThesisApi api, TokenDto authToken) {
        this.api = api;
        this.authToken = authToken;
    }

    public HttpResponse<String> updateById(StoryDto story, long id) throws Exception {
        HttpRequest request;
        String body = storyToString(story);

        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(api.updateStoryUrl(id)))
                    .PUT(HttpRequest.BodyPublishers.ofString(body))
                    .header("authorization", authToken.token())
                    .header("content-type","application/json")
                    .build();

        } catch (URISyntaxException e) {
            System.err.println("Error with url syntax");
            throw new URISyntaxException(e.getInput(), e.getReason());
        }

        var start = System.currentTimeMillis();

        var response = getStringHttpResponse(request);

        var end = Long.toString(System.currentTimeMillis() - start);

        api.saveStats(new RequestStatistics(LocalDateTime.now(), RequestType.UPDATE_STORY, end));

        return response;
    }

    private String storyToString(StoryDto label) {
        return new JSONObject(label).toString();
    }
}
