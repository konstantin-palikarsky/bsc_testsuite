package testsuite.apis;

import testsuite.repositories.RequestStatisticsRepository;
import testsuite.repositories.entities.RequestStatistics;

public record MonolithApi(RequestStatisticsRepository stats) implements ThesisApi {

    public void saveStats(RequestStatistics stat) {
        stats.save(stat);
    }

    @Override
    public String searchStoriesUrl(String title, String label) {
        if (title == null) {
            title = "";
        }
        if (label == null) {
            label = "";
        }

        return "http://localhost:8080/stories?title=" + title + "&label=" + label;
    }

    @Override
    public String exportStoryUrl(long id) {
        return "http://localhost:8080/stories/pdf/" + id;
    }

    @Override
    public String updateStoryUrl(long id) {
        return "http://localhost:8080/stories/" + id;
    }

    @Override
    public String deleteStoryUrl(long id) {
        return "http://localhost:8080/stories/" + id;
    }

    @Override
    public String createStoryUrl() {
        return "http://localhost:8080/stories";
    }


    @Override
    public String searchLabelsUrl(String label) {

        if (label == null) {
            label = "";
        }

        return "http://localhost:8080/labels?label=" + label;
    }

    @Override
    public String loginUrl() {
        return "http://localhost:8080/login";
    }

    @Override
    public String createUserUrl() {
        return "http://localhost:8080/users";
    }

}
