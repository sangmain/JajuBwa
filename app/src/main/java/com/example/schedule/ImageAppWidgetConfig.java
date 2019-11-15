package com.example.schedule;

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
    private int pixel_limit = 2350 * 2350;


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

        if(resultCode == RESULT_OK &&  requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            try {
                SharedPreferences prefs = getSharedPreferences("URI", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(String.valueOf(appWidgetId), imageUri.toString());
                editor.apply();
                Update(appWidgetId);
            }
            catch (Exception e){
                imageView.setImageResource(R.drawable.error);
            }
        }

        else if(resultCode == RESULT_OK && requestCode == PICK_WEB_IMGAE){
            String url = data.getStringExtra("getURL");
//            Log.d("sangmin", url);

            SharedPreferences prefs = getSharedPreferences("URL", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(String.valueOf(appWidgetId), url);
            editor.apply();
            Update(appWidgetId);
        }
        else {
            imageView.setImageResource(R.drawable.error);
        }
    }
    public void Update(int appWidgetId)
    {
        try {
            try {
                UpdateImage(appWidgetId);
            }

            catch (Exception e){
                Update_Web_Image(appWidgetId);
            }
        }
       catch (Exception e){

        }
    }
    private void UpdateImage(int appWidgetId){
        SharedPreferences prefs = this.getSharedPreferences("URI", Context.MODE_PRIVATE);
        String str_uri = prefs.getString(String.valueOf(appWidgetId), "");

        Uri uri = Uri.parse(str_uri);
        imageView.setImageURI(uri);
        setWidgetImage();

    }
    private void Update_Web_Image(int appWidgetId) {
        SharedPreferences prefs = this.getSharedPreferences("URL", Context.MODE_PRIVATE);
        String url = prefs.getString(String.valueOf(appWidgetId), "");
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
    private void setWidgetImage(){
        Log.d("sangmin", "Update Widget Image");

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        double heigth = bitmap.getHeight();
        double width = bitmap.getWidth();
        double ratio = 0.8;
//        Log.d("sangmin", String.valueOf(heigth));
//        Log.d("sangmin", String.valueOf(width));

        while ((heigth * width) >= pixel_limit){
            heigth =  heigth * ratio;
            width = width  * ratio;
        }
//        Log.d("sangmin", String.valueOf(heigth));
//        Log.d("sangmin", String.valueOf(width));
        Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int)width, (int)heigth, true);

        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.image_widget);
        Log.d("sangmin", "1");

        remoteViews.setImageViewBitmap(R.id.view_image_widget, resized);
        Log.d("sangmin", "2");

        //        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//        remoteViews.setOnClickPendingIntent(R.id.view_image_widget, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        Log.d("sangmin", "3");


        String str_image = BitMapToString(resized);
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
