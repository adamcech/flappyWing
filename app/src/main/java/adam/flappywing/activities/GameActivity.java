package adam.flappywing.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import adam.flappywing.utils.AudioPlayer;
import adam.flappywing.game.GameSurface;


public class GameActivity extends Activity {
    private AudioPlayer audioPlayer;
    GameSurface gameSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO poresit audioPlayer at meni sound/song pripadne jinak apod..
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
        Intent intent = new Intent(GameActivity.this, GameActivity.class);
        startActivity(intent);

        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        end();
    }

    public void signalEnd() {
        audioPlayer.playRandomEndSound(getBaseContext());
    }
}
