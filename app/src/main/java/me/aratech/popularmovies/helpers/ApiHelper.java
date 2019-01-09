package me.aratech.popularmovies.helpers;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/***
 * Web Api helper to execute web api methods
 */
public class ApiHelper {

    /***
     * Get Movies Web API result
     * @param url Web Method Url
     * @return Web API Response
     * @throws IOException IO exception if failed to execute the request
     */
    public Response getMoviesResult(URL url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        return client.newCall(request).execute();
    }
}
