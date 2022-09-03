package testsuite.workflows.requests;

import org.json.JSONObject;
import testsuite.apis.ThesisApi;
import testsuite.dtos.LabelDto;
import testsuite.dtos.TokenDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static testsuite.workflows.requests.Commons.getStringHttpResponse;

public class CreateLabelRequest {
    ThesisApi api;
    TokenDto authToken;

    public CreateLabelRequest(ThesisApi api, TokenDto authToken) {
        this.api = api;
        this.authToken = authToken;
    }

    public HttpResponse<String> create(LabelDto label) throws Exception {
        HttpRequest request;
        String body = LabelToString(label);

        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(api.createLabelUrl()))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("authorization", authToken.getToken())
                    .build();

        } catch (URISyntaxException e) {
            System.err.println("Error with url syntax");
            throw new URISyntaxException(e.getInput(), e.getReason());
        }

        return getStringHttpResponse(request);
    }

    private String LabelToString(LabelDto label) {
        return new JSONObject(label).toString();
    }
}
