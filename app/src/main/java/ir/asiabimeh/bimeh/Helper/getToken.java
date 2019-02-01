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

import ir.asiabimeh.bimeh.Activity.MainActivity;
import ir.asiabimeh.bimeh.Module.SnakBar.SnakBar;
import ir.asiabimeh.bimeh.R;

public class getToken {

    public void connect(final Context context, final String username, final String password, final ProgressBar progressBar) {

        progressBar.setVisibility(View.VISIBLE);

        String url = context.getString(R.string.LinkServer) + "api/User/Login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("mohsenjamali", "onSuccessResponse: " + response);
                        try {
                            JSONObject jsonObjectToken = new JSONObject(response);
                            switch (jsonObjectToken.getString("Message")) {
                                case "0":


                                    SharedPreferences sp2 = context.getSharedPreferences("profile", 0);
                                    SharedPreferences sp3 = context.getSharedPreferences("picture", 0);
                                    sp2.edit().clear().apply();
                                    sp3.edit().clear().apply();


                                    SharedPreferences sp = context.getSharedPreferences("Token", 0);
                                    sp.edit().putString("token", jsonObjectToken.getString("Api_Token")).apply();


                                    getProfileStatus getProfileStatus = new getProfileStatus();
                                    getProfileStatus.connect(context);

                                    break;
                                case "1":

                                    SnakBar snakBar = new SnakBar();
                                    snakBar.snakShow(context, context.getString(R.string.userPassError));

                                    progressBar.setVisibility(View.INVISIBLE);
                                    break;

                            }
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

                params.put("UserNamee", username);
                params.put("Password", password);

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


    public void ClearToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("Token", 0);
        sp.edit().putString("token", "nothing").apply();


        Toast.makeText(context, "خروج از حساب کاربری", Toast.LENGTH_SHORT).show();
    }


    public Boolean Ok(Context context) {
        SharedPreferences sp = context.getSharedPreferences("Token", 0);
        return !sp.getString("token", "nothing").equals("nothing");
    }
}
