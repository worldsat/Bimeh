package ir.asiabimeh.bimeh.Helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ir.asiabimeh.bimeh.Activity.LoginActivity;
import ir.asiabimeh.bimeh.Activity.MainActivity;
import ir.asiabimeh.bimeh.Module.SnakBar.SnakBar;
import ir.asiabimeh.bimeh.R;

public class setSignUp {

    public void connect(final Context context, final String Mobile, final String ParentCodeMeli, final String codeMeli, final ProgressBar progressBar) {

        progressBar.setVisibility(View.VISIBLE);
        String url = context.getString(R.string.LinkServer) + "api/User/Register";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("moh3n", "onSuccessResponse: " + response);
                        try {

                            JSONObject jsonObjectToken = new JSONObject(response);
                            SnakBar snakBar = new SnakBar();

                            switch (jsonObjectToken.getString("Message")) {
                                case "0":

                                    snakBar.snakShow(context, "ثبت نام شما با موفقیت انجام شد،نام کاربری و رمز عبور برای شما ارسال خواهد شد");
                                    Toast.makeText(context, "ثبت نام شما با موفقیت انجام شد،نام کاربری و رمز عبور برای شما ارسال خواهد شد", Toast.LENGTH_LONG).show();
                                    context.startActivity(new Intent(context, LoginActivity.class));
                                    break;
                                case "1":

                                    snakBar.snakShow(context, "شماره همراه وارد شده تکراری است");

                                    break;
                                case "2":

                                    snakBar.snakShow(context, "کد معرف در بانک اطلاعاتی موجود نمی باشد");

                                    break;
                                case "3":

                                    snakBar.snakShow(context, "هر شخص حداکثر  4 زیر مجموعه می تواند داشته باشد");

                                    break;
                                case "4":

                                    snakBar.snakShow(context, "معرف قادر به اضافه کردن کاربر جدید نمی باشد");

                                    break;
                                case "5":

                                    snakBar.snakShow(context, "کد ملی شما با موفقیت ثبت گردید اما متاسفاه ارسال پیامک با مشکل مواجه شده است");

                                    break;
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                        } catch (Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // wait.dismiss();
                        // Toast.makeText(context, "نام کاربری یا رمز عبور معتبر نمی باشد", Toast.LENGTH_LONG).show();
                        Log.i("mohsenjamali", "onErrorResponse: " + error);
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("Mobile", Mobile);
                params.put("ParentCodeMeli", ParentCodeMeli);
                params.put("CodeMeli", codeMeli);
                Log.i("mohsenjamali", Mobile + " " + ParentCodeMeli + " " + codeMeli);
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
