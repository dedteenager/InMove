package com.example.myapplication.home;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.DaysActivity.KhatkhaActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Tracker_fragment extends Fragment {
    private ImageView TrophyOne, TrophyTwo,TrophyThree,TrophyFour,TrophyFive,TrophySix;
    private LinearLayout hofLayout;
    private LinearLayout detkaLayout;
    private LinearLayout khatkhaLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout._tracker, container, false);

        TrophyOne=view.findViewById(R.id.TrophyOne);
        TrophyTwo=view.findViewById(R.id.TrophyTwo);
        TrophyThree=view.findViewById(R.id.TrophyThree);
        TrophyFour=view.findViewById(R.id.TrophyFour);
        TrophyFive=view.findViewById(R.id.TrophyFive);
        TrophySix=view.findViewById(R.id.TrophySix);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        DocumentReference userActive = db.collection("users").document(userId);
        DocumentReference wim = db.collection("users").document(userId).collection("Progress").document("WimMethod");
        DocumentReference khatkha = db.collection("users").document(userId).collection("Progress").document("Khatkha");
        DocumentReference detka = db.collection("users").document(userId).collection("Progress").document("Detka");


        TrophyOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Адепт")
                        .setMessage("Начните пользоваться приложением!")
                        .setIcon(R.drawable.icon)
                        .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });
        TrophyTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Хатха-йог!")
                        .setMessage("Закончите курс Хатха-йоги!")
                        .setIcon(R.drawable.icon)
                        .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });
        TrophyThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Спокойствие, только спокойствие...")
                        .setMessage("Закончите курс Управления стрессом")
                        .setIcon(R.drawable.icon)
                        .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });
        TrophyFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Биомашина")
                        .setMessage("Закончите курс Табата!")
                        .setIcon(R.drawable.icon)
                        .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });
        TrophyFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Меценат")
                        .setMessage("Сделайте донат!")
                        .setIcon(R.drawable.icon)
                        .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });
        TrophySix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Эксперт")
                        .setMessage("Отправьте отзыв на почту Xetyq@ya.ru. Укажите ник!")
                        .setIcon(R.drawable.icon)
                        .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });
        userActive.get() .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            boolean booleanActive = documentSnapshot.getBoolean("status");
                            if (booleanActive) {
                                TrophyOne.setImageResource(R.drawable.trophey);
                            } else {
                                TrophyOne.setImageResource(R.drawable.trophy_chb);
                            }
                            boolean booleanDonate = documentSnapshot.getBoolean("donate");
                            if (booleanDonate) {
                                TrophyFive.setImageResource(R.drawable.trophey);
                            } else {
                                TrophyFive.setImageResource(R.drawable.trophy_chb);
                            }
                            boolean booleanOtzovik = documentSnapshot.getBoolean("otzovik");
                            if (booleanOtzovik) {
                                TrophySix.setImageResource(R.drawable.trophey);
                            } else {
                                TrophySix.setImageResource(R.drawable.trophy_chb);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error getting document", e);
                    }
                });
        khatkha.get() .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            boolean booleanValue = documentSnapshot.getBoolean("completed");

                            if (booleanValue) {
                                TrophyTwo.setImageResource(R.drawable.trophey);
                            } else {
                                TrophyTwo.setImageResource(R.drawable.trophy_chb);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error getting document", e);
                    }
                });

        wim.get() .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            boolean booleanValue = documentSnapshot.getBoolean("completed");

                            if (booleanValue) {
                                TrophyThree.setImageResource(R.drawable.trophey);
                            } else {
                                TrophyThree.setImageResource(R.drawable.trophy_chb);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error getting document", e);
                    }
                });

        detka.get() .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            boolean booleanValue = documentSnapshot.getBoolean("completed");

                            if (booleanValue) {
                                TrophyFour.setImageResource(R.drawable.trophey);
                            } else {
                                TrophyFour.setImageResource(R.drawable.trophy_chb);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error getting document", e);
                    }
                });
        wim.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        hofLayout = view.findViewById(R.id.hofLayout);

                        int totalDays = 21;
                        int currentDay = 0;
                        currentDay = Integer.parseInt(document.getString("days"));
                        for (int i = 1; i <= totalDays; i++) {
                            View circle = new View(getContext());
                            int size = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics()));
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
                            params.setMargins((int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics())), 0, (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2 , getResources().getDisplayMetrics())), 0);
                            circle.setLayoutParams(params);

                            if (i <= currentDay) {
                                circle.setBackgroundResource(R.drawable.circle_filled_orange);
                            } else {
                                circle.setBackgroundResource(R.drawable.circle_empty);
                            }

                            hofLayout.addView(circle);
                        }

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        khatkha.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        khatkhaLayout = view.findViewById(R.id.khatkhaLayout);

                        int totalDays = 21;
                        int currentDay = 0;
                        currentDay = Integer.parseInt(document.getString("days"));
                        for (int i = 1; i <= totalDays; i++) {
                            View circle = new View(getContext());
                            int size = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics()));
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
                            params.setMargins((int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics())), 0, (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2 , getResources().getDisplayMetrics())), 0);
                            circle.setLayoutParams(params);
                            if (i <= currentDay) {
                                circle.setBackgroundResource(R.drawable.circle_filled_green);
                            } else {
                                circle.setBackgroundResource(R.drawable.circle_empty);
                            }

                            khatkhaLayout.addView(circle);
                        }

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        detka.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        detkaLayout = view.findViewById(R.id.detkaLayout);

                        int totalDays = 21;
                        int currentDay = 0;
                        currentDay = Integer.parseInt(document.getString("days"));
                        for (int i = 1; i <= totalDays; i++) {
                            View circle = new View(getContext());
                            int size = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics()));
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
                            params.setMargins((int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics())), 0, (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2 , getResources().getDisplayMetrics())), 0);
                            circle.setLayoutParams(params);

                            if (i <= currentDay) {
                                circle.setBackgroundResource(R.drawable.circle_filled_blue);
                            } else {
                                circle.setBackgroundResource(R.drawable.circle_empty);
                            }

                            detkaLayout.addView(circle);
                        }

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        return view;
    }
}