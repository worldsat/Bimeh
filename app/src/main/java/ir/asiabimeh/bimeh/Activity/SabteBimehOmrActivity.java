package ir.asiabimeh.bimeh.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ir.asiabimeh.bimeh.Helper.getLoadTarikhBimehOmr;
import ir.asiabimeh.bimeh.Helper.setBimeh;
import ir.asiabimeh.bimeh.Helper.setOmrVaPasandaz;
import ir.asiabimeh.bimeh.R;

public class SabteBimehOmrActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText EditText1, EditText2, EditText3, EditText4, EditText6;
    private Button save;
    private String RadioPayment = "1";
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private String spinner;
    private String edt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sabte_bimeh_omr);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Toolbar();
        initView();
        setVariable();
        spinner_category();

    }

    private void initView() {
        EditText1 = findViewById(R.id.editTextsabt1);
        EditText2 = findViewById(R.id.editTextsabt2);
        EditText3 = findViewById(R.id.editTextsabt3);
        EditText4 = findViewById(R.id.editTextsabt4);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar2);

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

                try {
                    String givenstring = s.toString();
                    Long longval;
                    if (givenstring.contains(",")) {
                        givenstring = givenstring.replaceAll(",", "");
                    }
                    longval = Long.parseLong(givenstring);
                    DecimalFormat formatter = new DecimalFormat("#,###,###");
                    String formattedString = formatter.format(longval);
                    EditText4.setText(formattedString);
                    EditText4.setSelection(EditText4.getText().length());
                    // to place the cursor at the end of text
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                EditText4.addTextChangedListener(this);

            }


        });

        final Bundle address = getIntent().getExtras();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (EditText1.getText().toString().isEmpty()) {
                    Toast.makeText(SabteBimehOmrActivity.this, "لطفا نام بیمه گذار را وارد نمائید", Toast.LENGTH_SHORT).show();
                } else if (EditText3.getText().toString().isEmpty()) {
                    Toast.makeText(SabteBimehOmrActivity.this, "لطفا شناسه بیمه نامه را وارد نمائید", Toast.LENGTH_SHORT).show();

                } else if (EditText4.getText().toString().isEmpty()) {
                    Toast.makeText(SabteBimehOmrActivity.this, "لطفا مبلغ حق بیمه را وارد نمائید", Toast.LENGTH_SHORT).show();

                } else {
                    if (address != null) {

                        edt4 = (EditText4.getText().toString()).replace(",", "");

                        SharedPreferences sp = getApplicationContext().getSharedPreferences("mablagh", 0);
                        int array = Integer.valueOf(sp.getString("tedad", "0"));

                        String Str = "";
                        for (int i = 0; i < (array); i++) {

                            String tarikh = sp.getString("tarikh" + i, "");
                            if (!tarikh.equals("")) {
                                Str += tarikh + ",";
                            }
                            String mablagh = sp.getString("pardakhti" + i, "");
                            if (!mablagh.equals("")) {
                                if (mablagh.equals("true")) {
                                    Str += "T";
                                } else if (mablagh.equals("false")) {
                                    Str += "F";
                                }
                            }

                            if (i != (array - 1)) Str += ",";

                        }
                        Log.i("moh3n", "onClick: " + Str + " " + spinner);
                        setOmrVaPasandaz setOmrVaPasandaz = new setOmrVaPasandaz();
                        setOmrVaPasandaz.connect(SabteBimehOmrActivity.this, Str, EditText1.getText().toString(), EditText2.getText().toString(), EditText3.getText().toString(), EditText4.getText().toString(), spinner);
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

            str = "عمر";


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

    public void spinner_category() {

        /**---Spinner-----------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

        final Spinner anva_melk = findViewById(R.id.Type);
        // Spinner click listener
        anva_melk.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("انتخاب کنید");
        categories.add("دو ماهه");
        categories.add("سه ماهه");
        categories.add("شش ماهه");
        categories.add("یکساله");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(SabteBimehOmrActivity.this, R.layout.spinner_item, categories);

        dataAdapter.setDropDownViewResource(R.layout.spinner_item);

        anva_melk.setAdapter(dataAdapter);


        //   spinner = IdItems.get(anva_melk.getSelectedItemPosition());
    }

    @Override
    public void onBackPressed() {
        backMethod();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        if (position != 0) {
            getLoadTarikhBimehOmr getLoadTarikhBimehOmr = new getLoadTarikhBimehOmr();
            getLoadTarikhBimehOmr.get_banners(SabteBimehOmrActivity.this, progressBar, recyclerView, String.valueOf(position));

        }
        spinner = String.valueOf(position);
        // Showing selected spinner item
        // Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private void backMethod() {
        Intent intent = new Intent(SabteBimehOmrActivity.this, BimehSaderehActivity.class);
        startActivity(intent);
    }
}
