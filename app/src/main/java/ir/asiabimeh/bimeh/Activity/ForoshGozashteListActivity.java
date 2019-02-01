package ir.asiabimeh.bimeh.Activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.asiabimeh.bimeh.R;

public class ForoshGozashteListActivity extends AppCompatActivity {

    private ConstraintLayout button1, button2, button3, button4, button5;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forosh_gozashte_list);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Toolbar();
        initView();
        setVariable();

    }

    private void initView() {
        button1 = findViewById(R.id.constraintLayout2);
        button2 = findViewById(R.id.constraintLayout8);
        button3 = findViewById(R.id.constraintLayout3);
        button4 = findViewById(R.id.constraintLayout4);
        button5 = findViewById(R.id.acount);


    }

    private void setVariable() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForoshGozashteListActivity.this, ForoshGozashteActivity.class);
                intent.putExtra("Activity", "omr");
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForoshGozashteListActivity.this, ForoshGozashteActivity.class);
                intent.putExtra("Activity", "sales");
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForoshGozashteListActivity.this, ForoshGozashteActivity.class);
                intent.putExtra("Activity", "badane");
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForoshGozashteListActivity.this, ForoshGozashteActivity.class);
                intent.putExtra("Activity", "masoliat");
                startActivity(intent);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForoshGozashteListActivity.this, ForoshGozashteActivity.class);
                intent.putExtra("Activity", "atashsozi");
                startActivity(intent);
            }
        });
    }

    private void Toolbar() {
        Toolbar my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);

        TextView titleActionBar = findViewById(R.id.title_toolbar);
        titleActionBar.setText("فروش ماه های گذشته");

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
        Intent intent = new Intent(ForoshGozashteListActivity.this, FinanceListActivity.class);
        startActivity(intent);

    }
}
