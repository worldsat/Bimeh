package ir.asiabimeh.bimeh.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.asiabimeh.bimeh.Adatper.getListAmozeshAdapter;
import ir.asiabimeh.bimeh.Adatper.getListBimehAdapter;
import ir.asiabimeh.bimeh.OtherClass.MiladiToShamsi;
import ir.asiabimeh.bimeh.OtherClass.OnLoadMoreListener;
import ir.asiabimeh.bimeh.R;


public class getLoadAmozesh {

    private static List<String> Level = new ArrayList<>();
    private static List<String> Name = new ArrayList<>();
    private static List<String> IdItems = new ArrayList<>();
    private static List<String> FileName = new ArrayList<>();


    private getListAmozeshAdapter ad;

    private int pageTotal = 0;
    private int page = 1;

    public void get_banners(final Context context, final ProgressBar ProgressBar, final RecyclerView recyclerViewlist) {

//        InternetActivity internetCheck = new InternetActivity();
//        internetCheck.CheckNet(context);

        SharedPreferences sp = context.getSharedPreferences("Token", 0);


        final String urlJsonArray = context.getString(R.string.LinkServer) + "/api/Home/Education?Api_token=c275255b072480425c0b5690cccda1620341";

        recyclerViewlist.setVisibility(View.INVISIBLE);
        ProgressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlJsonArray,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            recyclerViewlist.setVisibility(View.VISIBLE);


                            Level.clear();
                            Name.clear();
                            IdItems.clear();
                            FileName.clear();


                            JSONObject jsonRootObject = new JSONObject(response);
                            JSONArray array = jsonRootObject.optJSONArray("Data");


                            for (int i = 0; i < array.length(); i++) {

                                JSONObject person = array.getJSONObject(i);

                                Level.add(person.getString("Level"));
                                Name.add(  person.getString("Name"));
                                IdItems.add(person.getString("ID"));
                                FileName.add(person.getString("FileName"));



                            }
                            try {

                                recyclerViewlist.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


                                ad = new getListAmozeshAdapter(context, Level, Name, IdItems, FileName, recyclerViewlist);
                                recyclerViewlist.setAdapter(ad);

                            } catch (Exception e) {
                                Toast.makeText(context, "خطایی پیش آمده است لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                                Log.i("mohsenjamali", "onResponseErrorProduct: " + e);
                            }

                            recyclerViewlist.setVisibility(View.VISIBLE);
                            ProgressBar.setVisibility(View.GONE);



                        } catch (JSONException e) {
                            e.printStackTrace();

                            ProgressBar.setVisibility(View.GONE);
                            recyclerViewlist.setVisibility(View.GONE);

                            Log.i("mohsenjamali", "onResponseErrorProduct: " + e.getMessage());
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ProgressBar.setVisibility(View.GONE);
                recyclerViewlist.setVisibility(View.GONE);

                Log.i("mohsenjamali", "onResponseErrorProduct: " + error.getMessage());

            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<>();
                SharedPreferences sp = context.getSharedPreferences("Token", 0);

                MyData.put("Api_token", sp.getString("token", "nothing"));
                return MyData;
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
