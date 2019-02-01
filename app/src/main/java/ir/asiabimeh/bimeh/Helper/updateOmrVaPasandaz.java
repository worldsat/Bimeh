package ir.asiabimeh.bimeh.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

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
import ir.asiabimeh.bimeh.Module.SnakBar.SnakBar;
import ir.asiabimeh.bimeh.R;

public class updateOmrVaPasandaz {

    public void connect(final Context context,final String id, final String mablagh, final String NameBimehGozar, final String NameBimehShode, final String ShenaseBimeName, final String MablaghGharardad, final String RaveshPardakht) {

        //  progressBar.setVisibility(View.VISIBLE);
        String url = context.getString(R.string.LinkServer) + "api/Home/AddOmrAndPasandaz";
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

                                    snakBar.snakShow(context, "ذخیره با موفقیت انجام شد");

                                    ((Activity) context).startActivity(new Intent(context, BimehListActivity.class));
                                    Intent intent = new Intent(context, BimehListActivity.class);
                                    intent.putExtra("Activity", "omr");
                                    context.startActivity(intent);

                                    break;
                                case "1":

                                    snakBar.snakShow(context, "قادر به ویرایش نمی باشید");

                                    break;

                            }
                            // progressBar.setVisibility(View.INVISIBLE);
                        } catch (Exception e) {
                            // progressBar.setVisibility(View.INVISIBLE);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // wait.dismiss();
                        // Toast.makeText(context, "نام کاربری یا رمز عبور معتبر نمی باشد", Toast.LENGTH_LONG).show();
                        Log.i("mohsenjamali", "onErrorResponse: " + error);
                        // progressBar.setVisibility(View.INVISIBLE);

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sp = context.getSharedPreferences("Token", 0);
                Map<String, String> params = new HashMap<>();
                params.put("MablaghVosool", mablagh);
                params.put("NameBimeGozar", NameBimehGozar);

                params.put("NameBimeShode", NameBimehShode);
                Log.i("moh3n", "getParams: " + NameBimehGozar + " " + NameBimehShode);
                params.put("ShenaseBimeNaame", ShenaseBimeName);
                params.put("MablaghGharardad", MablaghGharardad);
                params.put("RaveshPardakht", RaveshPardakht);
                params.put("Api_Token", sp.getString("token", ""));
                params.put("ID", id);
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
