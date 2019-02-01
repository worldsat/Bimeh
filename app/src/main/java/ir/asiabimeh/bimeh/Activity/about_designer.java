package ir.asiabimeh.bimeh.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.asiabimeh.bimeh.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class about_designer extends AppCompatActivity {
    private ImageView site;
    private ImageView call;
    private TextView title_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_designer);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        //toolbar
        Toolbar my_toolbar =  findViewById(R.id.app_bar);
        setSupportActionBar(my_toolbar);
        LinearLayout back_icon =  findViewById(R.id.back_toolbar);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(about_designer.this, MainActivity.class);
                about_designer.this.startActivity(intent);
            }
        });
        title_toolbar =  findViewById(R.id.title_toolbar);
        title_toolbar.setText("درباره طراح برنامه");

        //end toolbar

        testWebView();
        site =  findViewById(R.id.siteb);
        call = findViewById(R.id.call);
        site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "telegram.me/atrincom";

                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent telling = new Intent(Intent.ACTION_DIAL);
                telling.setData(Uri.parse("tel:09136569769"));
                about_designer.this.startActivity(telling);
            }

        });



    }

    private void testWebView() {
        ViewGroup book2;
        book2 = findViewById(R.id.textView22);
        final WebView webView = new WebView(this);
        book2.addView(webView);
        webView.loadUrl("file:///android_asset/about.html");
        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(about_designer.this, MainActivity.class);
        about_designer.this.startActivity(intent);

    }

}
