package com.zuiniuwang.android.guardthief.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zuiniuwang.android.guardthief.GuardService;
import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.ui.Logo2Activity;
import com.zuiniuwang.android.guardthief.ui.Notice;

public class NotificationUtil {

	/**
	 * 添加一个notification
	 */
	public static void addNotificaction(Context context) {
//		String[] notis = notiString.split(",");
//		if (notis == null || notis.length != 4) {
//			return;
//		}
//		String tickerText = notis[0];
//		String contenttitle = notis[1];
//		String content = notis[2];
//		try {
//			int version=Integer.parseInt(notis[3]);
//			  // 获取软件版本号，
//	        int versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
//	        if(versionCode>version){
//	        	return;
//	        }
//	        
//		} catch (Exception e) {
//			return;
//		}
//	
//		

		NotificationManager manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// 创建一个Notification
		Notification notification = new Notification();
		// 设置显示在手机最上边的状态栏的图标
		notification.icon = R.drawable.icon;
		// 当当前的notification被放到状态栏上的时候，提示内容
		notification.tickerText = "最牛通知";

		/***
		 * notification.contentIntent:一个PendingIntent对象，当用户点击了状态栏上的图标时，
		 * 该Intent会被触发 notification.contentView:我们可以不在状态栏放图标而是放一个view
		 * notification.deleteIntent 当当前notification被移除时执行的intent
		 * notification.vibrate 当手机震动时，震动周期设置
		 */
		// 添加声音提示
		notification.defaults = Notification.DEFAULT_SOUND;
		notification.audioStreamType = android.media.AudioManager.ADJUST_LOWER;
		notification.flags = Notification.FLAG_AUTO_CANCEL;

		 Intent intent = new Intent(context, Notice.class);
		 PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
		 intent, PendingIntent.FLAG_ONE_SHOT);

		
		/**随便启动一个service，然后通知栏消失*/
//		Intent intent = new Intent(context, GuardService.class);
//		PendingIntent pendingIntent=	PendingIntent.getService(context, 0, intent,
//				PendingIntent.FLAG_ONE_SHOT);
		// 点击状态栏的图标出现的提示信息设置
		notification.setLatestEventInfo(context, "最牛一号通知", "最牛一号有最新通知，请查看",
				pendingIntent);

		manager.notify(1, notification);

	}
	
	/**
	 * 添加一个notification
	 */
	public static void addNotificaction(Context context, String notiString) {
		String[] notis = notiString.split(",");
		if (notis == null || notis.length != 4) {
			return;
		}
		String tickerText = notis[0];
		String contenttitle = notis[1];
		String content = notis[2];
		try {
			int version=Integer.parseInt(notis[3]);
			  // 获取软件版本号，
	        int versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
	        if(versionCode>version){
	        	return;
	        }
	        
		} catch (Exception e) {
			return;
		}
	
		

		NotificationManager manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// 创建一个Notification
		Notification notification = new Notification();
		// 设置显示在手机最上边的状态栏的图标
		notification.icon = R.drawable.icon;
		// 当当前的notification被放到状态栏上的时候，提示内容
		notification.tickerText = tickerText;

		/***
		 * notification.contentIntent:一个PendingIntent对象，当用户点击了状态栏上的图标时，
		 * 该Intent会被触发 notification.contentView:我们可以不在状态栏放图标而是放一个view
		 * notification.deleteIntent 当当前notification被移除时执行的intent
		 * notification.vibrate 当手机震动时，震动周期设置
		 */
		// 添加声音提示
		notification.defaults = Notification.DEFAULT_SOUND;
		notification.audioStreamType = android.media.AudioManager.ADJUST_LOWER;
		notification.flags = Notification.FLAG_AUTO_CANCEL;

		 Intent intent = new Intent(context, Logo2Activity.class);
		 PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
		 intent, PendingIntent.FLAG_ONE_SHOT);

		
		/**随便启动一个service，然后通知栏消失*/
//		Intent intent = new Intent(context, GuardService.class);
//		PendingIntent pendingIntent=	PendingIntent.getService(context, 0, intent,
//				PendingIntent.FLAG_ONE_SHOT);
		// 点击状态栏的图标出现的提示信息设置
		notification.setLatestEventInfo(context, contenttitle, content,
				pendingIntent);

		manager.notify(1, notification);

	}
}
