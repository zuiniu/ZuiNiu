package com.zuiniuwang.android.guardthief.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;

import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.ui.Help;
import com.zuiniuwang.android.guardthief.util.NavigationUtil;

public class PopDialog implements View.OnClickListener {

	/*********** 页面变量 *********/
	private Context mContext;// 上下文
	RelativeLayout picLayout;
	ImageView know, unknow;

	/** 静态字段 */
	private SharedPreferences preferences = null;
	/** 静态字段 */
	SharedPreferences.Editor editor = null;

	private String preferenceName = "";

	
	private DialogPicBean[]	 mDialogPicBeans;

	
	public PopDialog(final Context context, Handler handler, DialogPicBean... mDialogPicBeans) {
		this.mContext = context;
		this.mDialogPicBeans = mDialogPicBeans;
		preferenceName = mDialogPicBeans[0].resId + "name" + Const.VERSION;

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

		picLayout=(RelativeLayout)mLayout.findViewById(R.id.layout);
		
		if(mDialogPicBeans!=null){
			for(DialogPicBean picBean:mDialogPicBeans){
				ImageView image=new ImageView(mContext);
				RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				params.addRule(RelativeLayout.CENTER_HORIZONTAL);
				params.topMargin=picBean.topMargin;
				image.setImageResource(picBean.resId);
				picLayout.addView(image,params);
			}
		}
	
		
		
		
		know = (ImageView) mLayout.findViewById(R.id.know);
		unknow = (ImageView) mLayout.findViewById(R.id.unknow);

		know.setOnClickListener(this);
		unknow.setOnClickListener(this);

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
		if (value >= 1) {
			return true;
		} else {
			return false;
		}
	}

	private void setIsStart(boolean isStart) {

		int value = preferences.getInt(preferenceName, 0);
		value++;
		editor.putInt(preferenceName, value);
		editor.commit();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.know:
			setIsStart(true);
			break;
		case R.id.unknow:
			mContext.startActivity(new Intent(mContext, Help.class));
			break;
		default:
			break;
		}
		dialog.dismiss();
	}

}
