package com.example.myapplication.home;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Tracker_fragment extends Fragment {

    private LinearLayout daysLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tracker, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        DocumentReference progressRef = db.collection("users").document(userId)
                .collection("Progress").document("WimMethod");
        progressRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        daysLayout = view.findViewById(R.id.daysLayout);

                        int totalDays = 21;
                        int currentDay = 0;
                        currentDay = Integer.parseInt(document.getString("days"));
                        for (int i = 1; i <= totalDays; i++) {
                            View circle = new View(getContext());
                            int size = 30;
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
                            params.setMargins(5, 0, 5, 0);
                            circle.setLayoutParams(params);

                            if (i <= currentDay) {
                                circle.setBackgroundResource(R.drawable.circle_filled);
                            } else {
                                circle.setBackgroundResource(R.drawable.circle_empty);
                            }

                            daysLayout.addView(circle);
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