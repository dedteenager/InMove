package com.example.myapplication.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CloseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finish();
    }
}
