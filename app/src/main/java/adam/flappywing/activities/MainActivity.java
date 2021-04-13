package adam.flappywing.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import adam.flappywing.utils.AudioPlayer;
import adam.flappywing.Config;
import adam.flappywing.R;

public class MainActivity extends Activity {

    private AudioPlayer audioPlayer;

    Button playButton;
    Button highscoreButton;
    Button settingsButton;
    Button aboutButton;
    Button exitButton;

    SharedPreferences mySharedPref;
    SharedPreferences.Editor mySharedEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mySharedPref = getSharedPreferences("config", Context.MODE_PRIVATE);
        Config.PLAYER_NAME = mySharedPref.getString("PLAYER_NAME", "");
        Config.AUDIO_VOLUME_CURR = mySharedPref.getInt("AUDIO_VOLUME_CURR", 0);
        Config.PLAYER_HIGHSCORE = mySharedPref.getInt("PLAYER_HIGHSCORE", 0);

        if (Config.PLAYER_NAME.equals("")){
            alertPlayerName();
        }

        audioPlayer = AudioPlayer.getInstance(true);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        audioPlayer.play(getBaseContext(), R.raw.mos_eisley, true);

        setContentView(R.layout.activity_main);

        final int buttonClickColor = Color.rgb(175, 175, 175);
        final int buttonOriginalColor = Color.rgb(223, 223, 223);
        playButton = findViewById(R.id.mainButtonPlay);
        highscoreButton = findViewById(R.id.mainButtonHighScore);
        settingsButton = findViewById(R.id.mainButtonSettings);
        aboutButton = findViewById(R.id.mainButtonAbout);
        exitButton = findViewById(R.id.mainButtonExit);


        playButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        playButton.setBackgroundColor(buttonClickColor);
                        break;
                    case MotionEvent.ACTION_UP:
                        playButton.setBackgroundColor(buttonOriginalColor);

                        Intent intent = new Intent(MainActivity.this, GameActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return true;
            }
        });

        highscoreButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        highscoreButton.setBackgroundColor(buttonClickColor);
                        break;
                    case MotionEvent.ACTION_UP:
                        highscoreButton.setBackgroundColor(buttonOriginalColor);

                        Config.ALLOW_MUSIC = true;
                        Intent intent = new Intent(MainActivity.this, HighscoreActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });

        settingsButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        settingsButton.setBackgroundColor(buttonClickColor);
                        break;
                    case MotionEvent.ACTION_UP:
                        settingsButton.setBackgroundColor(buttonOriginalColor);

                        Config.ALLOW_MUSIC = true;
                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });

        aboutButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        aboutButton.setBackgroundColor(buttonClickColor);
                        break;
                    case MotionEvent.ACTION_UP:
                        aboutButton.setBackgroundColor(buttonOriginalColor);

                        Config.ALLOW_MUSIC = true;
                        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });

        exitButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        exitButton.setBackgroundColor(buttonClickColor);
                        break;
                    case MotionEvent.ACTION_UP:
                        exitButton.setBackgroundColor(buttonOriginalColor);
                        finish();
                        break;
                }

                return true;
            }
        });
    }


    private void alertPlayerName() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your name:");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Config.PLAYER_NAME = input.getText().toString();

                mySharedPref = getSharedPreferences("config", Context.MODE_PRIVATE);
                mySharedEditor = mySharedPref.edit();
                mySharedEditor.putString("PLAYER_NAME", Config.PLAYER_NAME);
                mySharedEditor.apply();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setCancelable(false);
        builder.show();
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
        Config.ALLOW_MUSIC = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        audioPlayer.stop();
    }
}
