package adam.flappywing.utils;

import android.media.MediaPlayer;

class MyMediaPlayer {
    public static int idCounter = 0;
    public MediaPlayer mediaPlayer;
    public int pausePosition;
    public int resourceId;
    public boolean looping;
    public int id;

    public MyMediaPlayer() {
        pausePosition = 0;
        id = ++idCounter;
    }

    public int getId(){
        return id;
    }

    @Override
    public String toString(){
        return "Media player: "+id;
    }
}
