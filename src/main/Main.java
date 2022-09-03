package main;

import main.apis.MonolithApi;
import main.apis.ThesisApi;
import main.workflows.ReadingWorkflow;
import main.workflows.Workflow;
import main.workflows.requests.SearchLabelsRequest;

import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) {
        ThesisApi api = new MonolithApi();

        Workflow workflow = new ReadingWorkflow(api);

        try {
            workflow.execute();
        } catch (Exception e) {
            System.err.println("Error in reading workflow");
        }

    }


}

