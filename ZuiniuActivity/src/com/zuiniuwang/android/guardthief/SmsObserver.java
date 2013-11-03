package com.zuiniuwang.android.guardthief;

import com.zuiniuwang.android.guardthief.bll.CameraStrategy;
import com.zuiniuwang.android.guardthief.bll.TimeStrategy;
import com.zuiniuwang.android.guardthief.ui.MainActivity;
import com.zuiniuwang.android.guardthief.util.CustomConfiguration;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

/**
 * 短信数据库监控
 * <P>
 * 收到短信后检查收件箱，判断是否存在指定的短信。
 * 
 * @author guoruiliang
 * 
 */
public class SmsObserver extends ContentObserver {
	private String LOG_TAG = Const.LOG_TAG;
	public String className = getClass().getSimpleName();
	private Context mContext;
	private static Uri SMS_INBOX = Uri.parse("content://sms/inbox");
	private static final String[] SMS_PROJECTION = new String[] { "_id",
			"thread_id", "address", "body" };

	String body;// 短信内容
	String address;// 发件人号码
	Cursor cursor;

	public SmsObserver(Context context, Handler handler) {
		super(handler);
		this.mContext = context;
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		Cursor cursor = null;
		try {
			cursor = mContext.getContentResolver().query(SMS_INBOX,
					SMS_PROJECTION, null, null, "date desc");
			if (cursor != null) {
				// ，因为是查询倒叙排列，所以查询第一条就是最近收到的
				if (cursor.moveToFirst()) {
					address = cursor
							.getString(cursor.getColumnIndex("address"))
							.toString();
					body = cursor.getString(cursor.getColumnIndex("body"))
							.toString();
					int id = cursor.getInt(cursor.getColumnIndex("_id"));

					long threadId = cursor.getLong(cursor
							.getColumnIndex("thread_id"));

					Log.v(LOG_TAG, className + " receive  message: from:"
							+ address + " body:" + body + " threadId:"
							+ threadId);

					/** 判断是最牛指令 */
					if (body.startsWith("#") && body.endsWith("#")) {

						doZuiniuAction(body, id);

					}

				}

			}
		} catch (Exception e) {
			Log.e(LOG_TAG, className + "  error：" + e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
				cursor = null;
			}
		}
	}

	private void doZuiniuAction(String body, int id) {

		if (body.startsWith("#zuiniu@")) {
			mContext.sendBroadcast(new Intent(body));
			mContext.sendBroadcast(new Intent(MainActivity.ACTION_REFRESH));
		}

	}

}
