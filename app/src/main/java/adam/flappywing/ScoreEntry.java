package adam.flappywing;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScoreEntry implements Serializable {
    public String name;
    public int value;

    public ScoreEntry(){

    }

    public ScoreEntry(String name, int value){
        this.name = name;
        this.value = value;
    }

    public static List<ScoreEntry> parseJSONArray(JSONArray jsonArray) {
        List<ScoreEntry> scoreEntryList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject row = new JSONObject(jsonArray.getString(i));
                scoreEntryList.add(new ScoreEntry(row.getString("name"), row.getInt("value")));
            } catch (JSONException e) { }
        }

        return scoreEntryList;
    }

    public static List<ScoreEntry> getAll() {
        return ScoreEntry.parseJSONArray(RestScoreClient.selectAll());
    }
}
