package com.example.myapplication.more;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.myapplication.R;

public class Calendar_page extends AppCompatActivity {
    TextView back;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_page);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String myurl = "file:///android_asset/calender_page.html";
        WebView view = (WebView) this.findViewById(R.id.webview);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(myurl);
    }
}