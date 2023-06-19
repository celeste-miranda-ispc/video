package com.example.video;

import androidx.appcompat.app.AppCompatActivity;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private static final String VIDEO_SAMPLE = "ecoaliados";
    private VideoView mVideoView;

    private Uri getMedia(String mediaName) {
        return Uri.parse( "android.resource://"+getPackageName()+"/raw/"+mediaName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVideoView = findViewById(R.id.videoview);

        if (savedInstanceState !=null) {
            mCurrentPosition=savedInstanceState.getInt(PLAYBACK_TIME);
        }

        MediaController mediaC = new MediaController(this);
        mediaC.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(mediaC);
        mediaC.setAnchorView(mVideoView);

        mVideoView.start();
    }

    private int mCurrentPosition = 0;
    private static final String PLAYBACK_TIME="play_time";

    private void initializePlayer(){
        Uri videoUri=getMedia(VIDEO_SAMPLE);
        //mVideoView.setVideoURI(videoURI);
        mVideoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.ecoaliados ));
        if (mCurrentPosition>0){
            mVideoView.seekTo(mCurrentPosition);
        } else {
            /*saltar a 1, muestra el primer cuadro del video.*/
            mVideoView.seekTo(1);
        }

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(MainActivity.this,"Playback completed",
                        Toast.LENGTH_SHORT).show();
                mVideoView.seekTo(1);
            }
        });
    }

    private void releasePlayer(){mVideoView.stopPlayback();}

    @Override
    protected void onStart(){
        super.onStart();
        initializePlayer();
    }

    @Override
    protected void onStop(){
        super.onStop();
        releasePlayer();
    }

    @Override
    protected void onPause(){
        super.onPause();
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.N){
            mVideoView.pause();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(PLAYBACK_TIME,mVideoView.getCurrentPosition());
    }
}