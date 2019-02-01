package ir.asiabimeh.bimeh.Activity;

import android.content.Entity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ir.asiabimeh.bimeh.Activity.MenuFragment.Activity1;
import ir.asiabimeh.bimeh.Activity.MenuFragment.Activity2;
import ir.asiabimeh.bimeh.Activity.MenuFragment.Activity3;
import ir.asiabimeh.bimeh.Fragment.NavigationDrawerFragment;
import ir.asiabimeh.bimeh.R;

public class MainActivity extends AppCompatActivity {
    private Toolbar my_toolbar;
    private ConstraintLayout profle, education, finance, sabteBimeh, estelam, chart;
    private ImageView exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Toolbar();
        initView();
        NavigationDrawer();
        setVariable();

    }

    private void Toolbar() {
        Toolbar my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);


        LinearLayout backIcon = findViewById(R.id.about_toolbar);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);

            }
        });

    }

    private void initView() {
        my_toolbar = findViewById(R.id.toolbar);
        profle = findViewById(R.id.profile);
        estelam = findViewById(R.id.estelam);
        sabteBimeh = findViewById(R.id.sabt_bimeh);
        education = findViewById(R.id.education);
        finance = findViewById(R.id.finance);
        chart = findViewById(R.id.chart);
        exitBtn = findViewById(R.id.exit);
    }

    private void setVariable() {
        profle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        estelam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EstelamActivity.class);
                startActivity(intent);
            }
        });
        sabteBimeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BimehSaderehActivity.class);
                startActivity(intent);
            }
        });
        finance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FinanceListActivity.class);
                startActivity(intent);
            }
        });
        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EducationActivity.class);
                startActivity(intent);
            }
        });
        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChartActivity.class);
                startActivity(intent);
            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getApplicationContext().getSharedPreferences("Token", 0);
                sp.edit().clear().apply();

                Toast.makeText(MainActivity.this, "با موفقیت از حساب کاربری خود خارج شدید", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

    }

    public void NavigationDrawer() {


        //navigation drawer
        NavigationDrawerFragment my_nav = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        my_nav.setUp(R.id.fragment_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), my_toolbar);
        //end navigation drawer
    }

    public void onbtn1(View v) {
        startActivity(new Intent(MainActivity.this, Activity1.class));

    }

    public void onbtn2(View v) {
        startActivity(new Intent(MainActivity.this, Activity2.class));

    }

    public void onbtn3(View v) {
        startActivity(new Intent(MainActivity.this, Activity3.class));

    }

    public void onbtn4(View v) {
        startActivity(new Intent(MainActivity.this, about_designer.class));

    }

    public void onbtn5(View v) {
        startActivity(new Intent(MainActivity.this, about_designer.class));

    }

    public void onbtn6(View v) {
        startActivity(new Intent(MainActivity.this, about_designer.class));

    }

    public void onbtn7(View v) {
        startActivity(new Intent(MainActivity.this, about_designer.class));

    }

    public void onbtn8(View v) {
        startActivity(new Intent(MainActivity.this, about_designer.class));

    }

    public void onbtn9(View v) {
        startActivity(new Intent(MainActivity.this, about_designer.class));

    }

    public void onbtn10(View v) {

        Intent intent = new Intent(MainActivity.this, about_designer.class);
        MainActivity.this.startActivity(intent);

    }
}
