package com.zuiniuwang.android.guardthief.config;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.util.LogUtil;

import android.text.TextUtils;
import android.util.Log;

public class UserConfig extends GuardConfig {
	/** json标签名称 */
	private static String mJsonTag = Const.ConfigName_User;

	public List<UserConfiglBean> mList = new ArrayList<UserConfiglBean>();

	/** 获取配置 */
	public static UserConfig getUserConfig() {
		String json = getConfigFromSystem(mJsonTag);
		UserConfig config = null;
		if (!TextUtils.isEmpty(json)) {
			config = fromJson(json);
		} else {
			config = getDefaultConfig();
		}
		LogUtil.logI(className + " getUserConfig json:" + json);
		return config;
	}

	/** 默认配置 */
	private static UserConfig getDefaultConfig() {
		UserConfig config = new UserConfig();
		config.mList.add(new UserConfiglBean("", "BasicConfig", "MailConfig"));
		return config;
	}

	@Override
	protected String getTag() {
		return mJsonTag;
	}

	/** 从json获取邮件配置类 */
	private static UserConfig fromJson(String json) {
		UserConfig config = null;
		if (!TextUtils.isEmpty(json)) {
			try {
				config = new UserConfig();
				JSONArray array = new JSONArray(json);
				if (array != null) {
					config.mList = new ArrayList<UserConfiglBean>();
					for (int i = 0; i < array.length(); i++) {
						config.mList.add(UserConfiglBean.fromJson(array
								.getString(i)));
					}
				}

			} catch (Exception e) {
			}

		}
		return config;
	}

	/** 转换到json */
	public String toJson() {
		JSONArray array = new JSONArray();
		try {

			for (UserConfiglBean bean : mList) {
				array.put(bean.toJson());
			}
		} catch (Exception e) {
		}

		return String.valueOf(array);
	}

}
