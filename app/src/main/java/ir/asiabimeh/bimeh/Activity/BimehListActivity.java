package ir.asiabimeh.bimeh.Activity;

import android.app.Activity;
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
import android.widget.Toast;

import ir.asiabimeh.bimeh.Helper.getLoadBimeh;
import ir.asiabimeh.bimeh.Helper.getLoadBimehOmr;
import ir.asiabimeh.bimeh.R;

public class BimehListActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private Button newBimeh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bimeh_show);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        initView();
        Toolbar();
        setVariable();

        Bundle address = getIntent().getExtras();
        if (address != null) {

            String Activity = address.getString("Activity", "");
            if (Activity.equals("omr")) {
                getLoadBimehOmr getLoadBimehOmr = new getLoadBimehOmr();
                getLoadBimehOmr.get_banners(this, progressBar, recyclerView, Activity);
            } else {
                getLoadBimeh getLoadBimeh = new getLoadBimeh();
                getLoadBimeh.get_banners(new getLoadBimeh.ondata() {
                    @Override
                    public void data(String res) {
                     //   Toast.makeText(BimehListActivity.this, res, Toast.LENGTH_SHORT).show();
                    }
                }, this, progressBar, recyclerView, Activity);
            }
        }


    }

    private void initView() {
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.View);
        newBimeh = findViewById(R.id.NewBimehBtn);
    }

    private void setVariable() {
        newBimeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle address = getIntent().getExtras();
                if (address != null) {

                    String Activity = address.getString("Activity", "");
                    if (Activity.equals("omr")) {
                        Intent intent = new Intent(BimehListActivity.this, SabteBimehOmrActivity.class);
                        intent.putExtra("Button", address.getString("Activity", ""));
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(BimehListActivity.this, SabteBimehActivity.class);
                        intent.putExtra("Button", address.getString("Activity", ""));
                        startActivity(intent);
                    }
                }
            }
        });
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
        titleActionBar.setText(str);

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
        Intent intent = new Intent(BimehListActivity.this, BimehSaderehActivity.class);
        startActivity(intent);
    }
}
