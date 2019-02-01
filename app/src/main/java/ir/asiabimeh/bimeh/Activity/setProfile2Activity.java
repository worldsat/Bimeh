package ir.asiabimeh.bimeh.Activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.system.ErrnoException;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ir.asiabimeh.bimeh.Helper.setProfile;
import ir.asiabimeh.bimeh.OtherClass.croping_image;
import ir.asiabimeh.bimeh.R;

public class setProfile2Activity extends AppCompatActivity {
    private ImageView btn1, btn2, btn3, btn4;
    private Uri mCropImageUri;
    private SharedPreferences pic_reader;
    private Button save;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile2);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Toolbar();
        findViewByid();
        initView();

    }

    private void findViewByid() {
        btn1 = findViewById(R.id.aks);
        btn2 = findViewById(R.id.melli);
        btn3 = findViewById(R.id.shnesname);
        progressBar = findViewById(R.id.progressBar3);
        save = findViewById(R.id.saveBtn);

    }

    private void initView() {
        pic_reader = getApplicationContext().getSharedPreferences("picture", 0);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getPickImageChooserIntent("1"), 200);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getPickImageChooserIntent("2"), 200);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getPickImageChooserIntent("3"), 200);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setProfile setProfile = new setProfile();
                setProfile.connect(setProfile2Activity.this,progressBar);

            }
        });


        Bitmap[] img_shared = new Bitmap[7];
        if (!pic_reader.getString("pic1", "nothing to show").equals("nothing to show")) {
            img_shared[1] = decodeBase64(pic_reader.getString("pic1", "nothing to show"));
            btn1.setImageBitmap(img_shared[1]);
        }
        if (!pic_reader.getString("pic2", "nothing to show").equals("nothing to show")) {
            img_shared[2] = decodeBase64(pic_reader.getString("pic2", "nothing to show"));
            btn2.setImageBitmap(img_shared[2]);
        }
        if (!pic_reader.getString("pic3", "nothing to show").equals("nothing to show")) {
            img_shared[3] = decodeBase64(pic_reader.getString("pic3", "nothing to show"));
            btn3.setImageBitmap(img_shared[3]);
        }
        if (!pic_reader.getString("pic4", "nothing to show").equals("nothing to show")) {
            img_shared[4] = decodeBase64(pic_reader.getString("pic4", "nothing to show"));
            btn4.setImageBitmap(img_shared[4]);
        }
    }

    private void Toolbar() {
        Toolbar my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);

        TextView titleActionBar = findViewById(R.id.title_toolbar);
        titleActionBar.setText("بارگذاری عکس ها");

        LinearLayout backIcon = findViewById(R.id.back_toolbar);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backMethod();

            }
        });

    }

    @Override
    public void onBackPressed() {
        backMethod();
    }

    private void backMethod() {
        finish();
    }


    public Intent getPickImageChooserIntent(String number) {

// Determine Uri of camera image to  save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

// collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

// collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

// the main intent is the last in the  list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

// Create a chooser from the main  intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "انتخاب کنید:");

// Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));
        pic_reader.edit().putString("number_pick_image", number).apply();

        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        return outputFileUri;
    }


    /**
     * Get the URI of the selected image from  {}.<br/>
     * Will return the correct URI for camera  and gallery image.
     *
     * @param data the returned data of the  activity result
     */
    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null && data.getData() != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    /**
     * Test if we can open the given Android URI to background_topBorder_Green if permission required error is thrown.<br>
     */
    public boolean isUriRequiresPermissions(Uri uri) {
        try {
            ContentResolver resolver = getContentResolver();
            InputStream stream = resolver.openInputStream(uri);
            stream.close();
            return false;
        } catch (FileNotFoundException e) {
            if (e.getCause() instanceof ErrnoException) {
                return true;
            }
        } catch (Exception e) {
            //nothing
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // mCropImageView.setImageUriAsync(mCropImageUri);
            Intent intent = new Intent(setProfile2Activity.this, croping_image.class);
            intent.putExtra("imageUri", mCropImageUri.toString());
            intent.putExtra("number", pic_reader.getString("number_pick_image", ""));
            intent.putExtra("page", "activity_product");
            startActivity(intent);
        } else {
            //Toast.makeText(this, "Required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Bundle address2 = data.getExtras();


            }
        }
        if (requestCode == 200) {

            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = getPickImageResultUri(data);

                // For API >= 23 we need to check specifically that we have permissions to read external storage,
                // but we don't know if we need to for the URI so the simplest is to try open the stream and see if we get error.
                boolean requirePermissions = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                        isUriRequiresPermissions(imageUri)) {

                    // request permissions and handle the result in onRequestPermissionsResult()
                    requirePermissions = true;
                    mCropImageUri = imageUri;

                    requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                }

                if (!requirePermissions) {
                    Intent intent = new Intent(setProfile2Activity.this, croping_image.class);
                    intent.putExtra("imageUri", imageUri.toString());
                    intent.putExtra("number", pic_reader.getString("number_pick_image", ""));
                    intent.putExtra("page", "activity_product");
                    startActivity(intent);
                    //  mCropImageView.setImageUriAsync(imageUri);
                }
            }
        }
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
