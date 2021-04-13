package adam.flappywing.rest;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.*;

import adam.flappywing.activities.HighscoreEntry;
import cz.msebera.android.httpclient.Header;

public class RestScoreClient {

    private static JSONArray ret;

    public static void insert(HighscoreEntry score) {
        RequestParams params = new RequestParams();
        params.add("name", score.name);
        params.add("value", String.valueOf(score.value));

        RestClient.post(params);
    }

    public static JSONArray selectAll() {
        RestClient.get(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                ret = timeline;
            }
        });

        return ret;
    }
}
