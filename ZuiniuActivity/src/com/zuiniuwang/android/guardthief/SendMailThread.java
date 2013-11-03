package com.zuiniuwang.android.guardthief;

import java.security.acl.LastOwnerException;

import android.os.SystemClock;
import android.util.Log;

import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.GuardService;
import com.zuiniuwang.android.guardthief.util.CustomConfiguration;
import com.zuiniuwang.android.guardthief.util.FileUtil;
import com.zuiniuwang.android.guardthief.util.LogUtil;

/**
 * 
 * 循环执行发邮件,附带尝试发送邮件。
 * <P>
 * 
 */
public class SendMailThread {
	/** 记录Tag */
	private static final String LOG_TAG = Const.LOG_TAG;
	public static String className = SendMailThread.class.getSimpleName();
	/** 循环线程 */
	public static Thread autoThread;

	/**
	 * 开启
	 */
	public static void startSendMailThread() {
		stopMailThread();
		LogUtil.logI(className + " startSendMailThread");
		autoThread = new Thread("MailThread") {
			public void run() {
				try {
					while (true) {
						if (GuardService.guardService != null
								&& CustomConfiguration.isStartGuard()) {
 
							long lastTime = CustomConfiguration
									.getLastSendMailTime();
							long nowTime = System.currentTimeMillis();
							long intervalTime = nowTime - lastTime;
							if (intervalTime > GuardApplication.basicConfig.mailPeriod) {
								//先判断网络是否连接,没有开启则开启网络
								if(!GuardService.grpsUtil.isNetworkAvailable()){
									GuardService.grpsUtil.gprsEnabled(true);
								}
								Thread.sleep(30000);//休息30秒等待网络打开
								
								
								boolean isSend = GuardService.guardService
										.sendFaceMail();
								if (isSend) {
									CustomConfiguration
											.setLastSendMailiIme(nowTime);
								}
 
							} 
						}
						Thread.sleep(Const.CheckMailTimePeriod);
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
	public static void stopMailThread() {
		if (autoThread != null) {
			LogUtil.logI(className + " stopMailThread");
			autoThread.interrupt();
			autoThread = null;
		}
	}

}
