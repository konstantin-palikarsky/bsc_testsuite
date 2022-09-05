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

public class CreateUserRequest {
    ThesisApi api;

    public CreateUserRequest(ThesisApi api) {
        this.api = api;
    }

    public HttpResponse<String> create(UserDto user) throws Exception {
        HttpRequest request;
        String body = userToString(user);

        var start = System.currentTimeMillis();
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(api.createUserUrl()))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("content-type","application/json")
                    .build();

        } catch (Exception e) {
            System.err.println("Error with url syntax:" + e.getMessage());
            throw new Exception(e.getMessage());
        }
        var response = getStringHttpResponse(request);

        var end = Long.toString(System.currentTimeMillis() - start);

        api.saveStats(new RequestStatistics(LocalDateTime.now(), RequestType.REGISTER, end));

        return response;
    }

    private String userToString(UserDto user) {
        return new JSONObject(user).toString();
    }

}
