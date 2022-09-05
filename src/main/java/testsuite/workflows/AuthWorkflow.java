package testsuite.workflows;

import org.json.JSONObject;
import testsuite.apis.ThesisApi;
import testsuite.dtos.TokenDto;
import testsuite.dtos.UserDto;
import testsuite.workflows.requests.CreateUserRequest;
import testsuite.workflows.requests.LoginRequest;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class AuthWorkflow {
    private final CreateUserRequest createUser;
    private final LoginRequest login;

    public AuthWorkflow(ThesisApi api) {
        this.createUser = new CreateUserRequest(api);
        this.login = new LoginRequest(api);
    }

    public TokenDto execute() throws Exception {
        var user = new UserDto(randomString(), randomString());

        createUser.create(user);
        var loginResponse = login.login(user);
        var jsonToken = new JSONObject(loginResponse.body());

        return new TokenDto(jsonToken.get("token").toString());
    }

    private String randomString() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
}
