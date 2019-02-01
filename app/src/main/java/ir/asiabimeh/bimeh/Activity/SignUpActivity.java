package ir.asiabimeh.bimeh.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ir.asiabimeh.bimeh.Helper.getToken;
import ir.asiabimeh.bimeh.Helper.setSignUp;
import ir.asiabimeh.bimeh.Module.SnakBar.SnakBar;
import ir.asiabimeh.bimeh.R;

public class SignUpActivity extends AppCompatActivity {
    private EditText mobile, codeMeli, codeMeliParent;
    private Button Signup;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        initView();
        setVariable();
        Toolbar();
    }

    private void initView() {
        mobile = findViewById(R.id.editTextSign2);
        codeMeli = findViewById(R.id.editTextSign1);
        codeMeliParent = findViewById(R.id.editTextSign3);
        Signup = findViewById(R.id.signBtn);
        progressBar = findViewById(R.id.progressBarSignUp);
    }

    private void setVariable() {
        codeMeliParent.requestFocus();
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SnakBar snakBar = new SnakBar();
                if (mobile.getText().toString().isEmpty()) {

                    snakBar.snakShow(SignUpActivity.this, getString(R.string.noMobile));

                } else if (codeMeliParent.getText().toString().isEmpty()) {

                    snakBar.snakShow(SignUpActivity.this, getString(R.string.noCodemelliParent));
                } else if (codeMeli.getText().toString().isEmpty()) {

                    snakBar.snakShow(SignUpActivity.this, getString(R.string.noCodemelli));
                } else {

                    setSignUp setSignUp = new setSignUp();
                    setSignUp.connect(SignUpActivity.this, mobile.getText().toString(), codeMeliParent.getText().toString(), codeMeli.getText().toString(), progressBar);

                }
            }
        });

    }

    private void Toolbar() {
        android.support.v7.widget.Toolbar my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);

        TextView titleActionBar = findViewById(R.id.title_toolbar);
        titleActionBar.setText("ثبت نام");

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
        Intent intent = new Intent(SignUpActivity.this, FirstActivity.class);
        startActivity(intent);
    }
}
