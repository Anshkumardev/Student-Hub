package com.akd.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class BooksActivity extends AppCompatActivity {

    ImageView back;
    ProgressBar loader;
    WebView booksWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        back = findViewById(R.id.go_back_books);
        booksWebView = findViewById(R.id.booksWebView);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loader = findViewById(R.id.webViewLoader);
        loader.setVisibility(View.VISIBLE);

        String url = "https://easyengineering.net/";

        booksWebView.getSettings().setDomStorageEnabled(true);
        booksWebView.getSettings().setJavaScriptEnabled(true);

        booksWebView.setWebViewClient(new WebViewClient() {

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
        booksWebView.loadUrl(url);

    }
}