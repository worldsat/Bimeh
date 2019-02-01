package ir.asiabimeh.bimeh.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ir.asiabimeh.bimeh.Helper.updateProfile;
import ir.asiabimeh.bimeh.R;

public class SetProfileActivity extends AppCompatActivity {
    EditText[] EditText = new EditText[12];
    ImageView[] imageViews = new ImageView[5];
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_set);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Toolbar();
        findViewById();
       // connect(this);
        SetVariable();
    }


    private void findViewById() {
        EditText[1] = findViewById(R.id.editTextProfile1);
        EditText[2] = findViewById(R.id.editTextProfile2);
        EditText[3] = findViewById(R.id.editTextProfile3);
        EditText[4] = findViewById(R.id.editTextProfile4);
        EditText[5] = findViewById(R.id.editTextProfile5);
        EditText[6] = findViewById(R.id.editTextProfile6);
        EditText[7] = findViewById(R.id.editTextProfile7);
        EditText[8] = findViewById(R.id.editTextProfile8);
        EditText[9] = findViewById(R.id.editTextProfile9);
        EditText[10] = findViewById(R.id.editTextProfile10);
        EditText[11] = findViewById(R.id.editTextProfile11);


        save = findViewById(R.id.save);
    }

    private void SetVariable() {
        SharedPreferences pic_reader = getApplicationContext().getSharedPreferences("picture", 0);
        pic_reader.edit().clear().apply();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EditText[1].getText().toString().isEmpty()) {
                    Toast.makeText(SetProfileActivity.this, "لطفا نام را وارد نمائید", Toast.LENGTH_SHORT).show();
                } else if (EditText[2].getText().toString().isEmpty()) {
                    Toast.makeText(SetProfileActivity.this, "لطفا نام خانوادگی را وارد نمائید", Toast.LENGTH_SHORT).show();
                } else if (EditText[3].getText().toString().isEmpty()) {
                    Toast.makeText(SetProfileActivity.this, "لطفا کد ملی را وارد نمائید", Toast.LENGTH_SHORT).show();
                } else if (EditText[4].getText().toString().isEmpty()) {
                    Toast.makeText(SetProfileActivity.this, "لطفا تاریخ تولد را وارد نمائید", Toast.LENGTH_SHORT).show();
                } else if (EditText[5].getText().toString().isEmpty()) {
                    Toast.makeText(SetProfileActivity.this, "لطفا شماره شناسنامه را وارد نمائید", Toast.LENGTH_SHORT).show();
                } else if (EditText[6].getText().toString().isEmpty()) {
                    Toast.makeText(SetProfileActivity.this, "لطفا آدرس را وارد نمائید", Toast.LENGTH_SHORT).show();
                } else if (EditText[7].getText().toString().isEmpty()) {
                    Toast.makeText(SetProfileActivity.this, "لطفا کد پستی را وارد نمائید", Toast.LENGTH_SHORT).show();
                } else if (EditText[8].getText().toString().isEmpty()) {
                    Toast.makeText(SetProfileActivity.this, "لطفا تلفن ثابت را وارد نمائید", Toast.LENGTH_SHORT).show();
                } else if (EditText[9].getText().toString().isEmpty()) {
                    Toast.makeText(SetProfileActivity.this, "لطفا تلفن همراه را وارد نمائید", Toast.LENGTH_SHORT).show();
                } else if (EditText[10].getText().toString().isEmpty()) {
                    Toast.makeText(SetProfileActivity.this, "لطفا روز تولد خود را وارد نمائید", Toast.LENGTH_SHORT).show();
                } else if (EditText[11].getText().toString().isEmpty()) {
                    Toast.makeText(SetProfileActivity.this, "لطفا ماه تولد را وارد نمائید", Toast.LENGTH_SHORT).show();
                } else {

                    SharedPreferences sp = getApplicationContext().getSharedPreferences("profile", 0);
                    sp.edit().putString("name", EditText[1].getText().toString())
                            .putString("family", EditText[2].getText().toString())
                            .putString("melli", EditText[3].getText().toString())
                            .putString("year", EditText[4].getText().toString())
                            .putString("month", EditText[10].getText().toString())
                            .putString("day", EditText[11].getText().toString())
                            .putString("shenasname", EditText[5].getText().toString())
                            .putString("address", EditText[6].getText().toString())
                            .putString("postal", EditText[7].getText().toString())
                            .putString("tell", EditText[8].getText().toString())
                            .putString("mobile", EditText[9].getText().toString()).apply();

                    startActivity(new Intent(SetProfileActivity.this, setProfile2Activity.class));
                }


            }
        });
    }

    private void Toolbar() {
        Toolbar my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);

        TextView titleActionBar = findViewById(R.id.title_toolbar);
        titleActionBar.setText("تکمیل مشخصات");

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
        Intent intent = new Intent(SetProfileActivity.this, FirstActivity.class);
        startActivity(intent);
    }

    private void connect(final Context context) {


        String url = context.getString(R.string.LinkServer) + "api/User/GetProfile";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("moh3n", "onSuccessResponse: " + response);
                        try {
                            JSONObject jsonRootObject = new JSONObject(response).getJSONObject("Data");

                            EditText[1].setText(jsonRootObject.getString("Name"));
                            EditText[2].setText(jsonRootObject.getString("Family"));
                            EditText[3].setText(jsonRootObject.getString("CodeMeli"));
                            EditText[4].setText(jsonRootObject.getString("BirthDateYear") + "/" + jsonRootObject.getString("BirthDateMonth") + "/" + jsonRootObject.getString("BirthDateDay"));
                            EditText[5].setText(jsonRootObject.getString("ShomareShenasname"));
                            EditText[6].setText(jsonRootObject.getString("Address"));
                            EditText[7].setText(jsonRootObject.getString("CodePosti"));
                            EditText[8].setText(jsonRootObject.getString("Phone"));
                            EditText[9].setText(jsonRootObject.getString("Mobile"));


                            // glide
                            RequestOptions requestOptions = new RequestOptions();
                            //  requestOptions = requestOptions
                            //.transforms(new CenterCrop(), new RoundedCorners(8))
                            //   .error(R.mipmap.error)
                            // .override(250, 250)
                            //  .placeholder(R.mipmap.error);

                            Glide.with(context)
                                    .load(context.getString(R.string.site) + jsonRootObject.getString("CartMeliUpload"))
                                    .apply(requestOptions)
                                    .into(imageViews[1]);
                            Glide.with(context)
                                    .load(context.getString(R.string.site) + jsonRootObject.getString("ShenasnameUpload"))
                                    .apply(requestOptions)
                                    .into(imageViews[2]);
                            Glide.with(context)
                                    .load(context.getString(R.string.site) + jsonRootObject.getString("ImageUpload"))
                                    .apply(requestOptions)
                                    .into(imageViews[3]);
                            Glide.with(context)
                                    .load(context.getString(R.string.site) + jsonRootObject.getString("ImageTaahod"))
                                    .apply(requestOptions)
                                    .into(imageViews[4]);
                            //end glide

                        } catch (Exception e) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // wait.dismiss();
                        // Toast.makeText(context, "نام کاربری یا رمز عبور معتبر نمی باشد", Toast.LENGTH_LONG).show();
                        Log.i("mohsenjamali", "onErrorResponse: " + error);

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                SharedPreferences sp = context.getSharedPreferences("Token", 0);
//                params.put("UserNamee", username);
                params.put("Api_Token", sp.getString("token", ""));

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}
