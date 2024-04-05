package com.example.myapplication.DaysActivity;

import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.home.Home_page;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WimActivity extends AppCompatActivity {
    private Button btnNext;
    public int currentDayPub;
    public  DocumentReference docRefPub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._everyday_activity);
        btnNext=findViewById(R.id.btnNext);
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userId).collection("Progress").document("WimMethod");
        docRefPub =docRef;


        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                    int currentDay = Integer.parseInt(documentSnapshot.getString("days"));
                    currentDayPub=currentDay;
                    String[] daysVideos=new String[30];

                    daysVideos[0]="8kSjeuBVqjs";
                    daysVideos[1]="sQRfp2Xp6U0";
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
                    daysVideos[21]="H2I6V0NlaHg";
                    daysVideos[22]="fx7tkHLD3RY";
                    daysVideos[23]="TJ_7-n02nEg";
                    daysVideos[24]="JesWs7SALfQ";
                    daysVideos[25]="vBCNlxFTkJk";
                    daysVideos[26]="c0-hvjV2A5Y";
                    daysVideos[27]="hb0XLX0b4Y4";
                    daysVideos[28]="H2I6V0NlaHg";
                    daysVideos[29]="fx7tkHLD3RY";
                    youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            String videoId = daysVideos[currentDay];
                            youTubePlayer.loadVideo(videoId, 0);
                        }
                    });
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

        if (currentDayPub>29){

            builder.setMessage("Поздравляю вы закончили курс!");
        }
        else{
            builder.setMessage("Тренировка закончилась");
        }
        builder.setPositiveButton("Завершить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map<String, Object> progressMapWimMethod = new HashMap<>();
                String nextDayStr=Integer.toString(currentDayPub+1);
                if(Integer.parseInt(nextDayStr)==30){
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
