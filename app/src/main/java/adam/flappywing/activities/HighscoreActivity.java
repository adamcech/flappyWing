package adam.flappywing.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import adam.flappywing.utils.AudioPlayer;
import adam.flappywing.Config;
import adam.flappywing.R;

public class HighscoreActivity extends Activity {
    private AudioPlayer audioPlayer;
    Button backButton;
    TextView personalRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        audioPlayer = AudioPlayer.getInstance(false);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        backButton = findViewById(R.id.highscoreButtonBack);
        backButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        backButton.setBackgroundColor(Color.rgb(175, 175, 175));
                        break;
                    case MotionEvent.ACTION_UP:
                        backButton.setBackgroundColor(Color.rgb(223, 223, 223));
                        Config.ALLOW_MUSIC = true;
                        onBackPressed();
                        break;
                }

                return true;
            }
        });


        TextView personalRecord = findViewById(R.id.highscoreTextViewPersonalRecord);
        personalRecord.setText("Personal record: "+String.valueOf(Config.PLAYER_HIGHSCORE));

        if (isOnline()){
            new SelectAllScore().execute();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onResume() {
        super.onResume();

        audioPlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (!Config.ALLOW_MUSIC){
            audioPlayer.pause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();


        if (!Config.ALLOW_MUSIC) {
            audioPlayer.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


        if (!Config.ALLOW_MUSIC) {
            audioPlayer.stop();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(17432576, 17432577);

        Config.ALLOW_MUSIC = true;
        finish();
    }

    private class SelectAllScore extends AsyncTask<Void, Void, List<HighscoreEntry>> {

        @Override
        protected List<HighscoreEntry> doInBackground(Void... params) {
            return HighscoreEntry.getAll();
        }

        @Override
        protected void onPostExecute(List<HighscoreEntry> levelsList) {
            super.onPostExecute(levelsList);

            final HighscoreAdapter adapter = new HighscoreAdapter(getBaseContext(),
                    R.layout.highscore_entry, levelsList);

            ListView listViewLevels = findViewById(R.id.highscoreListview);
            listViewLevels.setAdapter(adapter);
        }
    }
}
