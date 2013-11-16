package com.zuiniuwang.android.guardthief.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.ui.EmailSetting;
import com.zuiniuwang.android.guardthief.ui.MainActivity;
import com.zuiniuwang.android.guardthief.ui.Navigation;
import com.zuiniuwang.android.guardthief.ui.PasswordSetting;
import com.zuiniuwang.android.guardthief.ui.PhoneSetting;
import com.zuiniuwang.android.guardthief.ui.Validate;

/** 页面跳转工具类 */
public class NavigationUtil {



	public static void gotoNextFade(Activity activity) {
		activity.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
	}

	/**
	 * 从logo页面跳转 从导航直接进入等不需要验证。 从logo直接进入需要验证
	 * 
	 * */
	public static void gotoMainPage(Context activity, boolean isNeedValidate) {
		// 如果已经导航过了
		if (CustomConfiguration.getIsNavigationed()) {
			if (CustomConfiguration.isSettedPassword()) {
				// 需要验证密码
				if (isNeedValidate) {
					activity.startActivity(new Intent(activity, Validate.class));
				} else {
					// 是否设置了mail
					if (CustomConfiguration.isSettedEmail()) {
						activity.startActivity(new Intent(activity,
								MainActivity.class));
					} else {
						activity.startActivity(new Intent(activity,
								EmailSetting.class));
					}

				}

			}
			// 没有设置密码，进入密码设置页面
			else {
				activity.startActivity(new Intent(activity,
						PasswordSetting.class));
			}// 判断是否设置了密码
		}
		// 如果没有导航过，进入导航页面
		else {
			activity.startActivity(new Intent(activity, Navigation.class));
		}

	}

}
