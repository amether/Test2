package com.example.testapp2;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {
    public static String lastUrl;
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        lastUrl = request.getUrl().toString();
        MainActivity.setLoadUrl(lastUrl);
        Uri myUri = Uri.parse(lastUrl);
        String fullPath = Uri.parse(lastUrl).getPath();
        String[] fullPathArray;
        String delim = "/";
        fullPathArray = fullPath.split(delim);
        boolean isMapPartConsist = false;
        for (String s : fullPathArray) {
            if (s.equals("maps")) {
                isMapPartConsist = true;
                break;
            }
        }

        boolean isWeatherPartConsist = false;
        for (String s : fullPathArray) {
            if (s.equals("pogoda") || s.equals("weather")) {
                isWeatherPartConsist = true;
                break;
            }
        }

        if (Uri.parse(request.getUrl().toString()).getHost().equals("maps.yandex.ru")
                || isMapPartConsist) {
            try {
                Uri intentUri = Uri.parse("yandexmaps:" + myUri.getHost() + myUri.getPath());
                Intent intent = new Intent(Intent.ACTION_VIEW, intentUri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            } catch (ActivityNotFoundException E) {
                view.loadUrl(request.getUrl().toString());
            }

        } else {
            if (Uri.parse(request.getUrl().toString()).getHost().equals("pogoda.yandex.ru")
                    || isWeatherPartConsist) {
                try {
                    Uri intentUri = Uri.parse("yandexweather:" + myUri.getHost() + myUri.getPath());
                    Intent intent = new Intent(Intent.ACTION_VIEW, intentUri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(intent);
                } catch (ActivityNotFoundException E){
                    view.loadUrl(request.getUrl().toString());
                }

            } else {
                view.loadUrl(request.getUrl().toString());
            }
        }

        return true;
    }

}
