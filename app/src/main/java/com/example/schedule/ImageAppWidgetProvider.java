package com.example.schedule;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.RemoteViews;

public class ImageAppWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d("sangmin","on Update");
        for (int appWidgetId : appWidgetIds) {
            ImageAppWidgetConfig cf = new ImageAppWidgetConfig();
            cf.Update(appWidgetId);

            SharedPreferences prefs = context.getSharedPreferences("str_image", Context.MODE_PRIVATE);
            String str_image = prefs.getString(String.valueOf(appWidgetId), "");
            Bitmap bitmap = StringToBitmap(str_image);


            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.image_widget);
            views.setImageViewBitmap(R.id.view_image_widget, bitmap);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//            views.setOnClickPendingIntent(R.id.view_image_widget, pendingIntent);

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

