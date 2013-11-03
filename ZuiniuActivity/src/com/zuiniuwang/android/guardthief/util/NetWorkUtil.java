package com.zuiniuwang.android.guardthief.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.ClientProtocolException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;

import com.zuiniuwang.android.guardthief.Const;

public class NetWorkUtil {

	private static String LOG_TAG = Const.LOG_TAG;
	protected static String className = NetWorkUtil.class.getSimpleName();

	// 建立连接超时时间
	private static int ConnectionTimeout = 20000;

	/**
	 * 根据uri地址获取inputstream
	 * 
	 * @author William 2010-12-28
	 * @param urlStr
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static InputStream getInputStreamFromNet(String urlPath)
			throws Exception {
		URL url = new URL(urlPath);
		URLConnection conn;
		conn = url.openConnection();
		conn.setConnectTimeout(ConnectionTimeout);
		conn.connect();
		InputStream in = conn.getInputStream();

		return in;

	}

	public static String getStringFromNet(String url) throws Exception {
		Log.i(LOG_TAG, className + " getStringFromNet:" + url);
		return getStringFromNet(getInputStreamFromNet(url));
	}

	/**
	 * 从输入流获取文字
	 * 
	 * @param is
	 *            输入流
	 * @return 字符
	 * @throws NetWorkException
	 *             网络异常
	 */
	public static String getStringFromNet(InputStream is) throws Exception {
		StringBuffer buffer = new StringBuffer();
		try {
			if (is != null) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));
				String line;
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
					// buffer.append("\n");
				}
			}
			return buffer.toString();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				is.close();
				is = null;
			} catch (Exception e) {
			}
		}
	}

	/***
	 * 判断网络是否连接
	 */
	public static boolean isNetWorkEnable(Context context) {
		try {
			ConnectivityManager manager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = manager.getActiveNetworkInfo();
			if (info != null && info.isAvailable()) {
				return true;

			}
		} catch (Exception e) {
		}

		return false;
	}

	/**
	 * 发送短信
	 * 
	 * @param address
	 * @param text
	 */
	public static void sendSms(String address, String text) {
		if (!TextUtils.isEmpty(address)) {
			try {
				SmsManager sms = SmsManager.getDefault();
				sms.sendTextMessage(address, "", text, null, null);
			} catch (Exception e) {
			}
		}

	}

}
