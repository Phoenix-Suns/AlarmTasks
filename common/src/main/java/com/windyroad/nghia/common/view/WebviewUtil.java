package com.windyroad.nghia.common.view;

import android.webkit.ValueCallback;
import android.webkit.WebView;

public class WebviewUtil {
    public static String getSelectText(WebView webView) {
        String js = "(function(){return window.getSelection().toString()})()";
        webView.evaluateJavascript(js, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                //return value.substring(1, value.lastIndexOf('"')).trim(); // Clean value, Delete ""
            }
        });
        return "";
    }
}
