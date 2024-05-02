package com.example.myapplication.home;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import com.example.myapplication.DaysActivity.KnowledgePageActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.DaysActivity.KhatkhaActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.myapplication.R;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

public class Knowledges_fragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    List<String> articleList = new ArrayList<>();
    List<String> documentIdsList = new ArrayList<>();
    List<String> imageList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.knowledges_fragment, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("articles");
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String documentId = document.getId();
                        documentIdsList.add(documentId);
                    }

                    if(imageList.isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String image     = document.getString("image");
                            if (image != null) {
                                imageList.add(image);
                            }
                        }
                    }
                    if(articleList.isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String article = document.getString("title");
                            if (article != null) {
                                articleList.add(article);
                            }
                        }
                    }
                    Log.d("Firestore", "Number of documents in collection: " + imageList.size());

                    recyclerView = view.findViewById(R.id.recyclerView);
                    recyclerView.setHasFixedSize(true);

                    layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);

                    adapter = new Knowledges_fragment.MyAdapter(articleList);
                    recyclerView.setAdapter(adapter);

                    int itemOffset = getResources().getDimensionPixelSize(R.dimen.item_offset);
                    recyclerView.addItemDecoration(new Knowledges_fragment.ItemOffsetDecoration(itemOffset));

                } else {
                    Log.e("Firestore", "Error getting documents: ", task.getException());
                }
            }
        });
        return view;
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
    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<String> articleList;

        public MyAdapter(List<String> dataList) {
            this.articleList = dataList;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;
            public ImageView imageView;

            public MyViewHolder(View view) {
                super(view);
                textView = view.findViewById(R.id.textView8);
                imageView = view.findViewById(R.id.imageView6);
            }
        }

        @NonNull
        @Override
        public Knowledges_fragment.MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout._knowledges_item_layout, parent, false);
            return new Knowledges_fragment.MyAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Knowledges_fragment.MyAdapter.MyViewHolder holder, int position) {
            String title = articleList.get(position);
            holder.textView.setText(title);

            String imageUrl =  imageList.get(position);
            Picasso.get().load(imageUrl).into(holder.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // При нажатии на элемент списка
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    // Получаем данные из Firestore для выбранного документа
                    String selectedDocumentId = documentIdsList.get(position);

                            DocumentReference docRef = db.collection("articles").document(selectedDocumentId);
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                String content = documentSnapshot.getString("content");
                                // Открываем новый макет "_knowledge_page"
                                Intent intent = new Intent(getActivity(), KnowledgePageActivity.class);
                                intent.putExtra("content", content);
                                getActivity().startActivity(intent);
                            }
                        }
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            return articleList.size();
        }
    }

}