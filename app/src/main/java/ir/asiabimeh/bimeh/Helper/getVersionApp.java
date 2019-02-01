package ir.asiabimeh.bimeh.Helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

import ir.asiabimeh.bimeh.Activity.DownloadAppActivity;
import ir.asiabimeh.bimeh.Activity.FirstActivity;
import ir.asiabimeh.bimeh.Activity.MainActivity;
import ir.asiabimeh.bimeh.Activity.SetProfileActivity;
import ir.asiabimeh.bimeh.Activity.SplashActivity;
import ir.asiabimeh.bimeh.BuildConfig;
import ir.asiabimeh.bimeh.R;

public class getVersionApp {
    private int versionCode;

    public void connect(final Context context) {

        String url = context.getString(R.string.LinkServer) + "api/Home/GetAndroidVersion";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("moh3n", "onSuccessResponse: " + response);
                        try {
                            JSONObject jsonRootObject = new JSONObject(response).getJSONObject("Data");


//                            JSONObject jsonRootObject = new JSONObject(response);
//                            JSONArray array = jsonRootObject.optJSONArray("Data");
//
//
//
//
//                                JSONObject person = array.getJSONObject(0);
                            //   PackageInfo pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                            //  int versionNumber = pinfo.versionCode;
                            String str2=jsonRootObject.getString("Version");
                            int version = Integer.valueOf(str2);
                            //  int versionCode = BuildConfig.VERSION_CODE;
//                            String versionName = context.getPackageManager()
//                                    .getPackageInfo(context.getPackageName(), 0).versionName;
//                            int versionCode=Integer.valueOf(versionName);
                            String Code = "0";
                            int verCode = 0;
//                            try {
//                                PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
//                                 verCode = pInfo.versionCode;
//                            } catch (PackageManager.NameNotFoundException e) {
//                                e.printStackTrace();
//                            }
                            PackageManager manager = context.getPackageManager();
                            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
                            verCode = info.versionCode;
                           // Toast.makeText(context, "" + verCode, Toast.LENGTH_SHORT).show();
                            //  String versionName = BuildConfig.VERSION_NAME;
                            versionCode = verCode;
                            if (versionCode < version) {
                                Intent intent = new Intent(context, DownloadAppActivity.class);
                                String str = context.getString(R.string.LinkServer) + "Upload/AndroidApps/" + jsonRootObject.getString("AppAndroidUrl");

                                intent.putExtra("address", str);
                                context.startActivity(intent);
                            } else {

                                SharedPreferences sp = context.getSharedPreferences("Token", 0);
                                String token = sp.getString("token", "");
                                if(token.equals("")){
                                    Intent intent = new Intent(context, FirstActivity.class);
                                    context.startActivity(intent);
                                }else{
                                    Intent intent = new Intent(context, MainActivity.class);
                                    context.startActivity(intent);
                                }

                            }

                        } catch (Exception e) {
                            Log.i("moh3n", "onResponse: " + e);
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
               // params.put("Api_token", sp.getString("token", ""));

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
