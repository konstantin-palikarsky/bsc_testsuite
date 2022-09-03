package testsuite.workflows;

import testsuite.apis.ThesisApi;
import testsuite.dtos.UserDto;
import testsuite.workflows.requests.CreateUserRequest;
import testsuite.workflows.requests.LoginRequest;

import java.nio.charset.Charset;
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

        System.out.println(loginResponse.body());
    }

    private String randomString() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
}
