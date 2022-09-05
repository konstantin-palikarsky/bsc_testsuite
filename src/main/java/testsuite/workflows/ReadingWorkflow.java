package testsuite.workflows;


import org.json.JSONArray;
import org.json.JSONObject;
import testsuite.apis.ThesisApi;
import testsuite.workflows.requests.ExportStoryRequest;
import testsuite.workflows.requests.SearchLabelsRequest;
import testsuite.workflows.requests.SearchStoriesRequest;

import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

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

        searchStories.requestByTitleAndLabel(null, getLabelFromResponse(labelsResponse));

        var storiesResponse =
                searchStories.requestByTitleAndLabel(null, null);

        getStory.requestById(getIdFromResponse(storiesResponse));
    }

    private String getLabelFromResponse(HttpResponse<String> labelsResponse) {
        var responseString = labelsResponse.body();
        var jsonResponseData = new JSONObject(responseString).get("data");

        var jsonLabelsArray = new JSONArray(jsonResponseData.toString());

        var temp = new JSONObject(jsonLabelsArray.get(3).toString()).get("label").toString();
        return URLEncoder.encode(temp, StandardCharsets.US_ASCII);
    }


    private long getIdFromResponse(HttpResponse<String> storyResponse) {
        var responseString = storyResponse.body();
        var jsonResponseData = new JSONObject(responseString).get("data");

        var jsonLabelsArray = new JSONArray(jsonResponseData.toString());

        return Long.parseLong(new JSONObject(jsonLabelsArray.get(2).toString()).get("id").toString());
    }
}
