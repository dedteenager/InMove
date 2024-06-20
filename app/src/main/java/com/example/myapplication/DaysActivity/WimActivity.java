package com.example.myapplication.DaysActivity;

import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.home.Home_page;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class WimActivity extends AppCompatActivity {
    private Button btnNext;
    private Button btnCancel;
    public String link;
    private ImageView banner;
    public  DocumentReference AD_ref_pub;
    public int currentDayPub;
    public  DocumentReference docRefPub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._everyday_activity);
        btnCancel=findViewById(R.id.btnCancel);
        banner=findViewById(R.id.imageView8);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WimActivity.this, Home_page.class);
                startActivity(intent);
                WimActivity.this.finish();
            }
        });
        btnNext=findViewById(R.id.btnNext);
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.setEnableAutomaticInitialization(false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userId).collection("Progress").document("WimMethod");
        docRefPub =docRef;

        DocumentReference AD_ref = db.collection("ad").document("1");
        AD_ref_pub=AD_ref;
        AD_ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    link=documentSnapshot.getString("link");
                    String imageUrl = documentSnapshot.getString("image");

                    // Загрузка изображения с помощью Picasso
                    Picasso.get().load(imageUrl).into(banner);
                } else {
                    Log.d("TAG", "Документ не существует");
                }
            }
        });

        banner.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(intent);
            }
        });

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                    int currentDay = Integer.parseInt(documentSnapshot.getString("days"));
                    currentDayPub=currentDay;
                    String[] daysVideos=new String[21];

                    daysVideos[0]="kiTRv4uwFZI";
                    daysVideos[1]="8Tv3flM-xvY";
                    daysVideos[2]="29IkA7zcEBg";
                    daysVideos[3]="3gJ_tVtYUfQ";
                    daysVideos[4]="lCNz0XKx4kU";
                    daysVideos[5]="cs8ZoXjqcOg";
                    daysVideos[6]="zl24-hACr2A";
                    daysVideos[7]="6ZfqtqoRK4A";
                    daysVideos[8]="zTUiaj8MG2Q";
                    daysVideos[9]="3wR4_ExjdJQ";
                    daysVideos[10]="Elvt-w_TKSU";
                    daysVideos[11]="fE75OLGknYY";
                    daysVideos[12]="y2nuAonzMHI";
                    daysVideos[13]="tBWsLFR8PmI";
                    daysVideos[14]="gYxoeiGHDqk";
                    daysVideos[15]="--8Aah3vtWY";
                    daysVideos[16]="zl24-hACr2A";
                    daysVideos[17]="lCNz0XKx4kU";
                    daysVideos[18]="y2nuAonzMHI";
                    daysVideos[19]="6ZfqtqoRK4A";
                    daysVideos[20]="8Tv3flM-xvY";

                    YouTubePlayerListener listener = new YouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            String videoId = daysVideos[currentDay];
                            youTubePlayer.loadVideo(videoId, 0);
                        }

                        @Override
                        public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState playerState) {

                        }

                        @Override
                        public void onPlaybackQualityChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackQuality playbackQuality) {

                        }

                        @Override
                        public void onPlaybackRateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackRate playbackRate) {

                        }

                        @Override
                        public void onError(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerError playerError) {

                        }

                        @Override
                        public void onCurrentSecond(@NonNull YouTubePlayer youTubePlayer, float v) {

                        }

                        @Override
                        public void onVideoDuration(@NonNull YouTubePlayer youTubePlayer, float v) {

                        }

                        @Override
                        public void onVideoLoadedFraction(@NonNull YouTubePlayer youTubePlayer, float v) {

                        }

                        @Override
                        public void onVideoId(@NonNull YouTubePlayer youTubePlayer, @NonNull String s) {

                        }

                        @Override
                        public void onApiChange(@NonNull YouTubePlayer youTubePlayer) {

                        }
                    };
                    IFramePlayerOptions iframePlayerOptions = new IFramePlayerOptions.Builder()
                            .controls(1)
                            .autoplay(1)
                            .build();
                    youTubePlayerView.initialize(listener,iframePlayerOptions);
                } else {
                    Log.d("TAG", "No such document");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "Error getting document", e);
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

        if (currentDayPub==20){
            Map<String, Object> progressMapWimMethod = new HashMap<>();
            progressMapWimMethod.put("completed",true);
            docRefPub.update(progressMapWimMethod);
            builder.setMessage("Поздравляю вы закончили курс!");
        }
        else{
            builder.setMessage("Занятие закончилось");
        }
        builder.setPositiveButton("Завершить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map<String, Object> progressMapWimMethod = new HashMap<>();
                String nextDayStr=Integer.toString(currentDayPub+1);
                if(Integer.parseInt(nextDayStr)==21){
                    nextDayStr="0";
                }
                progressMapWimMethod.put("days",nextDayStr);
                docRefPub.update(progressMapWimMethod);
                Intent intent = new Intent(WimActivity.this, Home_page.class);
                startActivity(intent);
                WimActivity.this.finish();
            }
        });
        builder.show();
    }
}
