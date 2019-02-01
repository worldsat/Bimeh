package ir.asiabimeh.bimeh.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ir.asiabimeh.bimeh.Helper.getLoadBimehOmr;
import ir.asiabimeh.bimeh.Helper.getLoadBimehOmrFiltered;
import ir.asiabimeh.bimeh.Helper.getLoadGetForoshGozashteh;
import ir.asiabimeh.bimeh.Helper.getLoadGetForoshGozashtehFiltered;
import ir.asiabimeh.bimeh.Helper.getLoadPadash;
import ir.asiabimeh.bimeh.R;

public class ForoshGozashteActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private TextView warning;
    private String str, Str = "";
    private Button SearchBtn;
    private EditText from, to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forosh_gozashteh);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        initView();
        Toolbar();
        setVariable();

        Bundle address = getIntent().getExtras();

        if (str.equals("عمر")) {
            getLoadBimehOmr getLoadBimehOmr = new getLoadBimehOmr();
            getLoadBimehOmr.get_banners(this, progressBar, recyclerView, "foroshGozashteh");
        } else {
            getLoadGetForoshGozashteh getLoadGetForoshGozashteh = new getLoadGetForoshGozashteh();
            getLoadGetForoshGozashteh.get_banners(this, progressBar, recyclerView, warning, Str);
        }


    }

    private void initView() {
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.View);
        warning = findViewById(R.id.warning);
        SearchBtn = findViewById(R.id.searchBtn);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);

    }

    private void setVariable() {

        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str.equals("عمر")) {
                    getLoadBimehOmrFiltered getLoadBimehOmr=new getLoadBimehOmrFiltered();
                    getLoadBimehOmr.get_banners(ForoshGozashteActivity.this,progressBar, recyclerView
                            , Str, from.getText().toString(), to.getText().toString());
                }else{
                    getLoadGetForoshGozashtehFiltered getLoad = new getLoadGetForoshGozashtehFiltered();
                    getLoad.get_banners(ForoshGozashteActivity.this, progressBar, recyclerView, warning
                            , Str, from.getText().toString(), to.getText().toString());
                }


            }
        });
    }

    private void Toolbar() {
        Toolbar my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);

        Bundle address = getIntent().getExtras();

        if (address != null) {
            Str = address.getString("Activity", "");
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
        finish();
    }
}

