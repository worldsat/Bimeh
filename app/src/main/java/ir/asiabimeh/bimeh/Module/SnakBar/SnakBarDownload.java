package ir.asiabimeh.bimeh.Module.SnakBar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

public class SnakBarDownload {
    public void snakShow(final Context context, String str) {


        final Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(android.R.id.content), str, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("بازکردن پوشه", new View.OnClickListener() {
            @Override
            public void onClick(View vv) {

                Uri selectedUri = Uri.parse(Environment.getExternalStorageDirectory() + "/Mahpad/");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(selectedUri, "resource/folder");
                ((Activity)context).startActivity(intent);

            }
        });


        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        TextView action = sbView.findViewById(android.support.design.R.id.snackbar_action);
        textView.setTextColor(Color.WHITE);
        action.setTextColor(Color.RED);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/iransans_m.ttf");
        textView.setTypeface(font);
        action.setTypeface(font);
        snackbar.show();

    }

    public void openFolder(Context context) {

    }
}
