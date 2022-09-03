package testsuite.workflows.requests;

import org.json.JSONObject;
import testsuite.apis.ThesisApi;
import testsuite.dtos.UserDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static testsuite.workflows.requests.Commons.getStringHttpResponse;

public class LoginRequest {
    ThesisApi api;

    public LoginRequest(ThesisApi api) {
        this.api = api;
    }

    public HttpResponse<String> login(UserDto user) throws Exception {
        HttpRequest request;
        String body = userToString(user);

        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(api.loginUrl()))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("authorization", "")
                    .build();

        } catch (URISyntaxException e) {
            System.err.println("Error with url syntax");
            throw new URISyntaxException(e.getInput(), e.getReason());
        }

        return getStringHttpResponse(request);
    }

    private String userToString(UserDto user) {
        return new JSONObject(user).toString();
    }
}
