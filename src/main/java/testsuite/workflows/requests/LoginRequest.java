package testsuite.workflows.requests;

import org.json.JSONObject;
import testsuite.apis.ThesisApi;
import testsuite.dtos.UserDto;
import testsuite.repositories.entities.RequestStatistics;
import testsuite.repositories.entities.RequestType;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static testsuite.workflows.requests.Commons.getStringHttpResponse;

public class LoginRequest {
    ThesisApi api;

    public LoginRequest(ThesisApi api) {
        this.api = api;
    }

    public HttpResponse<String> login(UserDto user) throws Exception {
        HttpRequest request;
        String body = userToString(user);

        var start = System.currentTimeMillis();
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(api.loginUrl()))
                    .header("content-type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("authorization", "")
                    .build();

        } catch (URISyntaxException e) {
            System.err.println("Error with url syntax");
            throw new URISyntaxException(e.getInput(), e.getReason());
        }

        var response = getStringHttpResponse(request);
        var end = Long.toString(System.currentTimeMillis() - start);

        api.saveStats(new RequestStatistics(LocalDateTime.now(), RequestType.LOGIN, end));

        return response;
    }

    private String userToString(UserDto user) {
        return new JSONObject(user).toString();
    }
}
