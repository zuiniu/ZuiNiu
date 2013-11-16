package com.zuiniuwang.android.guardthief.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.baidu.BaiduLocation;
import com.zuiniuwang.android.guardthief.util.PreferenceUtil.Enum_ShootPreference;

/**
 * 通用工具类
 * <P>
 * 邮箱和密码验证保存
 * <P>
 * 弹出文字等
 * 
 * @author guoruiliang
 * 
 */
public class CustomConfiguration {

	public static boolean getIsStartGprsOnSendMail() {
		SharedPreferences p = PreferenceManager
				.getDefaultSharedPreferences(GuardApplication.appContext);
		return p.getBoolean("pref_gprs", false);
	}
	
	/** 获取口袋间隔 */
	public static long getPocketTime() {
		SharedPreferences p = PreferenceManager
				.getDefaultSharedPreferences(GuardApplication.appContext);
		
		String value = p.getString("pref_pocket", "8000");
		return Long.parseLong(value);
	}

	/** 获取邮件发送频率 */
	public static long getEmailFreequncy() {
		SharedPreferences p = PreferenceManager
				.getDefaultSharedPreferences(GuardApplication.appContext);
		
		String value = p.getString("pref_frenquncy", "28800000");
		return Long.parseLong(value);
	}

	/**
	 * 是否Pocket模式
	 */
	public static boolean getIsPocketOn() {
		String value = PreferenceUtil.getInstance(GuardApplication.appContext)
				.getPreference(Enum_ShootPreference.POCKETMODE);
		return (Boolean.valueOf(value));
	}

	/**
	 * 设置是否Pocket模式
	 * <P>
	 */
	public static void setIsPocketOn(boolean start) {
		PreferenceUtil.getInstance(GuardApplication.appContext).setPreference(
				Enum_ShootPreference.POCKETMODE, String.valueOf(start));

	}

	/**
	 * 是否是root
	 */
	public static boolean getIsRoot() {
		String value = PreferenceUtil.getInstance(GuardApplication.appContext)
				.getPreference(Enum_ShootPreference.ISROOT);
		return (Boolean.valueOf(value));
	}

	/**
	 * 设置是否是root
	 * <P>
	 */
	public static void setIsRoot(boolean value) {
		PreferenceUtil.getInstance(GuardApplication.appContext).setPreference(
				Enum_ShootPreference.ISROOT, String.valueOf(value));
	}

	/**
	 * 是否已经通知过期
	 */
	public static boolean getIsNotifyVersionOut() {
		String value = PreferenceUtil.getInstance(GuardApplication.appContext)
				.getPreference(Enum_ShootPreference.ISNOTIFY_OUTOFDATE);
		return (Boolean.valueOf(value));
	}

	/**
	 * 设置是否已经通知过期
	 * <P>
	 */
	public static void setIsNotifyVersiontOut(boolean value) {
		Log.i(Const.LOG_TAG, "Commutil ISNOTIFY_OUTOFDATE :" + value);
		PreferenceUtil.getInstance(GuardApplication.appContext).setPreference(
				Enum_ShootPreference.ISNOTIFY_OUTOFDATE, String.valueOf(value));
	}

	/**
	 * 获取上次邮件发送时间
	 */
	public static long getLastSendMailTime() {
		String value = PreferenceUtil.getInstance(GuardApplication.appContext)
				.getPreference(Enum_ShootPreference.LAST_SENDMAIL_TIME);
		return Long.parseLong(value);
	}

	/**
	 * 设置上次邮件发送时间
	 * <P>
	 */
	public static void setLastSendMailiIme(long count) {
		PreferenceUtil.getInstance(GuardApplication.appContext).setPreference(
				Enum_ShootPreference.LAST_SENDMAIL_TIME, String.valueOf(count));
	}

	/**
	 * 获取录音次数
	 */
	public static int getRecordCount() {
		String value = PreferenceUtil.getInstance(GuardApplication.appContext)
				.getPreference(Enum_ShootPreference.RECORDCOUNT);
		return Integer.parseInt(value);
	}

	/**
	 * 设置录音次数
	 * <P>
	 */
	public static void setRecordCount(int count) {
		Log.i(Const.LOG_TAG, "Commutil setRecordCount :" + count);
		PreferenceUtil.getInstance(GuardApplication.appContext).setPreference(
				Enum_ShootPreference.RECORDCOUNT, String.valueOf(count));
	}

	/**
	 * 是否隐藏图标
	 */
	public static boolean getIsHideIcon() {
		String value = PreferenceUtil.getInstance(GuardApplication.appContext)
				.getPreference(Enum_ShootPreference.HIDEICON);
		return (Boolean.valueOf(value));
	}

	/**
	 * 设置是否隐藏图标
	 * <P>
	 */
	public static void setIsHideIcon(boolean start) {
		Log.i(Const.LOG_TAG, "Commutil setIsHideIcon :" + start);
		PreferenceUtil.getInstance(GuardApplication.appContext).setPreference(
				Enum_ShootPreference.HIDEICON, String.valueOf(start));
	}

	/**
	 * 是否导航完毕
	 */
	public static boolean getIsNavigationed() {
		String value = PreferenceUtil.getInstance(GuardApplication.appContext)
				.getPreference(Enum_ShootPreference.ISNAVIGATIONED);
		return (Boolean.valueOf(value));
	}

