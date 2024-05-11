package com.example.myapplication.DaysActivity;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class KhatkhaActivity extends AppCompatActivity {
    private Button btnNext;
    private Button btnCancel;
    private ImageView banner;
    public int currentDayPub;
    public  DocumentReference docRefPub,AD_ref_pub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._everyday_activity);
banner=findViewById(R.id.imageView8);
        btnCancel=findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(KhatkhaActivity.this, Home_page.class);
                    startActivity(intent);
                    KhatkhaActivity.this.finish();
            }
        });

        btnNext=findViewById(R.id.btnNext);
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.setEnableAutomaticInitialization(false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userId).collection("Progress").document("Khatkha");
        DocumentReference AD_ref = db.collection("ad").document("1");
        AD_ref_pub=AD_ref;
        docRefPub =docRef;
        AD_ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String imageUrl = documentSnapshot.getString("image");
                    // Загрузка изображения с помощью Picasso
                    Picasso.get().load(imageUrl).into(banner);
                } else {
                    Log.d("TAG", "Документ не существует");
                }
            }
        });
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                    int currentDay = Integer.parseInt(documentSnapshot.getString("days"));
                    currentDayPub=currentDay;
                    String[] daysVideos=new String[21];

                    daysVideos[0]="cSm6EZxE5Ic";
                    daysVideos[1]="8kSjeuBVqjs";
                    daysVideos[2]="--pnreEPBLE";
                    daysVideos[3]="VjQvtE_el7w";
                    daysVideos[4]="TJ_7-n02nEg";
                    daysVideos[5]="JesWs7SALfQ";
                    daysVideos[6]="vBCNlxFTkJk";
                    daysVideos[7]="c0-hvjV2A5Y";
                    daysVideos[8]="hb0XLX0b4Y4";
                    daysVideos[9]="H2I6V0NlaHg";
                    daysVideos[10]="fx7tkHLD3RY";
                    daysVideos[11]="PMak_mhQh5k";
                    daysVideos[12]="8kSjeuBVqjs";
                    daysVideos[13]="sQRfp2Xp6U0";
                    daysVideos[14]="--pnreEPBLE";
                    daysVideos[15]="VjQvtE_el7w";
                    daysVideos[16]="TJ_7-n02nEg";
                    daysVideos[17]="JesWs7SALfQ";
                    daysVideos[18]="vBCNlxFTkJk";
                    daysVideos[19]="c0-hvjV2A5Y";
                    daysVideos[20]="hb0XLX0b4Y4";

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
            Map<String, Object> progressMapKhatkha = new HashMap<>();
            progressMapKhatkha.put("completed",true);
            docRefPub.update(progressMapKhatkha);
            builder.setMessage("Поздравляю вы закончили курс!");
        }
        else{
            builder.setMessage("Тренировка закончилась");
        }
        builder.setPositiveButton("Завершить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map<String, Object> progressMapKhatkha = new HashMap<>();
                String nextDayStr=Integer.toString(currentDayPub+1);
                if(Integer.parseInt(nextDayStr)==21){
                    nextDayStr="0";
                }
                progressMapKhatkha.put("days",nextDayStr);
                docRefPub.update(progressMapKhatkha);
                Intent intent = new Intent(KhatkhaActivity.this, Home_page.class);
                startActivity(intent);
                KhatkhaActivity.this.finish();
            }
        });
        builder.show();
    }
}
