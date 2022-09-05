package testsuite.apis;

import testsuite.repositories.RequestStatisticsRepository;
import testsuite.repositories.entities.RequestStatistics;

public record FaasApi(RequestStatisticsRepository stats) implements ThesisApi {

    @Override
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
        return "http://192.168.49.2:31112/function/search-stories?title=" + title + "&label=" + label;
    }

    @Override
    public String exportStoryUrl(long id) {
        return "http://192.168.49.2:31112/function/pdf-get-story/" + id;
    }

    @Override
    public String updateStoryUrl(long id) {
        return "http://192.168.49.2:31112/function/update-story/" + id;
    }

    @Override
    public String deleteStoryUrl(long id) {
        return "http://192.168.49.2:31112/function/delete-story/" + id;
    }

    @Override
    public String createStoryUrl() {
        return "http://192.168.49.2:31112/function/create-story";
    }

    @Override
    public String searchLabelsUrl(String label) {
        if (label == null) {
            label = "";
        }
        return "http://192.168.49.2:31112/function/search-labels?label=" + label;
    }

    @Override
    public String loginUrl() {
        return "http://192.168.49.2:31112/function/login";
    }

    @Override
    public String createUserUrl() {
        return "http://192.168.49.2:31112/function/create-user";
    }
}
