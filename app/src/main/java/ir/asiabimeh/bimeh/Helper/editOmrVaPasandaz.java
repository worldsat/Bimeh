package ir.asiabimeh.bimeh.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
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

public class editOmrVaPasandaz {

    public void connect(final Context context, final String page, final String Id, final String ShenaseBimeName, final MaterialDialog alert, final ProgressBar wait) {

        //  progressBar.setVisibility(View.VISIBLE);
        String url = context.getString(R.string.LinkServer) + "api/Home/AddOmrAndPasandaz";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("mohsenjamali", "onSuccessResponse: " + response);
                        try {
                            JSONObject jsonObjectToken = new JSONObject(response);
                            switch (jsonObjectToken.getString("Message")) {
                                case "0":

                                    Toast.makeText(context, "ویرایش با موفقیت انجام شد", Toast.LENGTH_SHORT).show();


                                    Intent intent = new Intent(context, BimehListActivity.class);
                                    intent.putExtra("Activity", page);
                                    context.startActivity(intent);

                                    break;
                                case "1":

                                    SnakBar snakBar = new SnakBar();
                                    snakBar.snakShow(context, "در این وضعیت قادر به ویرایش نیستید");

                                    //progressBar.setVisibility(View.INVISIBLE);
                                    break;

                            }
                        } catch (Exception e) {

                        }
                        wait.setVisibility(View.GONE);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        wait.setVisibility(View.GONE);
                        alert.dismiss();
                        SnakBar snakBar = new SnakBar();
                        snakBar.snakShow(context, "خطا در انجام عملیات");
                        Log.i("mohsenjamali", "onErrorResponse: " + error);

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sp = context.getSharedPreferences("Token", 0);
                Map<String, String> params = new HashMap<>();
                params.put("ShenaseBimeNaame", ShenaseBimeName);
                params.put("ID", Id);
                params.put("Api_token", sp.getString("token", "nothing"));
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
