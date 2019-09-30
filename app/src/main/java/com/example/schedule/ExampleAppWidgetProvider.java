package com.example.schedule;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.RemoteViews;


public class ExampleAppWidgetProvider extends AppWidgetProvider {

    Bitmap bitmap;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d("act widget", "update function called");

        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.screenshot);

        if (MainActivity.bitmap != null) {
            Log.d("act widget", "bitmap is not null");
            bitmap = MainActivity.bitmap;
        }
        for (int appWidgetId : appWidgetIds) {
            Log.d("act widget", "update in widget");
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_widget);
            views.setOnClickPendingIntent(R.id.scrImage, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
            views.setImageViewBitmap(R.id.scrImage, bitmap);


        }
    }
}

