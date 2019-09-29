package com.example.schedule;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    Uri imageUri;
    public static Bitmap bitmap;
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.current_image);
    }

    public void confirmConfiguration(View v)
    {
        String myUrlStr = "https://image.shutterstock.com/image-photo/colorful-flower-on-dark-tropical-260nw-721703848.jpg";

        Picasso.get().load(myUrlStr).placeholder(R.drawable.loading).into(imageView);
        final Context context = this;
        Handler delayHandler = new Handler();
        delayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("print", "image from url set");
                bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.example_widget);
                ComponentName thisWidget = new ComponentName(context, ExampleAppWidgetProvider.class);
//            remoteViews.setImageViewUri(R.id.scrImage, imageUri);
//        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image2);
//        remoteViews.setImageViewUri(R.id.scrImage, uri);
                remoteViews.setImageViewBitmap(R.id.scrImage, bitmap);


                appWidgetManager.updateAppWidget(thisWidget, remoteViews);
            }
        }, 3000);

    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK &&  requestCode == PICK_IMAGE) {
            if (data.getData() != null) {
                Log.d("print", "Activity result");
                imageUri = data.getData();
//                String myUrlStr = "https://image.shutterstock.com/image-photo/colorful-flower-on-dark-tropical-260nw-721703848.jpg";

//                Picasso.get().load(myUrlStr).into(imageView);

//                Picasso.get().load(myUrlStr).''
//
//                Context context = this;
//                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.example_widget);
//                ComponentName thisWidget = new ComponentName(context, ExampleAppWidgetProvider.class);
////            remoteViews.setImageViewUri(R.id.scrImage, imageUri);
//                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image2);
//            remoteViews.setImageViewUri(R.id.scrImage, uri);
////                remoteViews.setImageViewBitmap(R.id.scrImage, bitmap);
//
//
//                appWidgetManager.updateAppWidget(thisWidget, remoteViews);
            }

        }
    }
}