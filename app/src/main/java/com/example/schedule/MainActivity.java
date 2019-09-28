package com.example.schedule;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    public void confirmConfiguration(View v) {
        openGallery();
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK &&  requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);

            Context context = this;
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.example_widget);
            ComponentName thisWidget = new ComponentName(context, ExampleAppWidgetProvider.class);
//            remoteViews.setImageViewUri(R.id.scrImage, imageUri);
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.image2);
//            remoteViews.setImageViewResource(R.id.scrImage, R.drawable.image2);
            remoteViews.setImageViewBitmap(R.id.scrImage, bitmap);


            appWidgetManager.updateAppWidget(thisWidget, remoteViews);

        }
    }
}