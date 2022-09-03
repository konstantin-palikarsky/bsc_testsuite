package testsuite.workflows;


import testsuite.apis.ThesisApi;
import testsuite.workflows.requests.GetStoryRequest;
import testsuite.workflows.requests.SearchLabelsRequest;
import testsuite.workflows.requests.SearchStoriesRequest;

public class ReadingWorkflow implements Workflow {

    SearchLabelsRequest searchLabels;
    SearchStoriesRequest searchStories;
    GetStoryRequest getStory;

    public ReadingWorkflow(ThesisApi api) {
        this.searchLabels = new SearchLabelsRequest(api);
        this.searchStories = new SearchStoriesRequest(api);
        this.getStory = new GetStoryRequest(api);
    }

    public void execute() throws Exception {
        var labelsResponse = searchLabels.requestByLabel(null);
        var storiesResponse = searchStories.requestByTitleAndLabel(null, null);
        var storyResponse = getStory.requestById(1);

        System.out.println(storyResponse + " " + labelsResponse.body() + " " + storiesResponse.body().substring(0,1024));
    }
}
