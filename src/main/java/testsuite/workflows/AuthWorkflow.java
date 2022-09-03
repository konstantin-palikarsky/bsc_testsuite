package testsuite.workflows;

import org.json.JSONObject;
import testsuite.apis.ThesisApi;
import testsuite.dtos.TokenDto;
import testsuite.dtos.UserDto;
import testsuite.workflows.requests.CreateUserRequest;
import testsuite.workflows.requests.LoginRequest;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class AuthWorkflow implements Workflow {
    private final CreateUserRequest createUser;
    private final LoginRequest login;

    public AuthWorkflow(ThesisApi api) {
        this.createUser = new CreateUserRequest(api);
        this.login = new LoginRequest(api);
    }

    public void execute() throws Exception {
        var user = new UserDto(randomString(), randomString());

        var createUserResponse =
                createUser.create(user);
        var loginResponse = login.login(user);
        var jsonToken = new JSONObject(loginResponse.body());
        var token = new TokenDto();
        token.setToken(jsonToken.get("token").toString());

        System.out.println(token.getToken());
    }

    private String randomString() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
}
