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

import java.util.HashMap;
import java.util.Map;

import ir.asiabimeh.bimeh.Activity.MainActivity;
import ir.asiabimeh.bimeh.R;

public class setProfile {

    public void connect(final Context context, final ProgressBar progressBar) {

progressBar.setVisibility(View.VISIBLE);
        String url = context.getString(R.string.LinkServer) + "api/User/Update";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("moh3n", "onSuccessResponse: " + response);
                        try {


                            if (response.equals("{\"Message\":0}")) {

                                Toast.makeText(context, "تکمیل اطلاعات انجام شد", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(context, MainActivity.class);
                                context.startActivity(intent);

                            } else if (response.equals("{\"Message\":5}")) {

                                Toast.makeText(context, "خطا در انجام عملیات", Toast.LENGTH_LONG).show();

                            } else if (response.equals("{\"Message\":4}")) {

                                Toast.makeText(context, "تلفن همراه تکراری است", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {

                        }
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // wait.dismiss();
                        // Toast.makeText(context, "نام کاربری یا رمز عبور معتبر نمی باشد", Toast.LENGTH_LONG).show();
                        Log.i("mohsenjamali", "onErrorResponse: " + error);
                        progressBar.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();


                SharedPreferences sp = context.getSharedPreferences("Token", 0);
                SharedPreferences sp2 = context.getSharedPreferences("profile", 0);
                SharedPreferences sp3 = context.getSharedPreferences("picture", 0);

                params.put("Mobile", sp2.getString("mobile", ""));
                params.put("Address", sp2.getString("address", ""));
                params.put("Name", sp2.getString("name", ""));
                params.put("Family", sp2.getString("family", ""));
                params.put("CodePosti", sp2.getString("postal", ""));
                params.put("CodeMeli", sp2.getString("melli", ""));
                params.put("BirthDateDay", sp2.getString("day", ""));
                params.put("BirthDateYear", sp2.getString("year", ""));
                params.put("BirthDateMonth", sp2.getString("month", ""));
                params.put("Phone", sp2.getString("tell", ""));
                params.put("ShomareShenasname", sp2.getString("shenasname", ""));
                params.put("ImageUpload", sp3.getString("pic1", ""));
                params.put("ShenasnameUpload", sp3.getString("pic2", ""));
                params.put("CartMeliUpload", sp3.getString("pic3", ""));
                params.put("Api_Token", sp.getString("token", ""));

                Log.i("moh3n", "getParams:ImageUpload "+ sp3.getString("pic1", ""));
                Log.i("moh3n", "getParams:ShenasnameUpload "+ sp3.getString("pic2", ""));
                Log.i("moh3n", "getParams:CartMeliUpload "+ sp3.getString("pic3", ""));
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
