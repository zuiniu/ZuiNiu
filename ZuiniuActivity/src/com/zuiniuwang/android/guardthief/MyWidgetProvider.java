package com.zuiniuwang.android.guardthief;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.zuiniuwang.android.guardthief.ui.Logo2Activity;

public class MyWidgetProvider extends AppWidgetProvider {
	private static final String CLICK_NAME_ACTION = "com.zuiniuwang.android.widget.click";

	private static RemoteViews rv;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		if (intent.getAction().equals(CLICK_NAME_ACTION)) {
			Intent i=new Intent(context, Logo2Activity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);

		}

		// AppWidgetManager appWidgetManger = AppWidgetManager
		// .getInstance(context);
		// int[] appIds = appWidgetManger.getAppWidgetIds(new ComponentName(
		// context, MyWidgetProvider.class));
		// appWidgetManger.updateAppWidget(appIds, rv);
	}

	public static void updateAppWidget(Context context,
			AppWidgetManager appWidgeManger, int appWidgetId) {
		rv = new RemoteViews(context.getPackageName(), R.layout.widget);
		Intent intentClick = new Intent(CLICK_NAME_ACTION);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				intentClick, PendingIntent.FLAG_UPDATE_CURRENT);
		rv.setOnClickPendingIntent(R.id.widgetlayout, pendingIntent);
		appWidgeManger.updateAppWidget(appWidgetId, rv);
	}
}