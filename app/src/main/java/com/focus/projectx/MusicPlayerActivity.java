package com.focus.projectx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import java.util.Random;

public class MusicPlayerActivity extends AppCompatActivity implements Runnable{

    private SeekBar seekBar;
    private ToggleButton toogleButton;
    private ToggleButton toogleButton2;
    private ToggleButton toogleButton3;
    private MediaPlayer soundPlayer;
    private Thread soundThread;
    private ImageButton imageButton2;
    private ImageButton imageButton;
    private boolean music_random = false;
    int playmusic = 0;
    final Random random = new Random();
    final int[] music = {R.raw.ihanna, R.raw.medina, R.raw.pomdeter};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        //soundPlayer = MediaPlayer.create(this.getBaseContext(), R.raw.pomdeter);

        toogleButton = (ToggleButton) findViewById(R.id.toggleButton);
        /*toogleButton2 = (ToggleButton) findViewById(R.id.toggleButton2);
        toogleButton3 = (ToggleButton) findViewById(R.id.toggleButton3);*/
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        playListeners();

        soundThread = new Thread(this);
        soundPlayer = MediaPlayer.create(this.getBaseContext(), music[playmusic]);

        soundThread.start();

    }
    public void Randommusic(View view) {
        soundPlayer.reset();

        if (playmusic < music.length && !music_random ) {
            playmusic++;

        }


        if (music_random) {
            playmusic = random.nextInt(music.length - 1);
        }

        if (!(playmusic < music.length)) {
            playmusic = 0;
        }
        soundPlayer = MediaPlayer.create(view.getContext(), music[playmusic]);
        soundPlayer.start();
        seekBar.clearAnimation();
    }

    public void Randommusic2(View view) {
        soundPlayer.reset();

        if (playmusic < music.length && !music_random ) {
            playmusic--;


        }



        if (music_random) {
            playmusic = random.nextInt(music.length - 1);
        }

        if (!(playmusic < music.length)) {
            playmusic = music.length-1;
        }
        soundPlayer = MediaPlayer.create(view.getContext(), music[playmusic]);
        soundPlayer.start();
        seekBar.clearAnimation();
    }


    public void playListeners() {

        // final int [] music = {R.raw.ihanna, R.raw.medina, R.raw.pomdeter};


        //play&pause
        toogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toogleButton.isChecked()) {

                    soundPlayer.start();
                } else {
                    soundPlayer.pause();
                }


            }
        });

//nextbtn
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playmusic < music.length) {
                    toogleButton.setChecked(true);
                    Randommusic(view);

                }


            }
        });


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playmusic < music.length) {
                    toogleButton.setChecked(true);
                    Randommusic2(view);

                }


            }
        });
/*
        toogleButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toogleButton2.isChecked()) {

                    music_random = true;
                } else {
                    music_random = false;
                    // soundPlayer.pause();
                }
            }
        });

        toogleButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toogleButton3.isChecked()) {

                    soundPlayer.start();

                } else {
                    soundPlayer.pause();
                }
            }
        });




     */

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    soundPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    @Override
    public void run() {
        int currentPosition = 0;
        int soundTotal = soundPlayer.getDuration();
        seekBar.setMax(soundTotal);


        while (soundPlayer != null && currentPosition < soundTotal) {
            try {
                Thread.sleep(300);
                currentPosition = soundPlayer.getCurrentPosition();
            } catch (InterruptedException soundException) {
                return;
            } catch (Exception otherException) {
                return;
            }
            seekBar.setProgress(currentPosition);
        }




    }


}

