package com.example.eggstopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    SeekBar timerSeekBar;
    boolean counterIsActive = false;
    Button goButton;
    CountDownTimer countDownTimer;

    public void resetTimer()
    {
        goButton.setText("GO!");
        timerTextView.setText("00:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        counterIsActive = false;
    }
    public void buttonClicked (View view)
    {
        if (counterIsActive)
        {
            resetTimer();
        }
        else {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            goButton.setText("STOP!");

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000)
            {
                @Override
                public void onTick(long l)
                {
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish()
                {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                    resetTimer();
                }
            };
            countDownTimer.start();
        }
    }

    public  void updateTimer(int secondsLeft)
    {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);
        if (seconds < 10 && minutes < 10)
        {
            timerTextView.setText("0" + Integer.toString(minutes) + ":0" + Integer.toString(seconds));
        }
        else if (seconds < 10)
        {
            timerTextView.setText(Integer.toString(minutes) + ":0" + Integer.toString(seconds));
        }
        else if (minutes < 10)
        {
            timerTextView.setText("0" + Integer.toString(minutes) + ":" + Integer.toString(seconds));
        }
        else
        {
            timerTextView.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = (Button)findViewById(R.id.goButton);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}