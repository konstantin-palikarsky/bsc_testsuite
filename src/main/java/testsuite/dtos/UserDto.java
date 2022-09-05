package testsuite.dtos;

public record UserDto(String username, String password) {

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
