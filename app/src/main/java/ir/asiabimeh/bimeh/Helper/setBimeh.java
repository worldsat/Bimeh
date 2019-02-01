package ir.asiabimeh.bimeh.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import ir.asiabimeh.bimeh.Activity.BimehListActivity;
import ir.asiabimeh.bimeh.Activity.MainActivity;
import ir.asiabimeh.bimeh.Activity.SabteBimehActivity;
import ir.asiabimeh.bimeh.Module.SnakBar.SnakBar;
import ir.asiabimeh.bimeh.R;

public class setBimeh {

    public void connect(final Context context, final String page, final String NameBimehGozar, final String NameBimehShode, final String ShenaseBimeName, final String MablaghHagheBime, final String ravesh_pardakht, final String mablagh_vosoli) {
        String Str = "";
        switch (page) {
            case "omr":
                Str = "api/Home/AddAtashsoozi";
                break;
            case "sales":
                Str = "api/Home/AddSsales";
                break;
            case "badane":
                Str = "api/Home/AddBadane";
                break;
            case "masoliat":
                Str = "api/Home/AddMasooliyat";
                break;
            case "atashsozi":
                Str = "api/Home/AddAtashsoozi";
                break;

        }
        String url = context.getString(R.string.LinkServer) + Str;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("mohsenjamali", "onSuccessResponse: " + response);
                        try {
                            JSONObject jsonObjectToken = new JSONObject(response);
                            switch (jsonObjectToken.getString("Message")) {
                                case "0":

                                    Toast.makeText(context, "ثبت با موفقیت انجام شد", Toast.LENGTH_SHORT).show();


                                    Intent intent = new Intent(context, BimehListActivity.class);
                                    intent.putExtra("Activity", page);
                                    context.startActivity(intent);

                                    break;
                                case "1":

                                    SnakBar snakBar = new SnakBar();
                                    snakBar.snakShow(context, context.getString(R.string.userPassError));

                                    //progressBar.setVisibility(View.INVISIBLE);
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
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                SharedPreferences sp = context.getSharedPreferences("Token", 0);
//                String token = sp.getString("token", " ");
//                //Log.i("moh3n", "getHeaders: "+token);
//                if (token != null) {
//                    params.put("Api_Token", token);
//                }
//                return params;
//            }

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                SharedPreferences sp = context.getSharedPreferences("Token", 0);
                params.put("NameBimeGozar", NameBimehGozar);
                params.put("ShenaseBimeNaame", ShenaseBimeName);
                params.put("MablaghHagheBime", MablaghHagheBime);
                params.put("RaveshPardakhtAllBime", ravesh_pardakht);
                params.put("Api_token", sp.getString("token", "nothing"));
                Log.i("mohsenjamali", ravesh_pardakht + "   " + NameBimehGozar + " " + ShenaseBimeName + " " + MablaghHagheBime);
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
