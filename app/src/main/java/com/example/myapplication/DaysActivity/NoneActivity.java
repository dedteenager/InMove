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

public class NoneActivity extends AppCompatActivity {
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
                Intent intent = new Intent(NoneActivity.this, Home_page.class);
                startActivity(intent);
                NoneActivity.this.finish();
            }
        });
        btnNext=findViewById(R.id.btnNext);
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.setEnableAutomaticInitialization(false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userId).collection("Progress").document("Detka");
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

                    daysVideos[0]="rZdJKF0yCKE";
                    daysVideos[1]="JnrXZ3034hg";
                    daysVideos[2]="23WTBzutLJE";
                    daysVideos[3]="J3tRz0pyfys";
                    daysVideos[4]="yVUcHEOr450";
                    daysVideos[5]="x-BvlPDgOps";
                    daysVideos[6]="UpN2q4FkzaA";
                    daysVideos[7]="B8quRRZCPAI";
                    daysVideos[8]="vVzPbZ9Jx_A";
                    daysVideos[9]="23WTBzutLJE";
                    daysVideos[10]="KtM693B72_4";
                    daysVideos[11]="UmyVjaG6i00";
                    daysVideos[12]="sZf7ladUoD8";
                    daysVideos[13]="U2kQXG9jH28";
                    daysVideos[14]="tnPIRMdWOrs";
                    daysVideos[15]="xLdGMqu3LtI";
                    daysVideos[16]="G7IFG6-mRAM";
                    daysVideos[17]="B8quRRZCPAI";
                    daysVideos[18]="UmyVjaG6i00";
                    daysVideos[19]="yVUcHEOr450";
                    daysVideos[20]="sZf7ladUoD8";

                    YouTubePlayerListener listener = new YouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            String videoId = daysVideos[currentDay];
                            youTubePlayer.loadVideo(videoId, 0);
                        }

                        @Override
                        public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState playerState) {
                            Log.d("TAG", "No such document");
                        }

                        @Override
                        public void onPlaybackQualityChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackQuality playbackQuality) {
                            Log.d("TAG", "No such document");
                        }

                        @Override
                        public void onPlaybackRateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackRate playbackRate) {
                            Log.d("TAG", "No such document");
                        }

                        @Override
                        public void onError(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerError playerError) {
                            Log.d("TAG", "No such document");
                        }

                        @Override
                        public void onCurrentSecond(@NonNull YouTubePlayer youTubePlayer, float v) {
                            Log.d("TAG", "No such document");
                        }

                        @Override
                        public void onVideoDuration(@NonNull YouTubePlayer youTubePlayer, float v) {
                            Log.d("TAG", "No such document");
                        }

                        @Override
                        public void onVideoLoadedFraction(@NonNull YouTubePlayer youTubePlayer, float v) {
                            Log.d("TAG", "No such document");
                        }

                        @Override
                        public void onVideoId(@NonNull YouTubePlayer youTubePlayer, @NonNull String s) {
                            Log.d("TAG", "No such document");
                        }

                        @Override
                        public void onApiChange(@NonNull YouTubePlayer youTubePlayer) {
                            Log.d("TAG", "No such document");
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
            Map<String, Object> progressMapNone = new HashMap<>();
            progressMapNone.put("completed",true);
            docRefPub.update(progressMapNone);
            builder.setMessage("Поздравляю вы закончили курс!");
        }
        else{
            builder.setMessage("Занятие закончилось");
        }
        builder.setPositiveButton("Завершить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map<String, Object> progressMapNone = new HashMap<>();
                String nextDayStr=Integer.toString(currentDayPub+1);
                if(Integer.parseInt(nextDayStr)==21){
                    nextDayStr="0";
                }
                progressMapNone.put("days",nextDayStr);
                docRefPub.update(progressMapNone);
                Intent intent = new Intent(NoneActivity.this, Home_page.class);
                startActivity(intent);
                NoneActivity.this.finish();
            }
        });
        builder.show();
    }
}
