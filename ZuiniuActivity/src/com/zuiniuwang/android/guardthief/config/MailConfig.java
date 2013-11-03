package com.zuiniuwang.android.guardthief.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;

import android.text.TextUtils;
import android.util.Log;

import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.util.CustomConfiguration;
import com.zuiniuwang.android.guardthief.util.LogUtil;

/**
 * 邮件配置
 * 
 * @author william
 * 
 */
public class MailConfig extends GuardConfig {
	/** json标签名称 */
	private static String mJsonTag = Const.ConfigName_Mail;
	/** 自定义配置列表 */
	public List<MailBean> mMailList = new ArrayList<MailBean>();

	private List<MailBean> mMailListForQQ = new ArrayList<MailBean>();
	private List<MailBean> mMailListFor163 = new ArrayList<MailBean>();

	/** 默认邮箱 */
	public static List<String> defaultUserNames = new ArrayList<String>();
	public static List<String> defaultUserNamesForQQ = new ArrayList<String>();
	static {
		defaultUserNames.clear();
		defaultUserNames.add("zuiniuyihaodefault");
		defaultUserNamesForQQ.clear();
		defaultUserNamesForQQ.add("97633077");
		
	}

	/** 获取配置 */
	public static MailConfig getMailConfig() {
		String json = getConfigFromSystem(mJsonTag);
		MailConfig config = null;
		if (!TextUtils.isEmpty(json)) {
			config = fromJson(json);
		} else {
			config = getDefaultConfig();
		}
		LogUtil.logI(className + " getMailConfig mJsonTag:" + mJsonTag
				+ " json:" + json);
		return config;
	}

	/**
	 * 获取配置的一个随即MailBean
	 * <P>
	 * 增加功能，需要区分qq邮箱和163邮箱
	 * 
	 * */
	public MailBean getRandomMailBean() {
		// 如果是qq邮箱
		if (CustomConfiguration.getEmail().contains("@qq.com")) {
			if (mMailListForQQ.size() > 0) {
				return mMailListForQQ.get(new Random().nextInt(mMailListForQQ
						.size()));
			}
		} else {
			if (mMailListFor163.size() > 0) {
				return mMailListFor163.get(new Random().nextInt(mMailListFor163
						.size()));
			}
		}
		return null;
	}

	/** 默认配置 */
	private static MailConfig getDefaultConfig() {

		MailConfig config = new MailConfig();
		config.mMailList.add(new MailBean(defaultUserNames, Const.BackMailPass,
				"smtp.163.com", "@163.com"));

		config.mMailList.add(new MailBean(defaultUserNamesForQQ, Const.BackMailPassForQQ,
				"smtp.qq.com", "@qq.com"));
		
		config.initMailType();
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
	private static MailConfig fromJson(String json) {
		MailConfig config = null;
		if (!TextUtils.isEmpty(json)) {
			try {
				config = new MailConfig();
				JSONArray array = new JSONArray(json);
				if (array != null) {
					config.mMailList = new ArrayList<MailBean>();
					for (int i = 0; i < array.length(); i++) {
						config.mMailList.add(MailBean.fromJson(array
								.getString(i)));
					}
					config.initMailType();
				}

			} catch (Exception e) {
				config = getDefaultConfig();
			}

		}
		return config;
	}

	/** 转换到json */
	public String toJson() {
		JSONArray array = new JSONArray();
		try {

			for (MailBean bean : mMailList) {
				array.put(bean.toJson());
			}
		} catch (Exception e) {
		}

		return String.valueOf(array);
	}

	/** 初始化邮箱，区分 163和qq,保存列表 */
	private void initMailType() {
		mMailListForQQ.clear();
		mMailListFor163.clear();
		if (mMailList != null && mMailList.size() > 0) {
			for (MailBean bean : mMailList) {
				if (bean.emailSuffix.contains("@qq.com")) {
					mMailListForQQ.add(bean);
				} else {
					mMailListFor163.add(bean);
				}

			}

		}
	}
}
