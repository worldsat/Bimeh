package ir.asiabimeh.bimeh.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.asiabimeh.bimeh.R;

public class EstelamActivity extends AppCompatActivity {

    private ConstraintLayout[] btn = new ConstraintLayout[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estelam);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        initView();
        Toolbar();
        setVariable();
    }

    private void initView() {
        btn[1] = findViewById(R.id.constraintLayout2);
        btn[2] = findViewById(R.id.constraintLayout3);
        btn[3] = findViewById(R.id.constraintLayout4);
        btn[4] = findViewById(R.id.forosh_gozashte);
    }

    private void setVariable() {
        btn[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "telegram.me/padidehasia";

                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        btn[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "telegram.me/padidehasia";

                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        btn[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "telegram.me/padidehasia";

                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        btn[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "telegram.me/padidehasia";

                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void Toolbar() {
        Toolbar my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);

        TextView titleActionBar = findViewById(R.id.title_toolbar);
        titleActionBar.setText("استعلام");

        LinearLayout backIcon = findViewById(R.id.back_toolbar);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backMethod();

            }
        });

    }

    @Override
    public void onBackPressed() {
        backMethod();
    }

    private void backMethod() {
        Intent intent = new Intent(EstelamActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
