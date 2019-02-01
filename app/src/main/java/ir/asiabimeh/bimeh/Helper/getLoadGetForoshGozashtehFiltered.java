package ir.asiabimeh.bimeh.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import ir.asiabimeh.bimeh.Adatper.getListForoshGozashteAdapter;
import ir.asiabimeh.bimeh.OtherClass.MiladiToShamsi;
import ir.asiabimeh.bimeh.OtherClass.OnLoadMoreListener;
import ir.asiabimeh.bimeh.R;


public class getLoadGetForoshGozashtehFiltered {

    private static List<String> NameBimeGozar = new ArrayList<>();
    private static List<String> MablaghHagheBime = new ArrayList<>();
    private static List<String> ShenaseBimeNaame = new ArrayList<>();
    private static List<String> RaveshPardakhtAllBime = new ArrayList<>();
    private static List<String> EnumStatus = new ArrayList<>();
    private static List<String> DateItems = new ArrayList<>();
    String Str = "";

    private getListForoshGozashteAdapter ad;

    private int pageTotal = 0;
    private int page = 1;

    public void get_banners(final Context context, final ProgressBar ProgressBar, final RecyclerView recyclerViewlist, final TextView warning, final String Activity, final String From, final String to) {

//        InternetActivity internetCheck = new InternetActivity();
//        internetCheck.CheckNet(context);


        switch (Activity) {
            case "omr":
                Str = "3";
                break;
            case "sales":
                Str = "4";
                break;
            case "badane":
                Str = "2";
                break;
            case "masoliat":
                Str = "5";
                break;
            case "atashsozi":
                Str = "1";
                break;

        }
        Log.i("moh3n", "onResponse2: " + Str);

        final String urlJsonArray = context.getString(R.string.LinkServer) + "api/Home/LoadPastSaleData";

        recyclerViewlist.setVisibility(View.INVISIBLE);
        ProgressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlJsonArray,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            recyclerViewlist.setVisibility(View.VISIBLE);

                            Log.i("moh3n", "onResponse1: " + response);
                            NameBimeGozar.clear();
                            MablaghHagheBime.clear();
                            ShenaseBimeNaame.clear();
                            RaveshPardakhtAllBime.clear();
                            EnumStatus.clear();
                            DateItems.clear();


                            JSONObject jsonRootObject = new JSONObject(response);
                            JSONArray array = jsonRootObject.optJSONArray("Data");

                            MiladiToShamsi miladiToShamsi = new MiladiToShamsi();
                            pageTotal = jsonRootObject.getInt("TotalItemCount") / 5;

                            if (array.length() == 0) warning.setVisibility(View.VISIBLE);


                            for (int i = 0; i < array.length(); i++) {

                                JSONObject person = array.getJSONObject(i);

                                NameBimeGozar.add(person.getString("NameBimeGozar"));
                                MablaghHagheBime.add(person.getString("MablaghHagheBime"));
                                ShenaseBimeNaame.add(person.getString("ShenaseBimeNaame"));
                                RaveshPardakhtAllBime.add(person.getString("RaveshPardakhtAllBime"));
                                EnumStatus.add(person.getString("EnumStatus"));
                                DateItems.add(miladiToShamsi.convert(person.getString("Date")));


                            }
                            try {

                                recyclerViewlist.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


                                ad = new getListForoshGozashteAdapter(context, NameBimeGozar, MablaghHagheBime, ShenaseBimeNaame, RaveshPardakhtAllBime, EnumStatus,DateItems, recyclerViewlist);
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
                                    ShenaseBimeNaame.add(null);
                                    ad.notifyItemInserted(ShenaseBimeNaame.size() - 1);

                                    //-----------------------------------------------------------------------------------------------
                                    RequestQueue MyRequestQueue = Volley.newRequestQueue(context);
                                    StringRequest MyStringRequest = new StringRequest(Request.Method.POST, urlJsonArray, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            ShenaseBimeNaame.remove(ShenaseBimeNaame.size() - 1);
                                            ad.notifyItemRemoved(ShenaseBimeNaame.size());

                                            try {
                                                JSONObject jsonRootObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonRootObject.optJSONArray("Data");
                                                MiladiToShamsi miladiToShamsi = new MiladiToShamsi();
                                                for (int i = 0; i < jsonArray.length(); i++) {

                                                    JSONObject person = jsonArray.getJSONObject(i);

                                                    NameBimeGozar.add(person.getString("NameBimeGozar"));
                                                    MablaghHagheBime.add(person.getString("MablaghHagheBime"));
                                                    ShenaseBimeNaame.add(person.getString("ShenaseBimeNaame"));
                                                    RaveshPardakhtAllBime.add(person.getString("RaveshPardakhtAllBime"));
                                                    DateItems.add(miladiToShamsi.convert(person.getString("Date")));

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
                                            MyData.put("BimeKind", Str);
                                            MyData.put("FromDate", From);
                                            MyData.put("ToDate", to);
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
                MyData.put("BimeKind", Str);
                MyData.put("FromDate", From);
                MyData.put("ToDate", to);
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
