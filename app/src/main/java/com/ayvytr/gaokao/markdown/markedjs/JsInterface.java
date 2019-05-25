package com.ayvytr.gaokao.markdown.markedjs;

import android.webkit.JavascriptInterface;

public class JsInterface {

    private String content;

    public JsInterface(String content) {
        this.content = content;
    }


    @JavascriptInterface
    public String getContent() {
        return content;
    }
}
 

