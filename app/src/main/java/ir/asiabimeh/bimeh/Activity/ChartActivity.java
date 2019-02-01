package ir.asiabimeh.bimeh.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ir.asiabimeh.bimeh.R;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        Toolbar();
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        ViewGroup book2;
        book2 = findViewById(R.id.textView22);
        final WebView webView = new WebView(this);
        book2.addView(webView);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Token", 0);
        webView.loadUrl("http://portal.padidehasia.ir/Home/APIChart?Api_Token=" + sp.getString("token", "nothing"));
        //  webView.loadUrl("http://google.com");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {

            }
        });
    }

    private void Toolbar() {
        Toolbar my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);

        TextView titleActionBar = findViewById(R.id.title_toolbar);
        titleActionBar.setText("چارت سازمانی");

        LinearLayout backIcon = findViewById(R.id.back_toolbar);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


    }
}