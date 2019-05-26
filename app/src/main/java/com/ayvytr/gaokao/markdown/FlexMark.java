package com.ayvytr.gaokao.markdown;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;
import com.ayvytr.customview.loading.StatusView;
import com.ayvytr.gaokao.R;
import com.ayvytr.network.ApiClient;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import okhttp3.*;

import java.io.IOException;

public class FlexMark extends AppCompatActivity {

    private StatusView statusView;
    private TextView tv;
    private String url = "https://raw.githubusercontent.com/Ayvytr/KnowledgeHierarchy/master/%E5%88%9D%E4%B8%AD/type.md";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_mark);

        initView();

        requestData();

        statusView.setOnStatusClickListener(new StatusView.OnStatusClickListener() {
            @Override
            public void onLoading(StatusView statusView) {

            }

            @Override
            public void onError(StatusView statusView) {
                requestData();
            }

            @Override
            public void onEmpty(StatusView statusView) {

            }
        });
    }

    private void requestData() {
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
                            Parser parser = Parser.builder().build();
                            Node document = parser.parse(response.body().string());
                            HtmlRenderer renderer = HtmlRenderer.builder().build();
                            final String render = renderer.render(document);// "<p>This is <em>Sparta</em></p>\n"

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv.setText(Html.fromHtml(render));
                                    statusView.showContent();
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
        tv = findViewById(R.id.tv_content);
    }
}
