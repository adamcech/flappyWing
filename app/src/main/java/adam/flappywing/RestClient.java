package adam.flappywing;

import com.loopj.android.http.*;

class RestClient {
    private static final String url = "http://spiderx.cz/score.php";

    private static SyncHttpClient client = new SyncHttpClient();

    public static void get(AsyncHttpResponseHandler responseHandler) {
        client.get(url, responseHandler);
    }

    public static void post(RequestParams params) {
        client.post(url, params, new BlackholeHttpResponseHandler());
    }
}