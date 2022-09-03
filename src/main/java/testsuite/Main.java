package testsuite;

import testsuite.apis.MonolithApi;
import testsuite.apis.ThesisApi;
import testsuite.dtos.TokenDto;
import testsuite.workflows.AuthWorkflow;
import testsuite.workflows.ReadingWorkflow;
import testsuite.workflows.WritingWorkflow;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        ThesisApi api = new MonolithApi();

        try {
            AuthWorkflow authenticate = new AuthWorkflow(api);
            var auth = authenticate.execute();

            var authTime = LocalDateTime.now();
            ReadingWorkflow read = new ReadingWorkflow(api);
            WritingWorkflow write = new WritingWorkflow(api, auth);

            while (LocalDateTime.now().isBefore(authTime.plusSeconds(5))) {
                read.execute();
                write.execute();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }


}

