package ir.asiabimeh.bimeh.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ir.asiabimeh.bimeh.Helper.getToken;
import ir.asiabimeh.bimeh.Module.SnakBar.SnakBar;
import ir.asiabimeh.bimeh.R;

public class LoginActivity extends AppCompatActivity {
    private Button Login;
    private EditText UserEdt, PasswordEdt;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        initView();
        Toolbar();
        setVariable();

    }

    private void initView() {
        UserEdt = findViewById(R.id.hesab);
        PasswordEdt = findViewById(R.id.sheba);
        Login = findViewById(R.id.save);
        progressBar = findViewById(R.id.progressBarLogin);
    }

    private void setVariable() {
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnakBar snakBar = new SnakBar();
                if (UserEdt.getText().toString().isEmpty()) {

                    snakBar.snakShow(LoginActivity.this, getString(R.string.userError));

                } else if (PasswordEdt.getText().toString().isEmpty()) {

                    snakBar.snakShow(LoginActivity.this, getString(R.string.passError));

                } else {

                    String user = UserEdt.getText().toString();
                    String password = PasswordEdt.getText().toString();

                    getToken getToken = new getToken();
                    getToken.connect(LoginActivity.this, user, password, progressBar);

                }
            }
        });
    }

    private void Toolbar() {
        android.support.v7.widget.Toolbar my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);

        TextView titleActionBar = findViewById(R.id.title_toolbar);
        titleActionBar.setText("ورود");

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
        Intent intent = new Intent(LoginActivity.this, FirstActivity.class);
        startActivity(intent);
    }
}
