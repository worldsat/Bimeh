package ir.asiabimeh.bimeh.Activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ir.asiabimeh.bimeh.Helper.updateAcountBank;
import ir.asiabimeh.bimeh.R;

public class acountBanking extends AppCompatActivity {

    private EditText[] edt = new EditText[4];
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_banking);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        findViewById();
        connect();
        Toolbar();
    }


    private void findViewById() {

        edt[1] = findViewById(R.id.hesab);
        edt[2] = findViewById(R.id.sheba);
        edt[3] = findViewById(R.id.card);


        edt[2].setText("IR");
        Selection.setSelection(edt[2].getText(), edt[2].getText().length());


        edt[2].addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().startsWith("IR")) {
                    edt[2].setText("IR");
                    Selection.setSelection(edt[2].getText(), edt[2].getText().length());

                }

            }
        });

        edt[3].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (count <= edt[3].getText().toString().length()
                        && (edt[3].getText().toString().length() == 4
                        || edt[3].getText().toString().length() == 9
                        || edt[3].getText().toString().length() == 14)) {
                    edt[3].setText(edt[3].getText().toString() + " ");
                    int pos = edt[3].getText().length();
                    edt[3].setSelection(pos);
                } else if (count >= edt[3].getText().toString().length()
                        && (edt[3].getText().toString().length() == 4
                        || edt[3].getText().toString().length() == 9
                        || edt[3].getText().toString().length() == 14)) {
                    edt[3].setText(edt[3].getText().toString().substring(0, edt[3].getText().toString().length() - 1));
                    int pos = edt[3].getText().length();
                    edt[3].setSelection(pos);
                }
                count = edt[3].getText().toString().length();
            }
        });
        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAcountBank updateAccountBank = new updateAcountBank();
                updateAccountBank.connect(acountBanking.this, edt[1].getText().toString(), edt[2].getText().toString(), edt[3].getText().toString());
            }
        });
    }

    private void Toolbar() {
        Toolbar my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);

        TextView titleActionBar = findViewById(R.id.title_toolbar);
        titleActionBar.setText("حساب بانکی");

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

    private void connect() {


        String url = getApplicationContext().getString(R.string.LinkServer) + "api/Home/GetBankingAccount";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("moh3n", "onSuccessResponse: " + response);
                        try {
                            JSONObject jsonRootObject = new JSONObject(response).getJSONObject("Data");


                            EditText edt1 = findViewById(R.id.hesab);
                            EditText edt2 = findViewById(R.id.sheba);
                            EditText edt3 = findViewById(R.id.card);
                            edt1.setText(jsonRootObject.getString("HesabNumber"));
                            edt2.setText(jsonRootObject.getString("Shabanumber"));
                            edt3.setText(jsonRootObject.getString("CardNumber"));

                            TextView message = findViewById(R.id.textView7);
                            if (jsonRootObject.getString("EnumStatus").equals("1")) {
                                message.setVisibility(View.VISIBLE);
                            } else {
                                message.setVisibility(View.INVISIBLE);
                            }
                            Log.i("moh3n", jsonRootObject.getString("CardNumber"));

                        } catch (Exception e) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // wait.dismiss();
                        // Toast.makeText(context, "نام کاربری یا رمز عبور معتبر نمی باشد", Toast.LENGTH_LONG).show();
                        Log.i("mohsenjamali", "onErrorResponse: " + error.toString());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

//                params.put("UserNamee", username);
                SharedPreferences sp = getApplicationContext().getSharedPreferences("Token", 0);
                params.put("Api_Token", sp.getString("token", ""));

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
