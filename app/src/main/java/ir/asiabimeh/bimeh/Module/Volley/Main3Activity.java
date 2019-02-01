package ir.asiabimeh.bimeh.Module.Volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_main3);


        // You should add NETWORK Validation
        _loadAPI_POST();

    }

    public void _loadAPI_POST() {


        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("UserName", "m.jamali");
            jsonBody.put("UserPassword", "@44404530");
            final String mRequestBody = jsonBody.toString();
            _SEND(mRequestBody); // Check INTERNET is ON or NOT ?
        } catch (Exception e) {

        }


    }

    private void _SEND(String getPARAM) {
        try {

            String num1 = "20";
            String num2 = "0";
            String num3 = "Outbox";

            // String URL = "http://185.55.226.168/Dashboard/api/pgLogin/Login";

            String URL = String.format("http://185.55.226.168/Dashboard/api/pgDashboard?take=%s&skip=%s&DashboardMode=%s", num1, num2, num3);
            Log.i("moh3n", "_SEND: " + URL);
            VolleyApiCAll volleyApiCAll = new VolleyApiCAll(Main3Activity.this);
            // volleyApiCAll.Volley_POST(getPARAM, URL, new VolleyApiCAll.VolleyCallback() {
            volleyApiCAll.Volley_GET(URL, new VolleyApiCAll.VolleyCallback() {
                @Override
                public void onSuccessResponse(String result) {


                    try {
                        if (result.matches("VOLLEY_NETWORK_ERROR")) {
                            Toast.makeText(Main3Activity.this, "NETWORK PROBLEM", Toast.LENGTH_SHORT).show();
                        } else {
                            try {

                                Log.i("moh3n", "onSuccessResponse: " + result);
                                System.out.println("RESULT" + result);
                                // GET JSON THROUGH result


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}