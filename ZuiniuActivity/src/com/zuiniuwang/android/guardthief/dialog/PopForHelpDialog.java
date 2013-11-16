package com.zuiniuwang.android.guardthief.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.zuiniuwang.android.guardthief.R;

public class PopForHelpDialog {

	/*********** 页面变量 *********/
	private Context mContext;// 上下文

	private DialogPicBean[] mDialogPicBeans;
	RelativeLayout picLayout;

	ImageView know, unknow;

	/**
	 * 构造函数，采用默认值
	 * 
	 * @param context
	 */
	public PopForHelpDialog(final Context context,
			DialogPicBean... mDialogPicBeans) {
		this.mContext = context;
		this.mDialogPicBeans = mDialogPicBeans;
		mLayout = GetView();// 初始化页面布局
	}
	/** 创建AlertDialog */
	public Dialog BulidDialog() {
		this.dialog = new Dialog(mContext, R.style.popDialogForHelp);
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
		LayoutInflater inflater = LayoutInflater.from(mContext);
		mLayout = inflater.inflate(R.layout.pop, null);
		picLayout = (RelativeLayout) mLayout.findViewById(R.id.layout);

		if (mDialogPicBeans != null) {
			for (DialogPicBean picBean : mDialogPicBeans) {
				ImageView image = new ImageView(mContext);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				params.addRule(RelativeLayout.CENTER_HORIZONTAL);
				params.topMargin = picBean.topMargin;
				image.setImageResource(picBean.resId);
				picLayout.addView(image, params);
			}
		}



		know = (ImageView) mLayout.findViewById(R.id.know);
		unknow = (ImageView) mLayout.findViewById(R.id.unknow);
		know.setVisibility(View.GONE);
		unknow.setVisibility(View.GONE);
		
		picLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

}
