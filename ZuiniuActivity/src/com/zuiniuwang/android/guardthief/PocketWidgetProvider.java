package com.zuiniuwang.android.guardthief;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.zuiniuwang.android.guardthief.util.CustomConfiguration;

public class PocketWidgetProvider extends AppWidgetProvider {
	private static final String CLICK_NAME_ACTION = "com.zuiniuwang.android.widget.pocket.click";
	public static final String Refresh_ACTION = "com.zuiniuwang.android.widget.pocket.refresh";


	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		initListener(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		if (intent.getAction().equals(CLICK_NAME_ACTION)) {
			boolean value = CustomConfiguration.getIsPocketOn();
		}
		initListener(context);
	}

	
	public static void initListener(Context context){
		AppWidgetManager appWidgetManger = AppWidgetManager
				.getInstance(context);
		int[] appIds = appWidgetManger.getAppWidgetIds(new ComponentName(
				context, PocketWidgetProvider.class));
		RemoteViews	rv = new RemoteViews(context.getPackageName(), R.layout.widget_pocket);
		if (CustomConfiguration.getIsPocketOn()) {
			rv.setImageViewResource(R.id.pocketImage, R.drawable.pocket_on);
		} else {
			rv.setImageViewResource(R.id.pocketImage, R.drawable.pocket_off);
		}
		
		Intent intentClick = new Intent(CLICK_NAME_ACTION);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				intentClick, PendingIntent.FLAG_UPDATE_CURRENT);
		rv.setOnClickPendingIntent(R.id.widgetlayout, pendingIntent);
		
		appWidgetManger.updateAppWidget(appIds, rv); 
	}
	
	
	
	
	
	
}