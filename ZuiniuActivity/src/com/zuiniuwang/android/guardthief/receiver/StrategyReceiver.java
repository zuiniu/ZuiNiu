package com.zuiniuwang.android.guardthief.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.GuardService;
import com.zuiniuwang.android.guardthief.bll.CameraStrategy;
import com.zuiniuwang.android.guardthief.bll.TimeStrategy;
import com.zuiniuwang.android.guardthief.util.CustomConfiguration;

/**
 * 开关屏幕广播接收器
 * <P>
 * 
 * 
 * @author guoruiliang
 * 
 */
public class StrategyReceiver extends BroadcastReceiver {
	/** 日志标签 */
	private static final String LOG_TAG = Const.LOG_TAG;

	public String className = getClass().getSimpleName();
	/** 服务 */
	private Context mContext;
	/** 过滤器 */
	private IntentFilter mFilter;

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            服务
	 * */
	public StrategyReceiver(Context context) {
		this.mContext = context;
		mFilter = new IntentFilter();
		for(TimeStrategy strategy:TimeStrategy.values()){
			mFilter.addAction(strategy.getActionName());
		}
	}

	/**
	 * 注册广播到服务
	 */
	public void register() {
		if (mContext != null && mFilter != null) {
			mContext.registerReceiver(this, mFilter);
		}
	}

	/**
	 * 从服务中注销广播
	 */
	public void unRegister() {
		if (mContext != null && mFilter != null) {
			mContext.unregisterReceiver(this);
		}
	}

	@Override
	public void onReceive(final Context context, Intent intent) {
		if (intent != null) {
			String action=intent.getAction();
			Log.v(LOG_TAG, className + " strategy receiver action:"+action);
			TimeStrategy strategy=	TimeStrategy.getStrategyByActionName(action);
			String cameraActionName=intent.getStringExtra("name");
			strategy.openCamera(context,CameraStrategy.getStrategyByActionName(cameraActionName));
		}
	}

}
