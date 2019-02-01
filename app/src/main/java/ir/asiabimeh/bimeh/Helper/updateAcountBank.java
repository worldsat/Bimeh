package ir.asiabimeh.bimeh.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.asiabimeh.bimeh.Module.SnakBar.SnakBar;
import ir.asiabimeh.bimeh.R;

public class updateAcountBank {
    private static List<String> Id = new ArrayList<>();


    public void connect(final Context context, final String HesabNumber, final String Shabanumber, final String CardNumber) {


        String url = context.getString(R.string.LinkServer) + "api/Home/UpdateBankingAccount";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("moh3n", "onSuccessResponse: " + response);
                        try {

                            JSONObject jsonRootObject = new JSONObject(response);
//
                            SnakBar snakBar = new SnakBar();
                            switch (jsonRootObject.getString("Message")) {
                                case "0":

                                    snakBar.snakShow(context, "ویرایش با موفقیت انجام شد");
                                    break;
                                case "1":

                                    snakBar.snakShow(context, "کارت وارد شده مربوط به بانک ملت نمی باشد");
                                    break;
                                case "2":
                                    snakBar.snakShow(context, "ویرایش با موفقیت انجام شد");
                                    break;
                            }
//                            JSONObject person = array.getJSONObject(0);


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

                params.put("HesabNumber", HesabNumber);
                params.put("Shabanumber", Shabanumber);
                params.put("CardNumber", CardNumber);

                SharedPreferences sp = context.getSharedPreferences("Token", 0);
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
