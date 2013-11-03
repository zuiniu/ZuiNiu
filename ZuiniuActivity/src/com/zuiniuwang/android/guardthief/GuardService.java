package com.zuiniuwang.android.guardthief;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.zuiniuwang.android.guardthief.bean.FeedBackBean;
import com.zuiniuwang.android.guardthief.bean.PhotoBean;
import com.zuiniuwang.android.guardthief.bll.CameraStrategy;
import com.zuiniuwang.android.guardthief.bll.TimeStrategy;
import com.zuiniuwang.android.guardthief.config.MailBean;
import com.zuiniuwang.android.guardthief.mail.MailManager;
import com.zuiniuwang.android.guardthief.receiver.ScreenReceiver;
import com.zuiniuwang.android.guardthief.receiver.SmsCommandReceiver;
import com.zuiniuwang.android.guardthief.receiver.StrategyReceiver;
import com.zuiniuwang.android.guardthief.util.CustomConfiguration;
import com.zuiniuwang.android.guardthief.util.FileUtil;
import com.zuiniuwang.android.guardthief.util.GprsUtil;
import com.zuiniuwang.android.guardthief.util.LogUtil;
import com.zuiniuwang.android.guardthief.util.NetWorkUtil;

/**
 * 防盗助手后台服务
 * <P>
 * 开机自动运行，注册广播，短信监控。
 * <P>
 * 提供基础公共方法调用
 * 
 * @author guoruiliang
 * 
 */
public class GuardService extends Service {
	private String LOG_TAG = Const.LOG_TAG;
	public String className = getClass().getSimpleName();

	private Context mContext;
	private ScreenReceiver screenReceiver;
	private StrategyReceiver mStrategyReceiver;
	private SmsCommandReceiver mSmsCommandReceiver;

	private SmsObserver smsObserver;
	Handler handler = new Handler();
	public static GuardService guardService;

	public static GprsUtil grpsUtil;

	/** 默认邮箱 */
	List<String> userNames = new ArrayList<String>();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(Const.LOG_TAG, className + " onCreate");
		this.mContext = GuardService.this;
		guardService = this;
		grpsUtil = new GprsUtil(guardService);

