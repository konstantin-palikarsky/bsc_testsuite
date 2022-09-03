package testsuite.dtos;

public class StoryDto {
    private String title;
    private String authorName;
    private String contents;
    private LabelDto[] labels;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setLabels(LabelDto[] labels) {
        this.labels = labels;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getContents() {
        return contents;
    }

    public LabelDto[] getLabels() {
        return labels;
    }
}
