package testsuite.workflows;

import testsuite.apis.ThesisApi;
import testsuite.dtos.LabelDto;
import testsuite.dtos.StoryDto;
import testsuite.dtos.TokenDto;
import testsuite.workflows.requests.CreateLabelRequest;
import testsuite.workflows.requests.CreateStoryRequest;
import testsuite.workflows.requests.DeleteStoryRequest;
import testsuite.workflows.requests.UpdateStoryRequest;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class WritingWorkflow implements Workflow {
    private final CreateStoryRequest createStory;
    private final CreateLabelRequest createLabel;
    private final UpdateStoryRequest updateStory;
    private final DeleteStoryRequest deleteStory;

    public WritingWorkflow(ThesisApi api, TokenDto auth) {
        this.createStory = new CreateStoryRequest(api, auth);
        this.updateStory = new UpdateStoryRequest(api, auth);
        this.createLabel = new CreateLabelRequest(api, auth);
        this.deleteStory = new DeleteStoryRequest(api, auth);
    }

    @Override
    public void execute() throws Exception {

        var labelResponse = createLabel.create(getLabel());
        var storyResponse = createStory.create(getStory());
        var deleteResponse = deleteStory.deleteById(30);
        var updateResponse = updateStory.updateById(getStoryForUpdate(), 32);
    }

    private LabelDto getLabel() {
        var label = new LabelDto();
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
        labels[1] = getLabel();

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