	/**
	 * 设置 是否导航完毕
	 */
	public static void setIsNavigationed(boolean value) {
		PreferenceUtil.getInstance(GuardApplication.appContext).setPreference(
				Enum_ShootPreference.ISNAVIGATIONED, String.valueOf(value));
	}

	/**
	 * 设置IMEI
	 */
	public static void setImsi(String imei) {
		PreferenceUtil.getInstance(GuardApplication.appContext).setPreference(
				Enum_ShootPreference.IMSI, imei);
	}

	/** 获取保存的IMEI */
	public static String getImsi() {
		return PreferenceUtil.getInstance(GuardApplication.appContext)
				.getPreference(Enum_ShootPreference.IMSI);
	}

	/**
	 * 是否开启监控
	 */
	public static boolean isStartGuard() {
		String value = PreferenceUtil.getInstance(GuardApplication.appContext)
				.getPreference(Enum_ShootPreference.ISSTART_GUARD);
		return (Boolean.valueOf(value));
	}

	/**
	 * 设置是否开启监控
	 * <P>
	 */
	public static void setIsStartGuard(boolean start) {
		Log.i(Const.LOG_TAG, "Commutil setIsStartGuard :" + start);
		PreferenceUtil.getInstance(GuardApplication.appContext).setPreference(
				Enum_ShootPreference.ISSTART_GUARD, String.valueOf(start));

		if (start) {
			BaiduLocation.freshTime = Const.LOCATION_REFRESH_GAP;

		} else {
			BaiduLocation.freshTime = Const.LOCATION_REFRESH_GAP_STOP;
		}
		GuardApplication.mBaiduLocation.refresh();
	}

	/**
	 * 验证密码是否正确
	 */
	public static boolean isPasswordCorrect(String password) {
		String savePass = getPassword();
		if (savePass.equalsIgnoreCase(password)) {
			return true;
		}
		return false;
	}

	/** 获取保存的密码 */
	public static String getPassword() {
		return PreferenceUtil.getInstance(GuardApplication.appContext)
				.getPreference(Enum_ShootPreference.PASSWORD);
	}

	/**
	 * 保存密码
	 */
	public static void savePassword(String password) {
		PreferenceUtil.getInstance(GuardApplication.appContext).setPreference(
				Enum_ShootPreference.PASSWORD, password);
	}

	/**
	 * 是否设置了密码
	 */
	public static boolean isSettedPassword() {
		String password = getPassword();
		if (TextUtils.isEmpty(password)) {
			return false;
		}
		return true;
	}

	/**
	 * 确认字符串是否为email格式
	 */
	public static boolean isEmailCorrect(String strEmail) {
		String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		return m.matches();
	}

	/**
	 * 确认字符串是否为Phone格式
	 */
	public static boolean isPhoneCorrect(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,1-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 是否设置了邮件
	 */
	public static boolean isSettedEmail() {
		String email = getEmail();
		if (TextUtils.isEmpty(email)) {
			return false;
		}
		return true;
	}

	/** 保存邮箱地址 */
	public static void saveEmail(String email) {
		PreferenceUtil.getInstance(GuardApplication.appContext).setPreference(
				Enum_ShootPreference.EMAIL, email);
	}

	/** 获取保存的邮箱地址 */
	public static String getEmail() {
		return PreferenceUtil.getInstance(GuardApplication.appContext)
				.getPreference(Enum_ShootPreference.EMAIL);
	}

	/**
	 * 是否设置了号码
	 */
	public static boolean isSettedPhone() {
		String phone = getPhone();
		if (TextUtils.isEmpty(phone)) {
			return false;
		}
		return true;
	}

	/** 保存用户号码 */
	public static void savePhone(String phone) {
		PreferenceUtil.getInstance(GuardApplication.appContext).setPreference(
				Enum_ShootPreference.Phone, phone);
	}

	/** 获取用户号码 */
	public static String getPhone() {
		return PreferenceUtil.getInstance(GuardApplication.appContext)
				.getPreference(Enum_ShootPreference.Phone);
	}

	/** 保存配置信息 */
	public static void saveConfig(String value) {
		PreferenceUtil.getInstance(GuardApplication.appContext).setPreference(
				Enum_ShootPreference.UMENG, value);
	}

	/** 获取配置信息 */
	public static String getConfig() {
		return PreferenceUtil.getInstance(GuardApplication.appContext)
				.getPreference(Enum_ShootPreference.UMENG);
	}

	/** 保存配置信息 */
	public static void saveCode(String value) {
		PreferenceUtil.getInstance(GuardApplication.appContext).setPreference(
				Enum_ShootPreference.PEMISSIONCODE, value);
	}

	/** 获取配置信息 */
	public static String getCode() {
		return PreferenceUtil.getInstance(GuardApplication.appContext)
				.getPreference(Enum_ShootPreference.PEMISSIONCODE);
	}

	public static boolean isAllSetted() {
		if (!isSettedEmail() || !isSettedPassword()) {
			return false;
		}
		return true;
	}

	/** 获取通知 */
	public static String getNotification() {
		return PreferenceUtil.getInstance(GuardApplication.appContext)
				.getPreference(Enum_ShootPreference.NOTIFICATION);
	}

	/**
	 * 保存通知
	 */
	public static void saveNotification(String notification) {
		PreferenceUtil.getInstance(GuardApplication.appContext).setPreference(
				Enum_ShootPreference.NOTIFICATION, notification);
	}
}
