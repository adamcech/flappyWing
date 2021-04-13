package adam.flappywing.activities;

import android.app.Activity;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import adam.flappywing.utils.AudioPlayer;
import adam.flappywing.Config;
import adam.flappywing.R;

public class AboutActivity extends Activity {

    private AudioPlayer audioPlayer;

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        audioPlayer = AudioPlayer.getInstance(false);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        backButton = findViewById(R.id.aboutButtonBack);

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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(17432576, 17432577);
        Config.ALLOW_MUSIC = true;
        finish();
    }
}
