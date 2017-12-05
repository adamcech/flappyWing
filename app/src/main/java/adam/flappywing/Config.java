package adam.flappywing;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import java.io.Serializable;

public class Config implements Serializable {
    public static boolean ALLOW_MUSIC = false;
    public static int AUDIO_VOLUME_MAX = 100;
    public static int AUDIO_VOLUME_CURR = 0;
    public static int PLAYER_HIGHSCORE = 0;
    public static String PLAYER_NAME = "";

    public static int getAudioVolumeCurrSeekBarValue(){
        return (100 - AUDIO_VOLUME_CURR) / 2;
    }

    public static void setAudioVolumeCurrSeekBarValue(int value){
        AUDIO_VOLUME_CURR = 100 - (value * 2);
    }

    public static float getSoundLevel() {
        float soundLevel = (float)(Math.log(Config.AUDIO_VOLUME_MAX - Config.AUDIO_VOLUME_CURR) / Math.log(Config.AUDIO_VOLUME_MAX));
        return soundLevel < 0 ? 0 : soundLevel;
    }
}
