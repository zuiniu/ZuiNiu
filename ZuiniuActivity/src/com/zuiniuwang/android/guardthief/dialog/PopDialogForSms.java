package com.zuiniuwang.android.guardthief.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.ui.Help;
import com.zuiniuwang.android.guardthief.util.NavigationUtil;

public class PopDialogForSms  {

	/*********** 页面变量 *********/
	private Context mContext;// 上下文
	private Handler handler;

	ImageView image;

	ImageView know, unknow;

	private int resouceId = 0;

	/** 静态字段 */
	private SharedPreferences preferences = null;
	/** 静态字段 */
	SharedPreferences.Editor editor = null;

	private String preferenceName = "";

	/**
	 * 构造函数，采用默认值
	 * 
	 * @param context
	 */
	public PopDialogForSms(final Context context, Handler handler, int resourceId) {
		this.mContext = context;
		this.handler = handler;
		this.resouceId = resourceId;
		preferenceName = resourceId + "name" + Const.VERSION;

		preferences = context.getSharedPreferences(preferenceName,
				Context.MODE_WORLD_READABLE);
		this.editor = preferences.edit();
		mLayout = GetView();// 初始化页面布局
	}

	/** 创建AlertDialog */
	public Dialog BulidDialog() {
		this.dialog = new Dialog(mContext, R.style.popDialog);
		dialog.setContentView(mLayout);
		 dialog.setCanceledOnTouchOutside(false);
		return dialog;
	}

	/** 获取页面布局 */
	private View GetView() {
		findviews();
		return mLayout;
	}

	/********** UI界面控件 **********/
	private View mLayout;
	private Dialog dialog;

	public Dialog getDialog() {
		return this.dialog;
	}

	private void findviews() {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		mLayout = inflater.inflate(R.layout.pop, null);
		image = (ImageView) mLayout.findViewById(R.id.image);
		image.setBackgroundResource(resouceId);

		know = (ImageView) mLayout.findViewById(R.id.know);
		unknow = (ImageView) mLayout.findViewById(R.id.unknow);

		know.setVisibility(View.GONE);
		unknow.setVisibility(View.GONE);
		
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				image.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

			}
		}, 3000);

	}

	/**
	 * 获取是否启动
	 * <P>
	 * 启动3次后算已经启动过
	 * 
	 * @return
	 */
	public boolean getIsStart() {
		int value = preferences.getInt(preferenceName, 0);
		if (value >= 3) {
			return true;
		} else {
			return false;
		}
	}

	public void setIsStart(boolean isStart) {

		int value = preferences.getInt(preferenceName, 0);
		value++;
		editor.putInt(preferenceName, value);
		editor.commit();
	}


}
