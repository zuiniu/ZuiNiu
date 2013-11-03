package com.zuiniuwang.android.guardthief.dialog;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.zuiniuwang.android.guardthief.R;

public class PopForHelpDialog {

	/*********** 页面变量 *********/
	private Context context;// 上下文

	ImageView image;
	private int resouceId = 0;

	ImageView know, unknow;
	/** 静态字段 */
	private SharedPreferences preferences = null;
	/** 静态字段 */
	SharedPreferences.Editor editor = null;

	/**
	 * 构造函数，采用默认值
	 * 
	 * @param context
	 */
	public PopForHelpDialog(final Context context,
			int resourceId) {
		this.context = context;
		this.resouceId = resourceId;

		preferences = context.getSharedPreferences(String.valueOf(resouceId),
				Context.MODE_WORLD_READABLE);
		this.editor = preferences.edit();
		mLayout = GetView();// 初始化页面布局
	}

	/** 创建AlertDialog */
	public Dialog BulidDialog() {
		this.dialog = new Dialog(context, R.style.popDialog);
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

	private void findviews() {
		LayoutInflater inflater = LayoutInflater.from(context);
		mLayout = inflater.inflate(R.layout.pop, null);
		image = (ImageView) mLayout.findViewById(R.id.image);
		image.setBackgroundResource(resouceId);


		know = (ImageView) mLayout.findViewById(R.id.know);
		unknow = (ImageView) mLayout.findViewById(R.id.unknow);
		know.setVisibility(View.GONE);
		unknow.setVisibility(View.GONE);
		
		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

}
