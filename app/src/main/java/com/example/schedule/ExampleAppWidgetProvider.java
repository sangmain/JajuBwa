package com.example.schedule;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;


public class ExampleAppWidgetProvider extends AppWidgetProvider {

    static Uri uri;
    Bitmap bitmap;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d("debug", "update function called");

        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.screenshot);

        if (MainActivity.bitmap != null) {
            Log.d("debug", "bitmap is not null");
            bitmap = MainActivity.bitmap;
        }
        for (int appWidgetId : appWidgetIds) {
            Toast.makeText(context, "onUpdate():", Toast.LENGTH_LONG).show();
            Log.d("debug", "update in widget");
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_widget);
            views.setOnClickPendingIntent(R.id.scrImage, pendingIntent);
            views.setImageViewBitmap(R.id.scrImage, bitmap);
//            ComponentName thisWidget = new ComponentName(context, ExampleAppWidgetProvider.class);
//            appWidgetManager.updateAppWidget(thisWidget, views);


            appWidgetManager.updateAppWidget(appWidgetId, views);

        }
    }
}

