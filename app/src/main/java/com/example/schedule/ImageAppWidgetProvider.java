package com.example.schedule;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.RemoteViews;

public class ImageAppWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            SharedPreferences prefs = context.getSharedPreferences("str_image", Context.MODE_PRIVATE);
            String str_image = prefs.getString("imagestrings", "");
            Bitmap bitmap = StringToBitmap(str_image);


            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.image_widget);
            views.setOnClickPendingIntent(R.id.view_image_widget, pendingIntent);
            ComponentName thisWidget = new ComponentName(context, ImageAppWidgetProvider.class);
            views.setImageViewBitmap(R.id.view_image_widget, bitmap);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    public Bitmap StringToBitmap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}

