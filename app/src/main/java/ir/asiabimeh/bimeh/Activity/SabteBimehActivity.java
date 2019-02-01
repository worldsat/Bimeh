package ir.asiabimeh.bimeh.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import ir.asiabimeh.bimeh.Helper.setBimeh;
import ir.asiabimeh.bimeh.R;

public class SabteBimehActivity extends AppCompatActivity {
    private EditText EditText1, EditText2, EditText3, EditText4, EditText6;
    private Button save;
    private String RadioPayment = "1", edt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sabte_bimeh);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Toolbar();
        initView();
        setVariable();
        send_radio();

    }

    private void initView() {
        EditText1 = findViewById(R.id.editTextsabt1);
        EditText2 = findViewById(R.id.editTextsabt2);
        EditText3 = findViewById(R.id.editTextsabt3);
        EditText4 = findViewById(R.id.editTextsabt4);

        EditText6 = findViewById(R.id.editTextsabt6);
        save = findViewById(R.id.save);
    }

    private void setVariable() {
        EditText4.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                EditText4.removeTextChangedListener(this);

//                try {
//                    String givenstring = s.toString();
//                    Long longval;
//                    if (givenstring.contains(",")) {
//                        givenstring = givenstring.replaceAll(",", "");
//                    }
//                    longval = Long.parseLong(givenstring);
//                    DecimalFormat formatter = new DecimalFormat("#,###,###");
//                    String formattedString = formatter.format(longval);
//                    EditText4.setText(formattedString);
//                    EditText4.setSelection(EditText4.getText().length());
//                    // to place the cursor at the end of text
//                } catch (NumberFormatException nfe) {
//                    nfe.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                EditText4.addTextChangedListener(this);

            }


        });



        final Bundle address = getIntent().getExtras();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("moh3n", "setVariable: "+edt4);
                if (EditText1.getText().toString().isEmpty()) {
                    Toast.makeText(SabteBimehActivity.this, "لطفا نام بیمه گذار را وارد نمائید", Toast.LENGTH_SHORT).show();
                } else if (EditText3.getText().toString().isEmpty()) {
                    Toast.makeText(SabteBimehActivity.this, "لطفا شناسه بیمه نامه را وارد نمائید", Toast.LENGTH_SHORT).show();

                } else if (EditText4.getText().toString().isEmpty()) {
                    Toast.makeText(SabteBimehActivity.this, "لطفا مبلغ حق بیمه را وارد نمائید", Toast.LENGTH_SHORT).show();

                } else {
                    if (address != null) {

                        edt4 = (EditText4.getText().toString()).replace(",", "");

                        setBimeh setBimeh = new setBimeh();
                        switch (address.getString("Button", "")) {
                            case "omr": {
                                setBimeh.connect(SabteBimehActivity.this, "omr", EditText1.getText().toString(), EditText2.getText().toString()
                                        , EditText3.getText().toString(), edt4, RadioPayment, EditText6.getText().toString());
                                break;
                            }
                            case "sales": {
                                setBimeh.connect(SabteBimehActivity.this, "sales", EditText1.getText().toString(), EditText2.getText().toString()
                                        , EditText3.getText().toString(), edt4, RadioPayment, EditText6.getText().toString());
                                break;
                            }
                            case "badane": {
                                setBimeh.connect(SabteBimehActivity.this, "badane", EditText1.getText().toString(), EditText2.getText().toString()
                                        , EditText3.getText().toString(), edt4, RadioPayment, EditText6.getText().toString());
                                break;
                            }
                            case "masoliat": {
                                setBimeh.connect(SabteBimehActivity.this, "masoliat", EditText1.getText().toString(), EditText2.getText().toString()
                                        , EditText3.getText().toString(), edt4, RadioPayment, EditText6.getText().toString());
                                break;
                            }
                            case "atashsozi": {

                                setBimeh.connect(SabteBimehActivity.this, "atashsozi", EditText1.getText().toString(), EditText2.getText().toString()
                                        , EditText3.getText().toString(), edt4, RadioPayment, EditText6.getText().toString());
                                break;
                            }
                        }
                    }
                }
            }
        });

        if (address.getString("Button", "").

                equals("omr"))

        {
            EditText2.setVisibility(View.VISIBLE);
            EditText6.setVisibility(View.VISIBLE);

        } else

        {

            EditText2.setVisibility(View.GONE);
            EditText6.setVisibility(View.GONE);
        }

    }

    private void Toolbar() {
        Toolbar my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);
        Bundle address = getIntent().getExtras();
        String str = "";
        if (address != null) {

            switch (address.getString("Button", "")) {
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
        titleActionBar.setText("ثبت بیمه نامه " + str);

        LinearLayout backIcon = findViewById(R.id.back_toolbar);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backMethod();

            }
        });

    }

    private void send_radio() {

        final RadioGroup send_radio = findViewById(R.id.RadioGroup1);

        send_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {

                switch (send_radio.getCheckedRadioButtonId()) {
                    case R.id.naghdi_radio:


                        RadioPayment = "1";
                        break;
                    case R.id.aghsati_radio:

                        RadioPayment = "2";
                        break;

                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        backMethod();
    }

    private void backMethod() {
        Intent intent = new Intent(SabteBimehActivity.this, BimehSaderehActivity.class);
        startActivity(intent);
    }

}
