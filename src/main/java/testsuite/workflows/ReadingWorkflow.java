package testsuite.workflows;


import testsuite.apis.ThesisApi;
import testsuite.workflows.requests.ExportStoryRequest;
import testsuite.workflows.requests.SearchLabelsRequest;
import testsuite.workflows.requests.SearchStoriesRequest;

public class ReadingWorkflow {

    SearchLabelsRequest searchLabels;
    SearchStoriesRequest searchStories;
    ExportStoryRequest getStory;

    public ReadingWorkflow(ThesisApi api) {
        this.searchLabels = new SearchLabelsRequest(api);
        this.searchStories = new SearchStoriesRequest(api);
        this.getStory = new ExportStoryRequest(api);
    }

    public void execute() throws Exception {
        var labelsResponse = searchLabels.requestByLabel(null);
        var storiesResponse = searchStories.requestByTitleAndLabel(null, null);
        var storyResponse = getStory.requestById(1);
    }
}
