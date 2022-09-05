package testsuite.dtos;

public record StoryDto(String title, String authorName, String contents, LabelDto[] labels) {

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
