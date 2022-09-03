package testsuite;

import testsuite.apis.MonolithApi;
import testsuite.apis.ThesisApi;
import testsuite.dtos.TokenDto;
import testsuite.workflows.AuthWorkflow;
import testsuite.workflows.ReadingWorkflow;
import testsuite.workflows.WritingWorkflow;

public class Main {
    public static void main(String[] args) {
        ThesisApi api = new MonolithApi();

        /*TokenDto auth = new TokenDto();
        auth.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6Ilx0zac477-977-977-9IiwiZXhwIjoxNjYyMjE0ODQzfQ.5kMkGSI2rbajLDUqG1tHVH-H9MY8kUjaxb29b4boGro");
        */


        try {
            AuthWorkflow authenticate = new AuthWorkflow(api);
            var auth = authenticate.execute();

            ReadingWorkflow read = new ReadingWorkflow(api);
            WritingWorkflow write = new WritingWorkflow(api, auth);

            for (int i = 0; i < 10; i++) {

                read.execute();
                write.execute();

            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }


}

