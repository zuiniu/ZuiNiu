package com.zuiniuwang.android.guardthief.receiver;

import android.util.Log;

import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.GuardService;
import com.zuiniuwang.android.guardthief.util.FileUtil;

/**
 * 
 * 监控线程，循环执行偷拍,附带尝试发送邮件。
 * 开始后30秒开始拍照
 * <P>
 * 通过开关屏幕来启动和停止。
 * @author guoruiliang
 * 
 */
public class ShootThread {
	/** 记录Tag */
	private static final String LOG_TAG = Const.LOG_TAG;
	public static String className=ShootThread.class.getSimpleName();
	/**循环线程*/
	public static Thread autoThread;
	/**
	 * 开启
	 */
	public static void startShootThread() {
		stopShootThread();
		Log.d(LOG_TAG, className+" startShootThread");
		autoThread = new Thread("ShootThread") {
			public void run() {
				try {
					while (true) {
						if (FileUtil.getGuardPicNumber() >GuardApplication.basicConfig.maxPic) {
							stopShootThread();
							return;
						}
						
						Thread.sleep(Const.screenTimeToStartCamera);//开启屏幕后N秒才开始拍照
						
						GuardService.guardService.startToShootFace();
						
						Thread.sleep(GuardApplication.basicConfig.guardPeriod);
						
						
					}
				} catch (Exception e) {
				}

			}
		};
		autoThread.start();
	}

	/**
	 * 关闭
	 */
	public static void stopShootThread() {
		if (autoThread != null) {
			Log.d(LOG_TAG, className+" stopShootThread");
			autoThread.interrupt();
			autoThread = null;
		}
	}

}
