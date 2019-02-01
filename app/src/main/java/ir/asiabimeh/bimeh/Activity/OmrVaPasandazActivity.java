package ir.asiabimeh.bimeh.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ir.asiabimeh.bimeh.R;

public class OmrVaPasandazActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omr_va_pasandaz);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
    }
}
