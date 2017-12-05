package adam.flappywing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;


public class GameActivity extends Activity {
    private AudioPlayer audioPlayer;
    GameSurface gameSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        audioPlayer = AudioPlayer.getInstance(true);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        audioPlayer.playRandomStartSound(getBaseContext());
        audioPlayer.playRandomGameSong(getBaseContext());

        gameSurface = new GameSurface(this);
        setContentView(gameSurface);
    }

    @Override
    public void onResume() {
        super.onResume();

        audioPlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        audioPlayer.pause();
        finish();
    }

    @Override
    public void onStop() {
        super.onStop();

        audioPlayer.pause();
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        audioPlayer.stop();
        finish();
    }


    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public void end() {
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        startActivity(intent);

        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        end();
    }

    public void refreshScore(int score) {
        if (score > Config.PLAYER_HIGHSCORE){
            Config.PLAYER_HIGHSCORE = score;
            SharedPreferences mySharedPref;
            SharedPreferences.Editor mySharedEditor;

            mySharedPref = getSharedPreferences("config", Context.MODE_PRIVATE);
            mySharedEditor = mySharedPref.edit();
            mySharedEditor.putInt("PLAYER_HIGHSCORE", Config.PLAYER_HIGHSCORE);
            mySharedEditor.apply();
        }

        new InsertScore().execute(new ScoreEntry(Config.PLAYER_NAME, score));
    }


    private class InsertScore extends AsyncTask<ScoreEntry, Void, Void> {

        @Override
        protected Void doInBackground(ScoreEntry... params) {
            RestScoreClient.insert(params[0]);
            return null;
        }
    }
}
