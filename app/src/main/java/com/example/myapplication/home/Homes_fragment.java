package com.example.myapplication.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.SharedPreferences;
import com.example.myapplication.R;

import com.example.myapplication.stopwatch.stop_watch_page;


public class Homes_fragment extends Fragment {
    Button btn_yoga1,yoga2;
    TextView stop_watch;
    Context context;
    TextView tbHello;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_homes_fragment, container, false);
        context = view.getContext();
        tbHello = view.findViewById(R.id.tbHello);
        stop_watch = view.findViewById(R.id.stop_watch);
        stop_watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), stop_watch_page.class);
                startActivity(intent);
            }
        });
        return view;

    }


}