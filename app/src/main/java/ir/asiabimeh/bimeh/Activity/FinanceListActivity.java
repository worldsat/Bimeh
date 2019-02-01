package ir.asiabimeh.bimeh.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.asiabimeh.bimeh.R;

public class FinanceListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_list);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Toolbar();
        ConstraintLayout acount = findViewById(R.id.acount);
        ConstraintLayout karmozd = findViewById(R.id.karmozd);
        ConstraintLayout padash = findViewById(R.id.padash);
        ConstraintLayout forosh_gozashte = findViewById(R.id.forosh_gozashte);
        acount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinanceListActivity.this, acountBanking.class));
            }
        });
        karmozd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinanceListActivity.this, KarmozdShowActivity.class));
            }
        });
        padash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinanceListActivity.this,PadashActivity.class));
            }
        });
        forosh_gozashte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinanceListActivity.this,ForoshGozashteListActivity.class));
            }
        });
    }

    private void Toolbar() {
        Toolbar my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);

        TextView titleActionBar = findViewById(R.id.title_toolbar);
        titleActionBar.setText("امور مالی");

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
        Intent intent = new Intent(FinanceListActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
