package adam.flappywing.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import adam.flappywing.utils.AudioPlayer;
import adam.flappywing.Config;
import adam.flappywing.R;

public class SettingsActivity extends Activity {
    private AudioPlayer audioPlayer;

    EditText name;
    SeekBar volumeLevel;
    Button saveButton;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        audioPlayer = AudioPlayer.getInstance(false);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        if (Config.getAudioVolumeCurrSeekBarValue() == 0){
            audioPlayer.play(getBaseContext(), R.raw.mos_eisley, true, 0, true);
        }

        final int buttonClickColor = Color.rgb(175, 175, 175);
        final int buttonOriginalColor = Color.rgb(223, 223, 223);

        name = findViewById(R.id.settingsEditTextName);
        name.setText(Config.PLAYER_NAME);

        volumeLevel = findViewById(R.id.settingsSeekBarVolume);
        volumeLevel.setProgress(Config.getAudioVolumeCurrSeekBarValue());

        final Context context = getBaseContext();
        volumeLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int temp = Config.AUDIO_VOLUME_CURR;
                Config.setAudioVolumeCurrSeekBarValue(seekBar.getProgress());
                audioPlayer.reInitSoundLevel(context, true);
                Config.AUDIO_VOLUME_CURR = temp;
            }
        });

        saveButton = findViewById(R.id.settingsButtonSave);
        saveButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        saveButton.setBackgroundColor(buttonClickColor);
                        break;
                    case MotionEvent.ACTION_UP:
                        saveButton.setBackgroundColor(buttonOriginalColor);
                        Config.setAudioVolumeCurrSeekBarValue(volumeLevel.getProgress());
                        Config.PLAYER_NAME = name.getText().toString();

                        SharedPreferences mySharedPref;
                        SharedPreferences.Editor mySharedEditor;

                        mySharedPref = getSharedPreferences("config", Context.MODE_PRIVATE);
                        mySharedEditor = mySharedPref.edit();
                        mySharedEditor.putString("PLAYER_NAME", Config.PLAYER_NAME);
                        mySharedEditor.putInt("AUDIO_VOLUME_CURR", Config.AUDIO_VOLUME_CURR);
                        mySharedEditor.apply();

                        onBackPressed();
                        break;
                }

                return true;
            }
        });

        backButton = findViewById(R.id.settingsButtonBack);
        backButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        backButton.setBackgroundColor(buttonClickColor);
                        break;
                    case MotionEvent.ACTION_UP:
                        backButton.setBackgroundColor(buttonOriginalColor);
                        onBackPressed();
                        break;
                }

                return true;
            }
        });
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

        audioPlayer.reInitSoundLevel(getBaseContext(), false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(17432576, 17432577);
        Config.ALLOW_MUSIC = true;
        finish();
    }
}
