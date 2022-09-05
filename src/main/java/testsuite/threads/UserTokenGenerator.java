package testsuite.threads;

import testsuite.repository.TokenQueue;

public class UserTokenGenerator extends Thread {
    private final TokenQueue tokens;

    public UserTokenGenerator(TokenQueue tokens) {
        this.tokens = tokens;
    }

    public void run() {

        tokens.add();

    }
}
