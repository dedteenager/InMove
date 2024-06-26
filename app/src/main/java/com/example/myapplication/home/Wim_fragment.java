package com.example.myapplication.home;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.myapplication.DaysActivity.KhatkhaActivity;
import com.example.myapplication.DaysActivity.WimActivity;
import com.example.myapplication.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class Wim_fragment extends Fragment {
    Context context;
    TextView tbHello;
    private ImageView WimSwitch;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Wim_fragment.OnFragmentInteractionListener mListener;
    private List<String> dataList = new ArrayList<>();


    @SuppressLint("MissingInflatedId")
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userId);
        View view = inflater.inflate(R.layout._wim_home_fragment, container, false);
        context = view.getContext();
        WimSwitch=view.findViewById(R.id.imageView3);

       if(dataList.isEmpty()) {
            for (int i = 1; i <= 21; i++) {
                dataList.add("День " + i);
            }
        }
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter(dataList);
        recyclerView.setAdapter(adapter);

        int itemOffset = getResources().getDimensionPixelSize(R.dimen.item_offset);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(itemOffset));

        WimSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), WimSwitch);
                popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                mListener.onButtonClickedKhatkha();
                                return true;
                            case R.id.action_item2:
                                mListener.onButtonClickedWim();
                                return true;
                            case R.id.action_item3:
                                mListener.onButtonClickedNone();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });

        return view;

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }
    public interface OnFragmentInteractionListener {
        void onButtonClickedKhatkha();
        void onButtonClickedWim();
        void onButtonClickedNone();
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
            DocumentReference wim = db.collection("users").document(userId).collection("Progress").document("WimMethod");
            wim.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
                                        Intent intent = new Intent(getActivity(), WimActivity.class);
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

    public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
        private final int mItemOffset;

        public ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }
}