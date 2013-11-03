package com.zuiniuwang.android.guardthief.util;

import java.lang.reflect.Method;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class GprsUtil {
	private ConnectivityManager mCM;

	public GprsUtil(Context context) {
		this.mCM = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	// 打开或关闭GPRS
	public void gprsEnabled(final boolean bEnable) {
		new Thread() {
			public void run() {
				boolean isOpen = false;
				try {
					Object[] argObjects = null;

					isOpen = gprsIsOpenMethod("getMobileDataEnabled");
					if (isOpen == !bEnable) {
						setGprsEnabled("setMobileDataEnabled", bEnable);
					}
				} catch (Exception e) {
				}

			}
		}.start();

	}

	
	
	
	// 检测GPRS是否打开
	private boolean gprsIsOpenMethod(String methodName) {
		Class cmClass = mCM.getClass();
		Class[] argClasses = null;
		Object[] argObject = null;

		Boolean isOpen = false;
		try {
			Method method = cmClass.getMethod(methodName, argClasses);

			isOpen = (Boolean) method.invoke(mCM, argObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("yy", "isopen:" + isOpen);
		return isOpen;
	}

	// 开启/关闭GPRS
	private void setGprsEnabled(String methodName, boolean isEnable) {
		Class cmClass = mCM.getClass();
		Class[] argClasses = new Class[1];
		argClasses[0] = boolean.class;

		try {
			Method method = cmClass.getMethod(methodName, argClasses);
			method.invoke(mCM, isEnable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/** 网络是否连接 */
	public boolean isNetworkAvailable() {
		NetworkInfo[] infos = mCM.getAllNetworkInfo();
		for (NetworkInfo info : infos) {
			if (info.isConnected()) {
				return true;
			}
		}
		return false;
	}
}
