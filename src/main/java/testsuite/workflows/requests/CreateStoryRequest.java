package testsuite.workflows.requests;

import org.json.JSONObject;
import testsuite.apis.ThesisApi;
import testsuite.dtos.StoryDto;
import testsuite.dtos.TokenDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("authorization", authToken.getToken())
                    .build();

        } catch (URISyntaxException e) {
            System.err.println("Error with url syntax");
            throw new URISyntaxException(e.getInput(), e.getReason());
        }

        return getStringHttpResponse(request);
    }

    private String storyToString(StoryDto label) {
        return new JSONObject(label).toString();
    }

}
