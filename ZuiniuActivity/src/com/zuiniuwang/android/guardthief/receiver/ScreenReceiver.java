package com.zuiniuwang.android.guardthief.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.GuardService;
import com.zuiniuwang.android.guardthief.util.CustomConfiguration;
import com.zuiniuwang.android.guardthief.util.LogUtil;

/**
 * 开关屏幕广播接收器
 * <P>
 * 网络状态改变也加进去了
 * 
 * @author guoruiliang
 * 
 */
public class ScreenReceiver extends BroadcastReceiver {
	/** 日志标签 */
	private static final String LOG_TAG = Const.LOG_TAG;

	public String className = getClass().getSimpleName();
	/** 服务 */
	private Context context;
	/** 过滤器 */
	private IntentFilter screenFilter;
	/** sim卡 */
	private final static String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            服务
	 * */
	public ScreenReceiver(Context context) {
		this.context = context;
		screenFilter = new IntentFilter();
		screenFilter.addAction(Intent.ACTION_SCREEN_ON);
		screenFilter.addAction(Intent.ACTION_SCREEN_OFF);
		screenFilter.addAction(ACTION_SIM_STATE_CHANGED);
		screenFilter.addAction(Intent.ACTION_USER_PRESENT);
		// 网络状态改变
		// screenFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
	}

	/**
	 * 注册广播到服务
	 */
	public void register() {
		if (context != null && screenFilter != null) {
			context.registerReceiver(this, screenFilter);
		}
	}

	/**
	 * 从服务中注销广播
	 */
	public void unRegister() {
		if (context != null && screenFilter != null) {
			context.unregisterReceiver(this);
		}
	}

	@Override
	public void onReceive(final Context context, Intent intent) {
		if (intent != null) {
			if (intent.getAction().equalsIgnoreCase(Intent.ACTION_SCREEN_ON)) {
				LogUtil.logI(className + " open");
				if (GuardService.guardService != null
						&& CustomConfiguration.isStartGuard()) {
					Const.ScreenUnlockTime++;
					ShootThread.startShootThread();// 启动循环偷拍线程
				}

				/** 发送反馈信息 */

				if (GuardService.guardService != null) {

					GuardApplication.excutorService.execute(new Runnable() {

						public void run() {
							GuardService.guardService.sendFeedBackMail();
						}

					});

				}

			} else if (intent.getAction().equalsIgnoreCase(
					Intent.ACTION_SCREEN_OFF)) {
				LogUtil.logI(className + " close");
				ShootThread.stopShootThread();
			}
			// 网咯状态改变
			else if (intent.getAction().equalsIgnoreCase(
					ConnectivityManager.CONNECTIVITY_ACTION)) {

			}
		}
	}

}
