package testsuite.apis;

public interface ThesisApi {
    /**
     * Stories API
     */

    public String searchStoriesUrl(String title, String label);

    public String getStoryUrl(long id);

    public String pdfGetStoryUrl(long id);

    public String updateStoryUrl(long id);

    public String deleteStoryUrl(long id);

    public String createStoryUrl();

    /**
     * Labels API
     */

    public String searchLabelsUrl(String label);

    public String createLabelUrl();

    /**
     * Users API
     */
    
    public String loginUrl();

    public String createUserUrl();
}
