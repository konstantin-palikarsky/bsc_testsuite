package testsuite.workflows;

import org.json.JSONObject;
import testsuite.apis.ThesisApi;
import testsuite.dtos.LabelDto;
import testsuite.dtos.StoryDto;
import testsuite.dtos.TokenDto;
import testsuite.workflows.requests.CreateLabelRequest;
import testsuite.workflows.requests.CreateStoryRequest;
import testsuite.workflows.requests.DeleteStoryRequest;
import testsuite.workflows.requests.UpdateStoryRequest;

import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class WritingWorkflow {
    private final CreateStoryRequest createStory;
    //private final CreateLabelRequest createLabel;
    private final UpdateStoryRequest updateStory;
    private final DeleteStoryRequest deleteStory;

    public WritingWorkflow(ThesisApi api, TokenDto auth) {
        this.createStory = new CreateStoryRequest(api, auth);
        this.updateStory = new UpdateStoryRequest(api, auth);
        //this.createLabel = new CreateLabelRequest(api, auth);
        this.deleteStory = new DeleteStoryRequest(api, auth);
    }

    public void execute() throws Exception {

        var storyResponse = createStory.create(getStory());

        long createdStoryId = getStoryIdFromResponse(storyResponse);

        var updateResponse = updateStory.updateById(getStoryForUpdate(), createdStoryId);
        var deleteResponse = deleteStory.deleteById(createdStoryId);
    }

    private long getStoryIdFromResponse(HttpResponse<String> storyResponse) {
        var responseString = storyResponse.body();
        var jsonResponseData = new JSONObject(responseString).get("data");

        var jsonStory = new JSONObject(jsonResponseData.toString());

        return Long.parseLong(jsonStory.get("id").toString());
    }

    private LabelDto getLabel() {

        var label = new LabelDto();
        label.setId(1L);
        label.setLabel(randomString());
        return label;
    }
    private LabelDto getLabel2() {

        var label = new LabelDto();
        label.setId(2L);
        label.setLabel(randomString());
        return label;
    }

    private StoryDto getStoryForUpdate() {
        var story = new StoryDto();
        story.setContents("Successfully updated");
        story.setAuthorName(randomString());
        story.setTitle(randomString());

        var labels = new LabelDto[2];
        labels[0] = getLabel();
        labels[1] = getLabel2();

        story.setLabels(labels);
        return story;
    }

    private StoryDto getStory() {
        var story = new StoryDto();
        story.setContents(randomString());
        story.setAuthorName(randomString());
        story.setTitle(randomString());

        story.setLabels(null);
        return story;
    }

    private String randomString() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
}
