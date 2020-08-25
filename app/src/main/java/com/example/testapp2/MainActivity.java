package com.example.testapp2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    public static final String APP_PREFERENCES = "mySettings";
    public static final String APP_PREFERENCES_LAST_SITE = "lastSite";
    private SharedPreferences settings;
    private static String loadUrl;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        loadUrl = "https://yandex.ru/";
        if (settings.contains(APP_PREFERENCES_LAST_SITE)) {
            loadUrl = settings.getString(APP_PREFERENCES_LAST_SITE,"https://yandex.ru/");
        }
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        CookieManager.getInstance().setAcceptCookie(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.loadUrl(loadUrl);
        webView.setWebViewClient(new MyWebViewClient());
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            FragmentManager manager = getSupportFragmentManager();
            MyDialogFragment myDialogFragment = new MyDialogFragment();
            myDialogFragment.show(manager, "myDialog");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(APP_PREFERENCES_LAST_SITE, loadUrl);
        editor.apply();
    }

    public static void setLoadUrl(String lastUrl){
        loadUrl = lastUrl;
    }
}