		registerReceiverAndSmsObserver();
		judgeChangeSim();
		// 初始化默认邮箱
		userNames.clear();
		userNames.add("zuiniuyihaoback");
		/** 开启自动发邮件线程 */
		SendMailThread.startSendMailThread();

	}

	// 判断是否换卡
	public void judgeChangeSim() {
		new Thread() {
			public void run() {
				// 停止3分钟以后再判断，防止sim卡未读取
				SystemClock.sleep(180000);
				TelephonyManager tm = (TelephonyManager) mContext
						.getSystemService(Context.TELEPHONY_SERVICE);
				String imsi = tm.getSubscriberId();
				Log.i(LOG_TAG, className + " get imsi:" + imsi);
				if (!TextUtils.isEmpty(imsi)) {
					String oldImei = CustomConfiguration.getImsi();
					if (TextUtils.isEmpty(oldImei)) {
						CustomConfiguration.setImsi(imsi);
					} else {
						if (!imsi.equalsIgnoreCase(oldImei)) {
							Log.i(LOG_TAG, className + " Change imsi:" + imsi);
							CustomConfiguration.setImsi(imsi);
							CustomConfiguration.setIsStartGuard(true);
							// 换卡了，发一条短信到指定号码
							NetWorkUtil.sendSms(CustomConfiguration.getPhone(),
									getString(R.string.changesim_sendmessage));

						}
					}
				}
				// 如果用户sim卡没有获取，那么对比以前保存的，如果有，说明拔卡了，一样开启
				// 飞行模式下读取不了信息，所以这步很有问题，先注释掉
				else {
					// String oldImei = CustomConfiguration.getImsi();
					// if (!TextUtils.isEmpty(oldImei)) {
					// CustomConfiguration.setIsStartGuard(true);
					// }
				}
			}
		}.start();
	}

	// 注册广播和监控
	private void registerReceiverAndSmsObserver() {
		screenReceiver = new ScreenReceiver(mContext);
		screenReceiver.register();
		mStrategyReceiver = new StrategyReceiver(mContext);
		mStrategyReceiver.register();

		mSmsCommandReceiver = new SmsCommandReceiver(mContext);
		mSmsCommandReceiver.register();

		smsObserver = new SmsObserver(GuardService.this, handler);
		getContentResolver().registerContentObserver(
				Uri.parse("content://sms/"), true, smsObserver);
	}

	@Override
	public void onDestroy() {
		Log.i(Const.LOG_TAG, className + " onDestroy");
		super.onDestroy();
		unRegisterReceiverAndObserver();

		Intent localIntent = new Intent();
		localIntent.setClass(this, GuardService.class); // 销毁时重新启动Service
		this.startService(localIntent);

	}

	// 撤销广播和监控
	private void unRegisterReceiverAndObserver() {
		if (screenReceiver != null) {
			screenReceiver.unRegister();
			screenReceiver = null;
		}
		if (mStrategyReceiver != null) {
			mStrategyReceiver.unRegister();
			mStrategyReceiver = null;
		}

		if (mSmsCommandReceiver != null) {
			mSmsCommandReceiver.unRegister();
			mSmsCommandReceiver = null;
		}

		getContentResolver().unregisterContentObserver(smsObserver);
	}

	/**
	 * 开始偷拍
	 */
	public void startToShootFace() {
		LogUtil.logI(className + " startToShootFace");
		TimeStrategy.SCREENOPEN_DELAY_20.sendTimeToGuardCommand(
				getApplicationContext(), CameraStrategy.FrontWithFace);

	}

	/**
	 * 发送反馈信息
	 */
	public void sendFeedBackMail() {
		List<FeedBackBean> feedbacks = FeedBackBean.getFeedBacks();
		if (feedbacks.size() <= 0) {
			return;
		}
		String subject = "用户反馈信息";

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		StringBuffer content = new StringBuffer();
		for (FeedBackBean back : feedbacks) {
			subject = back.userEmail + "的反馈信息";
			content.append("userName:" + back.userEmail + "  ");
			content.append("content:" + back.content + "  ");
			content.append("time:" + dateFormat.format(back.time) + "  ");
			content.append("contact:" + back.contact + "  ");

			content.append("\n");
		}

		MailBean mailBean = new MailBean(userNames, Const.BackMailPass,
				"smtp.163.com", "@163.com");
		MailManager sender = new MailManager(Const.FeedBackMail, subject,
				String.valueOf(content), null, mailBean);
		boolean value = sender.sendTextMail();
		if (value) {
			FeedBackBean.clear();
		}
		LogUtil.logI(className + " sendFeedBackMail:" + value + " content:"
				+ String.valueOf(content));
	}

	/** 发送拍到的图片 */
	public synchronized boolean sendFaceMail() {

		String address = CustomConfiguration.getEmail();
		if (TextUtils.isEmpty(address)) {
			return false;
		}

		List<PhotoBean> photos = PhotoBean.checkExist();
		if (photos.size() <= 0) {
			return false;
		}

		String subject = GuardApplication.basicConfig.emailSubject
				+ Const.VERSION;

		MailBean mailBean = GuardApplication.mailConfig.getRandomMailBean();

		LogUtil.logI("mail:" + String.valueOf(mailBean));

		if (mailBean != null) {
			MailManager sender = new MailManager(address, subject, "", photos,
					mailBean);

			boolean result = sender.sendHtmlMail();

			// 如果发送成功，清理
			if (result) {
				setShootFaceAndSendMailSuccessed();
			}
			return result;
		}
		return false;

	}

	/**
	 * 全部流程走完后的清理工作
	 */
	public void setShootFaceAndSendMailSuccessed() {
		FileUtil.clearGuardPic();// 清空拍到的Face
		FileUtil.clearRecords();
		PhotoBean.clear();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

}
