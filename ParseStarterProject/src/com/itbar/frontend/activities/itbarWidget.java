package com.itbar.frontend.activities;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.Toast;
import android.content.Intent;
import android.util.Log;

import com.itbar.R;

/**
 * Implementation of App Widget functionality.
 */
public class itbarWidget extends AppWidgetProvider {

    private static final String MyOnClick1 = "myOnClickTag1";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        ComponentName thisWidget = new ComponentName(context,itbarWidget.class);

        for (int i = 0; i < N; i++) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.itbar_widget);
            remoteViews.setOnClickPendingIntent(R.id.btn, getPendingSelfIntent(context,MyOnClick1));
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (MyOnClick1.equals(intent.getAction())) {
            // your onClick action is here
            Toast.makeText(context, "Button1", Toast.LENGTH_SHORT).show();
            Log.w("Widget", "Clicked button1");
        }
    };

    @Override
    public void onEnabled(Context context) {
        // EnterActivity relevant functionality for when the first widget is created

    }

    @Override
    public void onDisabled(Context context) {
        Toast.makeText(context,"Gracias por usar ITBAr",Toast.LENGTH_SHORT).show();

    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.itbar_widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

