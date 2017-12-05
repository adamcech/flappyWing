package adam.flappywing;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class AudioPlayer  {
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
        float soundLevel = Config.getSoundLevel();
        if (soundLevel <= 0 && !isSettings) { return; }

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

        myMediaPlayer.mediaPlayer.setVolume(soundLevel, soundLevel);

        myMediaPlayer.mediaPlayer.start();

        players.add(myMediaPlayer);
    }

    public void playRandomGameSong(Context context){
        int soundId = -1;

        switch (new Random().nextInt(5)){
            case 0: soundId = R.raw.duel_of_the_fates; break;
            case 1: soundId = R.raw.across_the_stars; break;
            case 2: soundId = R.raw.imperial_march; break;
            case 3: soundId = R.raw.mos_eisley; break;
            case 4: soundId = R.raw.the_force_theme; break;
        }

        play(context, soundId, true);
    }

    public void playRandomStartSound(Context context){
        int soundId = -1;

        switch (new Random().nextInt(4)){
            case 0: soundId = R.raw.you_should_not_come_back; break;
            case 1: soundId = R.raw.chewy_roar; break;
            case 2: soundId = R.raw.r2d2; break;
            case 3: soundId = R.raw.force; break;
        }

        play(context, soundId, false);
    }

    public void playRandomEndSound(Context context){
        int soundId = -1;

        switch (new Random().nextInt(3)){
            case 0: soundId = R.raw.vader_breathing; break;
            case 1: soundId = R.raw.jabba; break;
            case 2: soundId = R.raw.chosenone; break;
        }

        play(context, soundId, false);
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
