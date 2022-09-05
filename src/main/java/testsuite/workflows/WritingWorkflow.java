package testsuite.workflows;

import org.json.JSONObject;
import testsuite.apis.ThesisApi;
import testsuite.dtos.LabelDto;
import testsuite.dtos.StoryDto;
import testsuite.dtos.TokenDto;
import testsuite.workflows.requests.CreateStoryRequest;
import testsuite.workflows.requests.DeleteStoryRequest;
import testsuite.workflows.requests.UpdateStoryRequest;

import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class WritingWorkflow {
    private final CreateStoryRequest createStory;
    private final UpdateStoryRequest updateStory;
    private final DeleteStoryRequest deleteStory;

    public WritingWorkflow(ThesisApi api, TokenDto auth) {
        this.createStory = new CreateStoryRequest(api, auth);
        this.updateStory = new UpdateStoryRequest(api, auth);
        this.deleteStory = new DeleteStoryRequest(api, auth);
    }

    public void execute() throws Exception {

        var storyResponse = createStory.create(getStory());
        long createdStoryId = getStoryIdFromResponse(storyResponse);

        updateStory.updateById(getStoryForUpdate(), createdStoryId);
        deleteStory.deleteById(createdStoryId);
    }

    private long getStoryIdFromResponse(HttpResponse<String> storyResponse) {
        var responseString = storyResponse.body();
        var jsonResponseData = new JSONObject(responseString).get("data");

        var jsonStory = new JSONObject(jsonResponseData.toString());

        return Long.parseLong(jsonStory.get("id").toString());
    }

    private LabelDto getLabel() {
        return new LabelDto(randomString(), 1L);
    }

    private LabelDto getLabel2() {
        return new LabelDto(randomString(), 2L);
    }

    private StoryDto getStoryForUpdate() {
        var labels = new LabelDto[2];
        labels[0] = getLabel();
        labels[1] = getLabel2();

        return new StoryDto("Successfully updated title!",
                "Successfully updated author name!",
                "Successfully updated contents!",
                labels);
    }

    private StoryDto getStory() {
        return new StoryDto(randomString(), randomString(), randomString(), null);
    }

    private String randomString() {
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    private String randomLargeContentString() {
        byte[] array = new byte[4096];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
}
