package com.akd.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class BlogsActivity extends AppCompatActivity {

    WebView blogsWebView;
    ProgressBar loader;
    ImageView goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogs);

        blogsWebView = findViewById(R.id.blogWebView);
        goBack = findViewById(R.id.go_back);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        loader = findViewById(R.id.webViewLoader);
        loader.setVisibility(View.VISIBLE);


        String url = "https://akdblogs.online/category/lifestyle/";

        blogsWebView.getSettings().setDomStorageEnabled(true);
        blogsWebView.getSettings().setJavaScriptEnabled(true);

        blogsWebView.setWebViewClient(new WebViewClient() {
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
        blogsWebView.loadUrl(url);

    }
}