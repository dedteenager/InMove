package com.example.myapplication.home;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.SharedPreferences;

import com.example.myapplication.DaysActivity.KhatkhaActivity;
import com.example.myapplication.R;

import com.example.myapplication.stopwatch.stop_watch_page;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Homes_fragment extends Fragment {
    Button btn_yoga1,yoga2;
    TextView stop_watch;
    Context context;
    private TextView currentTV;
    TextView tbHello;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<String> dataList = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userId);
        View view = inflater.inflate(R.layout.fragment_homes_fragment, container, false);
        context = view.getContext();
       tbHello = view.findViewById(R.id.tbHello);

        for (int i = 1; i <= 30; i++) {
            dataList.add("День " + i);
        }
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter(dataList);
        recyclerView.setAdapter(adapter);

       //currentTV = view.findViewById(R.id.currentTV);

       // SimpleDateFormat sdf = new SimpleDateFormat("'Сегодня 'dd-MM-yyyy'");
       // String currentDateAndTime = sdf.format(new Date());
       // currentTV.setText(currentDateAndTime);
        /// stop_watch = view.findViewById(R.id.stop_watch);
       /// stop_watch.setOnClickListener(new View.OnClickListener() {
      ///      @Override
       ///     public void onClick(View view) {
        ///        Intent intent = new Intent(context.getApplicationContext(), stop_watch_page.class);
       ///         startActivity(intent);
       ///     }
      ///  });
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                       tbHello.setText("Здравствуй, "+document.getString("nick"));

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

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<String> dataList;

        public MyAdapter(List<String> dataList) {
            this.dataList = dataList;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;
            public Button button;

            public MyViewHolder(View view) {
                super(view);
                textView = view.findViewById(R.id.textView);
                button = view.findViewById(R.id.button);
            }
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new MyViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String userId = user.getUid();
            DocumentReference khatkha = db.collection("users").document(userId).collection("Progress").document("Khatkha");
            khatkha.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            holder.textView.setText(dataList.get(position));
                            int currentDay = 0;
                            currentDay = Integer.parseInt(document.getString("days"));
                            if (position == currentDay) {
                                holder.button.setVisibility(View.VISIBLE);
                                holder.button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getActivity(), KhatkhaActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                holder.button.setVisibility(View.GONE);
                            }
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });


        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }


}