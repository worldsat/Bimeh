package ir.asiabimeh.bimeh.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ir.asiabimeh.bimeh.R;

public class DownloadAppActivity extends AppCompatActivity {
    String Link = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_app);

        Bundle address = getIntent().getExtras();
        if (address != null) {

            Link = address.getString("address", "");

        }

        TextView download = findViewById(R.id.donnloadBtn);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Link));
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
