package adam.flappywing.utils;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

import adam.flappywing.R;

public class AudioPlayer  {
    private static AudioPlayer instance;

    private List<MyMediaPlayer> players = new ArrayList<>();

    private AudioPlayer(){

    }

    public static AudioPlayer getInstance(boolean reinit){
        if (instance == null || reinit){
            if (instance != null && reinit){
                instance.stop();
            }
            instance = new AudioPlayer();
        }
        return instance;
    }

    public void play(Context context, int rSoundId, boolean looping) {
        play(context, rSoundId, looping, 0);
    }

    public void play(Context context, int rSoundId, boolean looping, int pause){
        play(context, rSoundId, looping, 0, false);
    }

    public void play(Context context, int rSoundId, boolean looping, int pause, boolean isSettings){
        final MyMediaPlayer myMediaPlayer = new MyMediaPlayer();
        myMediaPlayer.resourceId = rSoundId;
        myMediaPlayer.looping = looping;

        myMediaPlayer.mediaPlayer = MediaPlayer.create(context, rSoundId);
        myMediaPlayer.mediaPlayer.setLooping(looping);
        myMediaPlayer.mediaPlayer.seekTo(pause);

        myMediaPlayer.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                mp = null;
                players.remove(myMediaPlayer);
            }
        });

        myMediaPlayer.mediaPlayer.setVolume(1.0f, 1.0f);

        myMediaPlayer.mediaPlayer.start();

        players.add(myMediaPlayer);
    }

    public void playRandomGameSong(Context context){
       play(context, R.raw.imperial_march, true);
    }

    public void playRandomStartSound(Context context){
        play(context, R.raw.force, false);
    }

    public void playRandomEndSound(Context context){
        play(context, R.raw.jabba, false);
    }

    public void start() {
        for (MyMediaPlayer player: players) {
            if (!player.mediaPlayer.isPlaying()){
                player.mediaPlayer.seekTo(player.pausePosition);
                player.mediaPlayer.start();
            }
        }
    }

    public void pause() {
        for (MyMediaPlayer player: players) {
            if (player.mediaPlayer.isPlaying()){
                player.mediaPlayer.pause();
                player.pausePosition = player.mediaPlayer.getCurrentPosition();
            }
        }
    }

    public void stop() {
        for (MyMediaPlayer player: players) {
            if (player.mediaPlayer.isPlaying()){
                player.mediaPlayer.stop();
                player.mediaPlayer.release();
                player.mediaPlayer = null;
                player.pausePosition = 0;
            }
        }
        players.clear();
    }

    public void reInitSoundLevel(Context context, boolean isSettings){
        List<MyMediaPlayer> tempPlayers = new ArrayList<>(players);

        for (MyMediaPlayer player: players) {
            player.pausePosition = player.mediaPlayer.getCurrentPosition();
            player.mediaPlayer.release();
            player.mediaPlayer = null;
        }
        players.clear();

        for (MyMediaPlayer player: tempPlayers) {
            if (player.looping) {
                play(context, player.resourceId, true, player.pausePosition, isSettings);
            }
        }
    }

    public void playEngine(Context context) {
        play(context, R.raw.engine, false);
    }

    public void playExplosion(Context context) {
        play(context, R.raw.explosion, false);
    }
}
