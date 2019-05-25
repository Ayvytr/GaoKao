package com.ayvytr.gaokao.markdown;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.ayvytr.customview.loading.StatusView;
import com.ayvytr.gaokao.R;
import com.ayvytr.gaokao.markdown.markedjs.JsInterface;
import com.ayvytr.network.ApiClient;
import okhttp3.*;

import java.io.IOException;

public class MarkedJs extends AppCompatActivity {

    private StatusView statusView;
    private WebView webview;

    private String url = "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/type.md";
    private JsInterface jsInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marked_js);
        initView();
        requestData();
    }

    private void requestData() {
        statusView.showLoading();

        CacheControl cc = new CacheControl.Builder().noCache().build();
        Request request = new Request.Builder().url(url).cacheControl(cc).build();
        ApiClient.getInstance().getOkHttpClient()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                statusView.showError(e.toString());
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        if (response.isSuccessful()) {
//                            Parser parser = Parser.builder().build();
//                            Node document = parser.parse(response.body().string());
//                            HtmlRenderer renderer = HtmlRenderer.builder().build();
//                            final String render = renderer.render(document);// "<p>This is <em>Sparta</em></p>\n"

                            final String string = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    statusView.showContent();

                                    jsInterface = new JsInterface(string);
                                    webview.addJavascriptInterface(jsInterface, "JsInterface");
                                    webview.loadUrl("file:///android_asset/1.html");

                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    statusView.showError(response.message());
                                }
                            });
                        }
                    }
                });
    }

    private void initView() {
        statusView = findViewById(R.id.status_view);
        webview = findViewById(R.id.webview);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
    }
}
