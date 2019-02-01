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

import ir.asiabimeh.bimeh.Adatper.getListBimehAdapter;
import ir.asiabimeh.bimeh.Adatper.getListKarmozdAdapter;
import ir.asiabimeh.bimeh.OtherClass.MiladiToShamsi;
import ir.asiabimeh.bimeh.OtherClass.OnLoadMoreListener;
import ir.asiabimeh.bimeh.R;


public class getLoadKarmozd {

    private static List<String> NameBimeGozar = new ArrayList<>();
    private static List<String> Percent = new ArrayList<>();
    private static List<String> MablaghKarmozd = new ArrayList<>();
    private static List<String> PayPDate = new ArrayList<>();
    private static List<String> MablaghHagheBime = new ArrayList<>();
    private static List<String> IsPay = new ArrayList<>();
    private static List<String> BimeKind = new ArrayList<>();
    private String Str = "";

    private getListKarmozdAdapter ad;

    private int pageTotal = 0;
    private int page = 1;

    public void get_banners(final Context context, final ProgressBar ProgressBar, final RecyclerView recyclerViewlist) {

//        InternetActivity internetCheck = new InternetActivity();
//        internetCheck.CheckNet(context);



        final String urlJsonArray = context.getString(R.string.LinkServer) + "api/Home/LoadKarmozdData";

        recyclerViewlist.setVisibility(View.INVISIBLE);
        ProgressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlJsonArray,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            recyclerViewlist.setVisibility(View.VISIBLE);

                            Log.i("moh3n", "onResponse: "+response);
                            NameBimeGozar.clear();
                            Percent.clear();
                            MablaghKarmozd.clear();
                            PayPDate.clear();
                            MablaghHagheBime.clear();
                            IsPay.clear();
                            BimeKind.clear();


                            JSONObject jsonRootObject = new JSONObject(response);
                            JSONArray array = jsonRootObject.optJSONArray("Data");


                            pageTotal = jsonRootObject.getInt("TotalItemCount") / 5;

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject person = array.getJSONObject(i);

                                NameBimeGozar.add(person.getString("NameBimeGozar"));
                                Percent.add(person.getString("Percent"));
                                MablaghKarmozd.add(person.getString("MablaghKarmozd"));
                                MablaghHagheBime.add(person.getString("MablaghHagheBime"));
                                PayPDate.add(person.getString("PayPDate"));
                                IsPay.add(person.getString("IsPay"));
                                BimeKind.add(person.getString("BimeKind"));


                            }
                            try {

                                recyclerViewlist.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


                                ad = new getListKarmozdAdapter(context, BimeKind, NameBimeGozar, Percent, MablaghKarmozd, PayPDate, MablaghHagheBime, IsPay, recyclerViewlist);
                                recyclerViewlist.setAdapter(ad);

                            } catch (Exception e) {
                                Toast.makeText(context, "خطایی پیش آمده است لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                                Log.i("mohsenjamali", "onResponseErrorProduct: " + e);
                            }

                            recyclerViewlist.setVisibility(View.VISIBLE);
                            ProgressBar.setVisibility(View.GONE);

                            ad.setOnLoadMoreListener(new OnLoadMoreListener() {
                                @Override
                                public void onLoadMore() {
                                    MablaghKarmozd.add(null);
                                    ad.notifyItemInserted(MablaghKarmozd.size() - 1);

                                    //-----------------------------------------------------------------------------------------------
                                    RequestQueue MyRequestQueue = Volley.newRequestQueue(context);
                                    StringRequest MyStringRequest = new StringRequest(Request.Method.POST, urlJsonArray, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            MablaghKarmozd.remove(MablaghKarmozd.size() - 1);
                                            ad.notifyItemRemoved(MablaghKarmozd.size());

                                            try {
                                                JSONObject jsonRootObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonRootObject.optJSONArray("Data");

                                                for (int i = 0; i < jsonArray.length(); i++) {

                                                    JSONObject person = jsonArray.getJSONObject(i);

                                                    NameBimeGozar.add(person.getString("NameBimeGozar"));
                                                    Percent.add(person.getString("Percent"));
                                                    MablaghKarmozd.add(person.getString("MablaghKarmozd"));
                                                    MablaghHagheBime.add(person.getString("MablaghHagheBime"));
                                                    PayPDate.add(person.getString("PayPDate"));
                                                    IsPay.add(person.getString("IsPay"));
                                                    BimeKind.add(person.getString("BimeKind"));

                                                }
                                                ad.setLoaded();

                                            } catch (JSONException e) {
                                                ProgressBar.setVisibility(View.GONE);
                                                recyclerViewlist.setVisibility(View.GONE);

                                                Log.i("mohsenjamali", "onErrorResponse:loadmore1 " + e.getMessage());
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            ProgressBar.setVisibility(View.GONE);
                                            recyclerViewlist.setVisibility(View.GONE);

                                            Log.i("mohsenjamali", "onErrorResponse1::loadmore2 " + error.getMessage());
                                        }
                                    }) {
                                        protected Map<String, String> getParams() {
                                            Map<String, String> MyData = new HashMap<>();
                                            page = page + 1;
                                            SharedPreferences sp = context.getSharedPreferences("Token", 0);
                                            MyData.put("PageNumber", String.valueOf(page));
                                            MyData.put("Api_token", sp.getString("token", "nothing"));
                                            // MyData.put("BimehKind", Str);
                                            return MyData;
                                        }
                                    };

                                    if (page <= pageTotal) {
                                        MyRequestQueue.add(MyStringRequest);
                                    }
                                    ad.notifyDataSetChanged();
                                }
                            });

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
                MyData.put("PageNumber", String.valueOf(page));
                MyData.put("Api_token", sp.getString("token", "nothing"));
                // MyData.put("BimehKind", Str);
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
