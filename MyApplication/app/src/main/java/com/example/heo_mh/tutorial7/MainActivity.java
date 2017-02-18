package com.example.heo_mh.tutorial7;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

// YouTubeBaseActivity를 상속받는다.
public class MainActivity extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView;
    Button button;
    YouTubePlayer.OnInitializedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.youtubeButton);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubeView);
        listener = new YouTubePlayer.OnInitializedListener(){

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // Youtube 동영상 아이디 값
                youTubePlayer.loadVideo("ePpPVE-GGJw");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // 구글 개발자 API에 가서 API 키를 가져온다.
                youTubePlayerView.initialize("AIzaSyBDn_tsUiB-od8hLEKDoUb-Q6rsstMVAcA", listener);
            }
        });


    }
}
