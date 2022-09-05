package testsuite.workflows;

import org.json.JSONObject;
import testsuite.apis.ThesisApi;
import testsuite.dtos.TokenDto;
import testsuite.dtos.UserDto;
import testsuite.workflows.requests.CreateUserRequest;
import testsuite.workflows.requests.LoginRequest;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

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
        StringBuilder rand= new StringBuilder("");

        for (int i = 0; i < 10; i++) {
            rand.append((char) (new Random().nextInt(26) + 'a'));

        }
        return rand.toString();
    }
}
