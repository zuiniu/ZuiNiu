package com.zuiniuwang.android.guardthief;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.zuiniuwang.android.guardthief.baidu.BaiduLocation;
import com.zuiniuwang.android.guardthief.bean.NoticeDao;
import com.zuiniuwang.android.guardthief.config.BasicConfig;
import com.zuiniuwang.android.guardthief.config.GuardConfig;
import com.zuiniuwang.android.guardthief.config.MailConfig;
import com.zuiniuwang.android.guardthief.config.NoticeBean;
import com.zuiniuwang.android.guardthief.config.NoticeConfig;
import com.zuiniuwang.android.guardthief.config.UserConfig;
import com.zuiniuwang.android.guardthief.config.UserConfiglBean;
import com.zuiniuwang.android.guardthief.util.CustomConfiguration;
import com.zuiniuwang.android.guardthief.util.FileUtil;
import com.zuiniuwang.android.guardthief.util.NotificationUtil;

/**
 * 全局类
 * 
 * @author guoruiliang
 */
public class GuardApplication extends Application {
	private static final String LOG_TAG = Const.LOG_TAG;
	public String className = getClass().getSimpleName();
	public static GuardApplication appInstance;
	public static Handler appHandler;
	public static Context appContext;
	/** 线程池 */
	public static ExecutorService excutorService;

	public static MailConfig mailConfig;
	public static BasicConfig basicConfig;
	public static UserConfig userConfig;
	public static NoticeConfig noticeConfig;
	NoticeDao noticeDao;
	
	/** 百度定位 */
	public static BaiduLocation mBaiduLocation = null;

	private List<Activity> mList = new LinkedList<Activity>(); // 用于存放每个Activity的List

	private static WakeLock mWakeLock;
	private static PowerManager pm;

	
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		Log.i(LOG_TAG, className + " onTerminate() version:" + Const.VERSION);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		noticeDao=new NoticeDao(GuardApplication.this);
		
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

		try {
			String packageName = getApplicationContext().getPackageName();
			ApplicationInfo appinfo = getPackageManager().getApplicationInfo(
					packageName, PackageManager.GET_META_DATA);
		} catch (Exception e) {

		}

		Log.i(LOG_TAG, className + " onCreate() version:" + Const.VERSION);
		
		appContext = getApplicationContext();
		appInstance = this;
		appHandler = new Handler();
		excutorService = Executors.newCachedThreadPool();
		
		if(Const.isRoot){
			CustomConfiguration.setIsRoot(true);
		}
		
		
		/** 如果没有设置权限Code,初始的时候设置一次 */
		if (TextUtils.isEmpty(CustomConfiguration.getCode())) {
			CustomConfiguration.saveCode(Const.ManageCode);
		}
		
		FileUtil.mkDirs();
		startRefreshConfigThread();// 更新配置
		// 启动服务
		startService();
		// 开启百度位置
		startBaiduLocation();

	}

	/**
	 * 开启百度位置监控
	 * <P>
	 * 根据是否开启自动监控设置刷新时间。 启动后，30秒后刷新获取一次位置。
	 */
	private void startBaiduLocation() {
		// 如果监控开启，则位置刷新时间比较短
		if (CustomConfiguration.isStartGuard()) {
			BaiduLocation.freshTime = Const.LOCATION_REFRESH_GAP;
		} else {
			BaiduLocation.freshTime = Const.LOCATION_REFRESH_GAP_STOP;
		}
		// 位置
		mBaiduLocation = new BaiduLocation(this);
		// 可以设置是否监控打开胡时候才获取位置
		mBaiduLocation.start();
		appHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				mBaiduLocation.refresh();
			}
		}, 30000);
	}

	public static String encrypt(String text) {
		return text;
	}

	public static String decrypt(String text) {
		return text;
	}

	/** 开启循环执行更新配置线程 */
	private void startRefreshConfigThread() {
		try {
			initConfig();// 先读取一下默认配置
		} catch (Exception e) {
			// TODO: handle exception
		}

		new Thread("refreshConfig") {
			public void run() {
				while (true) {
					updateConfig();
					SystemClock.sleep(Const.REFRESH_CONFIG_GAP);
				}
			}
		}.start();

	}

	/** 初始化配置 */
	public void initConfig() {
		Log.i(LOG_TAG, className + " initConfig");
		userConfig = UserConfig.getUserConfig();
		noticeConfig =NoticeConfig.getNoticeConfig();
		
		//保存通知
		if(noticeConfig!=null&&noticeConfig.mList!=null){
			for(NoticeBean bean:noticeConfig.mList){
				noticeDao.addNotice(bean);
			}
		}
		
		String code = GuardApplication.encrypt(CustomConfiguration.getCode());

		boolean isSuccess = false;
		if (!TextUtils.isEmpty(code)) {
			for (UserConfiglBean user : userConfig.mList) {
				if (user.userName.equalsIgnoreCase(code)) {
					// 说明是权限用户
					mailConfig.setTag(user.mailTag);
					basicConfig.setTag(user.basicTag);
					isSuccess = true;
				}
			}
			// 如果没有匹配成功，设置回默认
			if (!isSuccess) {
				mailConfig.setTag(Const.ConfigName_Mail);
				basicConfig.setTag(Const.ConfigName_Basic);
			}

		}

		mailConfig = MailConfig.getMailConfig();
		basicConfig = BasicConfig.getBasicConfig();

		// 判断是否需要显示通知。
		if (noticeConfig!=null
				&& !noticeConfig.toJson() 
						.equalsIgnoreCase(CustomConfiguration.getNotification())) {
			NotificationUtil.addNotificaction(appContext);
			CustomConfiguration.saveNotification(noticeConfig.toJson());
		}

	} 

	/** 网络更新配置,成功后，更新配置 */
	private void updateConfig() {
		GuardConfig.UpdateConfig(appContext, new Runnable() {
			@Override
			public void run() {
				initConfig();
			}
		});
	}

	/** 启动服务 */
	public static void startService() {
		Intent service = new Intent(appContext, GuardService.class);
		appContext.startService(service);
	}

	/**
	 * 添加启动的activity
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		mList.add(activity);
	}

	/** 遍历List，退出每一个Activity */
	public void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	/**
	 * 停止功能
	 */
	public static void stopAllFunction() {
		if (CustomConfiguration.isStartGuard()) {
			CustomConfiguration.setIsStartGuard(false);
			CustomConfiguration.setIsPocketOn(false);
		}

	}

	public static  void acquire() {
		release();
		int mode = PowerManager.PARTIAL_WAKE_LOCK;
		mWakeLock = pm.newWakeLock(mode, "");
		mWakeLock.acquire();
	}

	public static  void release() {
		if (mWakeLock != null) {
			mWakeLock.release();
			mWakeLock = null;
		}
	}

}
