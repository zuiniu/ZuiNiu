package com.zuiniuwang.android.guardthief.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.util.DensityUtil;

public class OutDateDialogBuilder {

	/*********** 页面变量 *********/
	private Context mContext;// 上下文

	private Button buyButton, commentButton, cancelButton;

	TextView progressDesc;

	ProgressBar progress;

	private View layout;
	private Dialog mDialog;

	public static final String MSTORE_URI_HEADER = "mstore:";
	public static final String SOFTWARE_URL_BASE = "http://app.meizu.com/phone/apps/";

	/**
	 * 构造函数，采用默认值
	 * 
	 * @param context
	 */
	public OutDateDialogBuilder(final Context context) {
		this.mContext = context;
		layout = GetView();// 初始化页面布局
	}

	/** 创建AlertDialog */
	public Dialog BulidDialog() {
		this.mDialog = new Dialog(mContext, R.style.dialog);
		mDialog.setContentView(layout);
		mDialog.setCancelable(false);
		Window w = mDialog.getWindow();
		WindowManager.LayoutParams wl = w.getAttributes();

		DisplayMetrics mDisplayMetrics = mContext.getResources()
				.getDisplayMetrics();
		float maxSW = mDisplayMetrics.widthPixels / mDisplayMetrics.density;
		// 如果是360sw以上
		if (maxSW >= 360) {
			wl.width = DensityUtil.dip2px(mContext, 318);
			wl.height = DensityUtil.dip2px(mContext, 292);
			wl.y = -DensityUtil.dip2px(mContext, 45);
		} else {
			wl.width = DensityUtil.dip2px(mContext, 254);
			wl.height = DensityUtil.dip2px(mContext, 233);
			wl.y = -DensityUtil.dip2px(mContext, 25);
		}

		w.setAttributes(wl);
		return mDialog;
	}

	/** 获取页面布局 */
	private View GetView() {
		findviews();
		return layout;
	}

	/********** UI界面控件 **********/

	private void findviews() {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		layout = inflater.inflate(R.layout.dialog_outdate, null);
		buyButton = (Button) layout.findViewById(R.id.buy);
		commentButton = (Button) layout.findViewById(R.id.comment);
		cancelButton = (Button) layout.findViewById(R.id.cancel);
		progressDesc = (TextView) layout.findViewById(R.id.progressDesc);
		progress = (ProgressBar) layout.findViewById(R.id.progress);

		buyButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gotoBuyPage();
				GuardApplication.appInstance.exit();
			}
		});

		commentButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GuardApplication.appInstance.exit();
			}
		});

	}

	private void gotoBuyPage() {
		try {
			Intent intent = new Intent();
			intent.setData(Uri.parse(MSTORE_URI_HEADER + SOFTWARE_URL_BASE
					+ Const.MSTORE_IDENTIFY));
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClassName("com.meizu.mstore",
					"com.meizu.mstore.MStoreMainPlusActivity");
			mContext.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
