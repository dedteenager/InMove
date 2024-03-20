package com.example.myapplication.DaysActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.home.Home_page;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class KhatkhaActivity extends AppCompatActivity {
    private VideoView videoView;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    private Button btnNext;
    private static final String API_KEY = "AIzaSyAPSw7i5naSiIk0zFfMUu8qVzYXlqcZboI";
    private static final String VIDEO_ID = "ZCDqDy6-azU";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.khatkha_activity);
        btnNext=findViewById(R.id.btnNext);
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "ctuj7P5P2w4";
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCompletionScreen();
            }
        });
    }

    private void showCompletionScreen() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Тренировка закончилась");
        builder.setPositiveButton("Завершить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(KhatkhaActivity.this, Home_page.class);
                startActivity(intent);
                KhatkhaActivity.this.finish();
            }
        });
        builder.show();
    }
}
