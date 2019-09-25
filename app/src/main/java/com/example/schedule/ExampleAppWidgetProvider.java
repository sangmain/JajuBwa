package com.example.schedule;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

public class ExampleAppWidgetProvider extends AppWidgetProvider {

    static Context context;
    static Uri uri;
    public ExampleAppWidgetProvider(Context c) {
        context = c;

    }
    ImageView widgetView;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
//            Toast.makeText(context, "onUpdate():" , Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_widget);
//            views.setImageViewUri(R.id.scrImage, uri);
            views.setOnClickPendingIntent(R.id.scrImage, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);


        }
    }

    public void aaa(Uri image) {
        uri = image;
//        widgetView = (ImageView)findViewById(R.id.scrImage);
    }
}

