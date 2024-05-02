package com.example.myapplication.DaysActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class KnowledgePageActivity extends AppCompatActivity {
    public TextView contentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._knowledge_page);
        Bundle arguments = getIntent().getExtras();
        WebView browser=findViewById(R.id.webview);
        browser.loadUrl(arguments.get("content").toString());
        contentView=findViewById(R.id.contentView);
        contentView.setText(arguments.get("content").toString());
    }}
