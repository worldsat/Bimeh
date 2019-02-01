package ir.asiabimeh.bimeh.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ir.asiabimeh.bimeh.Helper.getLoadBimeh;
import ir.asiabimeh.bimeh.Helper.getLoadKarmozd;
import ir.asiabimeh.bimeh.R;

public class KarmozdShowActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karmozd_show);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        initView();
        Toolbar();
        setVariable();


        getLoadKarmozd getLoadKarmozd = new getLoadKarmozd();
        getLoadKarmozd.get_banners(this, progressBar, recyclerView);


    }

    private void initView() {
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.View);

    }

    private void setVariable() {
    }

    private void Toolbar() {
        Toolbar my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);

        Bundle address = getIntent().getExtras();
        String str = "";
        if (address != null) {

            switch (address.getString("Activity", "")) {
                case "omr": {
                    str = "عمر";
                    break;
                }
                case "sales": {
                    str = "ثالث";
                    break;
                }
                case "badane": {
                    str = "بدنه";
                    break;
                }
                case "masoliat": {
                    str = "مسئولیت";
                    break;
                }
                case "atashsozi": {
                    str = "آتش سوزی";
                    break;
                }
            }
        }

        TextView titleActionBar = findViewById(R.id.title_toolbar);
        titleActionBar.setText("کارمزد های پرداخت شده");

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
        Intent intent = new Intent(KarmozdShowActivity.this, FinanceListActivity.class);
        startActivity(intent);
    }
}
