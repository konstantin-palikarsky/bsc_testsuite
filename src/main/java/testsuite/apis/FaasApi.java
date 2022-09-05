package testsuite.apis;

import testsuite.repositories.RequestStatisticsRepository;
import testsuite.repositories.entities.RequestStatistics;

public record FaasApi(RequestStatisticsRepository stats) implements ThesisApi {

    //TODO ADD URLS

    @Override
    public void saveStats(RequestStatistics stat) {
        stats.save(stat);
    }

    @Override
    public String searchStoriesUrl(String title, String label) {
        return null;
    }

    @Override
    public String getStoryUrl(long id) {
        return null;
    }

    @Override
    public String pdfGetStoryUrl(long id) {
        return null;
    }

    @Override
    public String updateStoryUrl(long id) {
        return null;
    }

    @Override
    public String deleteStoryUrl(long id) {
        return null;
    }

    @Override
    public String createStoryUrl() {
        return null;
    }

    @Override
    public String searchLabelsUrl(String label) {
        return null;
    }

    @Override
    public String loginUrl() {
        return null;
    }

    @Override
    public String createUserUrl() {
        return null;
    }
}
