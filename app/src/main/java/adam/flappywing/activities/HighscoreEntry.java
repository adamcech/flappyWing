package adam.flappywing.activities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import adam.flappywing.rest.RestScoreClient;

public class HighscoreEntry implements Serializable {
    public String name;
    public int value;

    public HighscoreEntry(){

    }

    public HighscoreEntry(String name, int value){
        this.name = name;
        this.value = value;
    }

    public static List<HighscoreEntry> parseJSONArray(JSONArray jsonArray) {
        List<HighscoreEntry> highscoreEntryList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject row = new JSONObject(jsonArray.getString(i));
                highscoreEntryList.add(new HighscoreEntry(row.getString("name"), row.getInt("value")));
            } catch (JSONException e) { }
        }

        return highscoreEntryList;
    }

    public static List<HighscoreEntry> getAll() {
        return HighscoreEntry.parseJSONArray(RestScoreClient.selectAll());
    }
}
