package main;

import main.apis.MonolithApi;
import main.apis.ThesisApi;
import main.workflows.requests.SearchStoriesRequest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) {
        ThesisApi api = new MonolithApi();

        var search = new SearchStoriesRequest();
        HttpResponse<String> response;
        try {
            response = search.send(api);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(response.body().substring(0, 3096));

    }


}

