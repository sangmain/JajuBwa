package com.example.schedule;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class ImageAppWidgetConfig extends AppCompatActivity {

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private ImageView imageView;


    Uri imageUri;
    public static Bitmap bitmap;
    private static final int PICK_IMAGE = 100;
    private static final int PICK_WEB_IMGAE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_app_widget_config);

        Intent configIntent = getIntent();
        Bundle extras = configIntent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_CANCELED, resultValue);

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        imageView = findViewById(R.id.view_preview);
    }

    public void select_from_file(View v)
    {
        openGallery(v);
    }

    public void select_from_web(View v){
        Intent intent = new Intent(this, ImageFromWeb.class);
        startActivityForResult(intent, PICK_WEB_IMGAE);
    }
    private void openGallery(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Context context = this;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        if(resultCode == RESULT_OK &&  requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            try {
                imageView.setImageURI(imageUri);
                setWidgetImage();
            }
            catch (Exception e){
                imageView.setImageResource(R.drawable.error);
            }

        }

        else if(resultCode == RESULT_OK && requestCode == PICK_WEB_IMGAE){
            String url = data.getStringExtra("getURL");
            Log.d("sangmin", url);

            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.loading)
                    .into(imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            setWidgetImage();
                        }
                        @Override
                        public void onError(Exception ex) {
                            imageView.setImageResource(R.drawable.error);
                        }
                    });
        }
        else {
            imageView.setImageResource(R.drawable.error);
        }

    }
    private void setWidgetImage(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        Log.d("sangmin", "in");
        bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.image_widget);
        remoteViews.setImageViewBitmap(R.id.view_image_widget, bitmap);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

        String str_image = BitMapToString(bitmap);
        SharedPreferences prefs = getSharedPreferences("str_image", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(String.valueOf(appWidgetId), str_image);
        editor.apply();

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte [] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
