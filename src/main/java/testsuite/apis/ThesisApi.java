package testsuite.apis;

import testsuite.repositories.entities.RequestStatistics;

public interface ThesisApi {
    public void saveStats(RequestStatistics stat);

    /**
     * Stories API
     */

    public String searchStoriesUrl(String title, String label);

    public String exportStoryUrl(long id);

    public String updateStoryUrl(long id);

    public String deleteStoryUrl(long id);

    public String createStoryUrl();

    /**
     * Labels API
     */

    public String searchLabelsUrl(String label);

    /**
     * Users API
     */

    public String loginUrl();

    public String createUserUrl();
}
