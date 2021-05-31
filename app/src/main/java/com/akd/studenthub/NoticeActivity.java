package com.akd.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class NoticeActivity extends AppCompatActivity {

    WebView NoticeWebView;
    ProgressBar loader;
    ImageView goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        goBack = findViewById(R.id.go_back_notice);
        NoticeWebView = findViewById(R.id.noticeWebView);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loader = findViewById(R.id.webViewLoader);
        loader.setVisibility(View.VISIBLE);


        String url = "https://drive.google.com/drive/folders/1-6AOdsM8lmOH1cYKqrVjg-04yQUxs-Wn?usp=sharing";

        NoticeWebView.getSettings().setDomStorageEnabled(true);
        NoticeWebView.getSettings().setJavaScriptEnabled(true);

        NoticeWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loader.setVisibility(View.VISIBLE);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loader.setVisibility(View.GONE);
            }
        });
        NoticeWebView.loadUrl(url);

    }
}