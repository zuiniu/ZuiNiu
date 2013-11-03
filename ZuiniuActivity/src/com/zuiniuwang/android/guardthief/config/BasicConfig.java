package com.zuiniuwang.android.guardthief.config;

import org.json.JSONObject;

import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.util.LogUtil;

import android.text.TextUtils;
import android.util.Log;

public class BasicConfig extends GuardConfig {
	/** json标签名称 */
	private static String mJsonTag = Const.ConfigName_Basic;

	/** 邮件标题 */
	public String emailSubject = "";

	/** 开启屏幕时两次扫描的间隔时间，测试可以60秒，正式最好10分钟以上。屏幕关闭后无效 */
	public long guardPeriod = Const.GuardPeriod;

	/** 系统保存的最大的人脸拍摄数目，到达后就不会再拍了 */
	public long maxPic = Const.MaxPic;

	/** 邮件间隔发送时间 */
	public long mailPeriod = Const.MailPeriod;

	/** 最大录音size */
	public long maxRecordSize = Const.MaxRecordSize;

	/** 默认录音次数 */
	public long maxRecordCount = Const.MaxRecordCount;

	/** 日志是否打开 */
	public boolean isLogOpen = false;

	/** 图片在邮件内容显示 */
	public boolean photoShow = false;

	/** 通知信息 */
	public String notification = "";

	/** 获取配置 */
	public static BasicConfig getBasicConfig() {
		String json = getConfigFromSystem(mJsonTag);
		BasicConfig config = null; 
		if (!TextUtils.isEmpty(json)) {
			config = fromJson(json);
		} else {
			config = getDefaultConfig();
		}
		LogUtil.logI(className + " getBasicConfig mJsonTag:" + mJsonTag
				+ " json:" + json);
		return config;
	}

	/** 默认配置 */
	private static BasicConfig getDefaultConfig() {
		BasicConfig config = new BasicConfig();
		config.emailSubject = "您手机位置的防盗信息-最牛一号";
		return config;
	}

	@Override
	protected String getTag() {
		return mJsonTag;
	}

	public static void setTag(String tag) {
		mJsonTag = tag;
	}
 
	/** 从json获取邮件配置类 */
	private static BasicConfig fromJson(String json) {
		BasicConfig config = null;
		if (!TextUtils.isEmpty(json)) {
			try {
		 		JSONObject o = new JSONObject(json);
				config = new BasicConfig();
				config.emailSubject = o.optString("emailSubject");
				config.guardPeriod = o.optLong("guardPeriod") != 0 ? o
						.optLong("guardPeriod") : Const.GuardPeriod;
				config.maxPic = o.optLong("maxPic") != 0 ? o.optLong("maxPic")
						: Const.MaxPic;
				config.mailPeriod = o.optLong("mailPeriod") != 0 ? o
						.optLong("mailPeriod") : Const.MailPeriod;
				config.maxRecordCount = o.optLong("maxRecordCount") != 0 ? o
						.optLong("maxRecordCount") : Const.MaxRecordCount;
				config.isLogOpen = o.optBoolean("isLogOpen");
				config.photoShow = o.optBoolean("photoShow");
				config.notification = o.optString("notification");

				config.maxRecordSize = o.optLong("maxRecordSize") != 0 ? o
						.optLong("maxRecordSize") : Const.MaxRecordSize;

			} catch (Exception e) {
				config=getDefaultConfig();
			}

		}
		return config;
	}

	/** 转换到json */
	public String toJson() {
		JSONObject o = new JSONObject();
		try {
			o.put("emailSubject", String.valueOf(emailSubject));
			o.put("guardPeriod", guardPeriod);
			o.put("maxPic", maxPic);
			o.put("mailPeriod", mailPeriod);
			o.put("maxRecordCount", maxRecordCount);
			o.put("isLogOpen", isLogOpen);
			o.put("photoShow", photoShow);
			o.put("notification", notification);
			o.put("maxRecordSize", maxRecordSize);

		} catch (Exception e) {
		}

		return String.valueOf(o);
	}

}
