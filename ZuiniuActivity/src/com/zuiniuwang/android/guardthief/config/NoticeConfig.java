package com.zuiniuwang.android.guardthief.config;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.util.LogUtil;

import android.text.TextUtils;
import android.util.Log;

public class NoticeConfig extends GuardConfig {
	/** json标签名称 */
	private static String mJsonTag = Const.ConfigName_Notice;

	public List<NoticeBean> mList = new ArrayList<NoticeBean>();

	/** 获取配置 */
	public static NoticeConfig getNoticeConfig() {
		String json = getConfigFromSystem(mJsonTag);
		NoticeConfig config = null;
		if (!TextUtils.isEmpty(json)) {
			config = fromJson(json);
		} else {
			config = getDefaultConfig();
		}
		LogUtil.logI(className + " getNoticeConfig json:" + json);
		return config;
	}

	/** 默认配置 */
	private static NoticeConfig getDefaultConfig() {
		NoticeConfig config = new NoticeConfig();
		return config;
	}

	@Override
	protected String getTag() {
		return mJsonTag;
	}

	/** 从json获取邮件配置类 */
	private static NoticeConfig fromJson(String json) {
		NoticeConfig config = null;
		if (!TextUtils.isEmpty(json)) {
			try {
				config = new NoticeConfig();
				JSONArray array = new JSONArray(json);
				if (array != null) {
					config.mList = new ArrayList<NoticeBean>();
					for (int i = 0; i < array.length(); i++) {
						config.mList.add(NoticeBean.fromJson(array
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

			for (NoticeBean bean : mList) {
				array.put(bean.toJson());
			}
		} catch (Exception e) {
		}

		return String.valueOf(array);
	}

}
