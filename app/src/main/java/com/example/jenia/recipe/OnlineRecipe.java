package com.example.jenia.recipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class OnlineRecipe extends AppCompatActivity {

    private WebView domaskaWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_recipe);

        domaskaWebView = (WebView) findViewById(R.id.webViewDomashka);
        domaskaWebView.getSettings().setJavaScriptEnabled(true);
        domaskaWebView.loadUrl("http://www.povarenok.ru/");
        domaskaWebView.setWebViewClient(new MyWebViewClient());
    }
    private class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }
}
