package com.example.songsplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    MediaPlayer myMediaPlayer;
    ArrayList<File> mySongs;
    Thread updateSeekBar;
    private String sname;
    private int position;
    private TextView textViewSongName;
    private SeekBar seekBar;
    private Button btnnext;
    private Button btnprev;
    private Button btnpause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        initLayoutView();
        getBundleData();
        updateSeekBar = new Thread() {
            @Override
            public void run() {
                int TotalDuration = myMediaPlayer.getDuration();
                int currentPosition = 0;

                while (currentPosition < TotalDuration) {
                    try {
                        sleep(500);
                        currentPosition = myMediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentPosition);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        seekBar.setMax(myMediaPlayer.getDuration());
        updateSeekBar.start();


    }

    private void setUpDataWithView() {
        Uri u = Uri.parse(mySongs.get(position).toString());

        myMediaPlayer = MediaPlayer.create(getApplicationContext(), u);
        myMediaPlayer.start();


    }


    private void initLayoutView() {
        textViewSongName = findViewById(R.id.textViewAudioName);
        seekBar = findViewById(R.id.seekBar);
        btnnext = findViewById(R.id.btn_next);
        btnprev = findViewById(R.id.btn_prev);
        btnpause = findViewById(R.id.btn_pause);
    }

    private void getBundleData() {
        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
        position = bundle.getInt("pos", 0);

        setUpDataWithView();
        setUpViewListener();
    }

    private void setUpViewListener() {

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                myMediaPlayer.seekTo(seekBar.getProgress());

            }
        });

        btnpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myMediaPlayer.isPlaying()) {
                    btnpause.setBackgroundResource(R.drawable.icon_play);
                    myMediaPlayer.pause();
                } else {
                    btnpause.setBackgroundResource(R.drawable.icon_pause);
                    myMediaPlayer.start();
                }
            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMediaPlayer.stop();
                myMediaPlayer.release();
                position = ((position + 1) % mySongs.size());

                Uri u = Uri.parse(mySongs.get(position).toString());

                myMediaPlayer = MediaPlayer.create(getApplicationContext(), u);
               // sname = mySongs.get(position).getName().toString();
               // textViewSongName.setText(sname);
                myMediaPlayer.start();
            }
        });

        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMediaPlayer.stop();
                myMediaPlayer.release();
                position = ((position-1)<0) ? (mySongs.size()-1) : (position-1);
                Uri u =Uri.parse(mySongs.get(position).toString());
                myMediaPlayer=MediaPlayer.create(getApplicationContext(),u);
                myMediaPlayer.start();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}