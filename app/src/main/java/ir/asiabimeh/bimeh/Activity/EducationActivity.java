package ir.asiabimeh.bimeh.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ir.asiabimeh.bimeh.Helper.getLoadAmozesh;
import ir.asiabimeh.bimeh.Helper.getLoadBimeh;
import ir.asiabimeh.bimeh.R;

public class EducationActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Toolbar();
        initView();


        getLoadAmozesh getLoadAmozesh = new getLoadAmozesh();
        getLoadAmozesh.get_banners(this, progressBar, recyclerView);
    }

    private void initView() {
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.View);

    }

    private void Toolbar() {
        Toolbar my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);

        TextView titleActionBar = findViewById(R.id.title_toolbar);
        titleActionBar.setText("آموزش");

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
        Intent intent = new Intent(EducationActivity.this, MainActivity.class);
        startActivity(intent);
    }


}
