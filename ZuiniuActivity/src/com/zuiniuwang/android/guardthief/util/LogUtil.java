package com.zuiniuwang.android.guardthief.util;

import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.GuardApplication;

import android.util.Log;

public class LogUtil {

	public static final String LOG_TAG = Const.LOG_TAG;

	public static void logI(String value) {
		try {
			boolean isOpen = GuardApplication.basicConfig.isLogOpen;
//			boolean isOpen=true;
			
			if (isOpen) {
				Log.i(LOG_TAG, value);
			}
		} catch (Exception e) {
		}

	}

	public static void logE(String value) {
		try {
			boolean isOpen = GuardApplication.basicConfig.isLogOpen;
//			boolean isOpen=true;
			if (isOpen) {
				Log.e(LOG_TAG, value);
			}
		} catch (Exception e) {
		}

	}

}
