package com.zuiniuwang.android.guardthief.config;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.onlineconfig.UmengOnlineConfigureListener;
import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.util.CustomConfiguration;
import com.zuiniuwang.android.guardthief.util.LogUtil;
import com.zuiniuwang.android.guardthief.util.PreferenceUtil;
import com.zuiniuwang.android.guardthief.util.PreferenceUtil.Enum_ShootPreference;

/**
 * 配置
 * 
 * @author william
 * 
 */
public class GuardConfig {
	protected static final String LOG_TAG = Const.LOG_TAG;
	protected static String className = GuardConfig.class.getSimpleName();

	/** 系统保存的配置信息 */
	protected static JSONObject mJsonObject;

	/** 是否有配置 */
	protected static boolean isHasConfig = false;
	/** 最后更新日期 */
	protected static long lastUpdateDate = 0;

	/** 更新方法 */
	protected static IUpdateConfig mIUpdateConfig = new IUpdateConfig() {

		@Override
		public void updateGuardConfig(Context context,
				final IUpdateConfigCallBack callBack) {
			MobclickAgent.updateOnlineConfig(context);
			MobclickAgent
					.setOnlineConfigureListener(new UmengOnlineConfigureListener() {
						@Override
						public void onDataReceived(JSONObject data) {
							
							LogUtil.logI(" umeng update:" + String.valueOf(data));
							callBack.onGuardConfigDateReceived(data);
						}
					});
		}
	};

	/** 标签 */
	protected String getTag() {
		return getClass().getSimpleName();
	}

	/** 获取当前配置信息 */
	protected static String getConfigFromSystem(String mJsonTag) {
		if (mJsonObject == null) {
			refreshConfig();
		}

		if (mJsonObject != null) {
			return mJsonObject.optString(mJsonTag);
		}
		return "";
	}

	/** 需要子类重写，生成配置时使用 */
	public String toJson() {
		return getConfigFromSystem(getTag());
	}

	private static void refreshConfig() {
		String json = CustomConfiguration.getConfig();

		if (!TextUtils.isEmpty(json)) {
			isHasConfig = true;
			lastUpdateDate = System.currentTimeMillis();
			try {
				mJsonObject = new JSONObject(json);
			} catch (JSONException e) {
				Log.e(LOG_TAG, className + " GuardConfig error", e);
			}
		} else {
			isHasConfig = false;
		}
	}

	/** 从服务器更新配置 */
	public static void UpdateConfig(Context context,
			final Runnable successCallBack) {
		if (mIUpdateConfig != null) {
			mIUpdateConfig.updateGuardConfig(context,
					new IUpdateConfigCallBack() {

						@Override
						public void onGuardConfigDateReceived(JSONObject data) {
							if (data == null) {
								return;
							}
							String json = String.valueOf(data);
							if (!TextUtils.isEmpty(json)) {
								CustomConfiguration.saveConfig(json);
								LogUtil.logI( className
										+ " updateConfig sucess");
								refreshConfig();// 更新成功，刷新配置
								if (successCallBack != null) {
									successCallBack.run();
								}
							}
						}
					});
		}

	}

	/** 测试使用，清空数据 */
	protected static void clearPrferForTest() {
		CustomConfiguration.saveConfig("");
		isHasConfig = false;
		mJsonObject = null;
		lastUpdateDate = 0;
	}

	/** 更新接口 */
	protected interface IUpdateConfig {
		void updateGuardConfig(Context context, IUpdateConfigCallBack callBack);
	}

	/** 更新接口 */
	protected interface IUpdateConfigCallBack {
		void onGuardConfigDateReceived(JSONObject data);
	}

	/**
	 * 配置生成器
	 * 
	 * @author william
	 * 
	 */
	public static class ConfigBuilder {
		JSONObject mJsonObjcet;

		public ConfigBuilder() {
			mJsonObjcet = new JSONObject();
		}

		public ConfigBuilder add(GuardConfig config) {
			try {
				mJsonObjcet.put(config.getTag(), config.toJson());
			} catch (Exception e) {
			}
			return this;

		}

		public JSONObject getJson() {
			return this.mJsonObjcet;
		}
	}
}
