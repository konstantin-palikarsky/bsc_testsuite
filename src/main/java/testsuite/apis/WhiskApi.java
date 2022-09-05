package testsuite.apis;

import testsuite.repositories.RequestStatisticsRepository;
import testsuite.repositories.entities.RequestStatistics;

public record WhiskApi(RequestStatisticsRepository stats) implements ThesisApi {

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

        return "https://localhost:31001/api/v1/web/guest/bsc/search-stories?title=" + title + "&label=" + label;
    }

    @Override
    public String exportStoryUrl(long id) {
        return "https://localhost:31001/api/v1/web/guest/bsc/pdf-get-story/"+id;
    }

    @Override
    public String updateStoryUrl(long id) {
        return "https://localhost:31001/api/v1/web/guest/bsc/update-story/"+id;
    }

    @Override
    public String deleteStoryUrl(long id) {
        return "https://localhost:31001/api/v1/web/guest/bsc/delete-story/"+id;
    }

    @Override
    public String createStoryUrl() {
        return "https://localhost:31001/api/v1/web/guest/bsc/create-story";
    }

    @Override
    public String searchLabelsUrl(String label) {
        if (label == null) {
            label = "";
        }

        return "https://localhost:31001/api/v1/web/guest/bsc/search-labels?label=" + label;
    }

    @Override
    public String loginUrl() {
        return "https://localhost:31001/api/v1/web/guest/bsc/login";
    }

    @Override
    public String createUserUrl() {
        return "https://localhost:31001/api/v1/web/guest/bsc/create-user";
    }
}
