package com.zuiniuwang.android.guardthief.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.bll.CameraStrategy;
import com.zuiniuwang.android.guardthief.bll.TimeStrategy;
import com.zuiniuwang.android.guardthief.ui.MainActivity;
import com.zuiniuwang.android.guardthief.util.DensityUtil;

public class TestDialogBuilder {

	/*********** 页面变量 *********/
	private Context context;// 上下文

	private ImageButton frontButton, rearButton, cancelButton;

	TextView progressDesc;

	ProgressBar progress;

	LinearLayout buttonLayout;

	RelativeLayout progressLayout;
	private Handler handler;

	private Runnable failRunnable;

	private View layout;
	private Dialog mDialog;

	/**
	 * 构造函数，采用默认值
	 * 
	 * @param context
	 */
	public TestDialogBuilder(final Context context, Handler handler) {
		this.context = context;
		layout = GetView();// 初始化页面布局
		this.handler = handler;
		failRunnable = new Runnable() {

			@Override
			public void run() {
				sendBroad(context, context.getString(R.string.dialog_timeout_failed),
						101);

			}
		};
	}

	/** 创建AlertDialog */
	public Dialog BulidDialog() {
		this.mDialog = new Dialog(context, R.style.dialog);
		mDialog.setContentView(layout);
		mDialog.setCanceledOnTouchOutside(true);

		mDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				//当开始拍摄的时候，如果关闭对话框，会弹出toast
				int value = progress.getProgress();
				if (value > 0 && value < 100) {
					Toast.makeText(context, "拍摄进度：" + progressDesc.getText(),
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		Window w = mDialog.getWindow();
		WindowManager.LayoutParams wl = w.getAttributes();

		DisplayMetrics mDisplayMetrics = context.getResources()
				.getDisplayMetrics();
		float maxSW = mDisplayMetrics.widthPixels / mDisplayMetrics.density;
		// 如果是360sw以上
		if (maxSW >= 360) {
			wl.width = DensityUtil.dip2px(context, 318);
			wl.height = DensityUtil.dip2px(context, 292);
			wl.y = -DensityUtil.dip2px(context, 45);
		} else {
			wl.width = DensityUtil.dip2px(context, 254);
			wl.height = DensityUtil.dip2px(context, 233);
			wl.y = -DensityUtil.dip2px(context, 25);
		}

		w.setAttributes(wl);
		return mDialog;
	}

	/** 获取页面布局 */
	private View GetView() {
		findviews();
		return layout;
	}

	/**
	 * 设置进度
	 * 
	 * @param text
	 *            描述文字
	 * @param value
	 *            进度
	 */
	public void setProgress(String text, int value) {
		buttonLayout.setVisibility(View.GONE);
		progressLayout.setVisibility(View.VISIBLE);
		progressDesc.setText(text);
		progress.setProgress(value);
		if (value == -1 || value >= 100) {
			handler.removeCallbacks(failRunnable);

			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					mDialog.dismiss();
					buttonLayout.setVisibility(View.VISIBLE);
					progressLayout.setVisibility(View.GONE);
				}
			}, 5000);

		}

	}

	/********** UI界面控件 **********/

	private void findviews() {
		LayoutInflater inflater = LayoutInflater.from(context);
		layout = inflater.inflate(R.layout.dialog_test, null);
		frontButton = (ImageButton) layout.findViewById(R.id.front);
		rearButton = (ImageButton) layout.findViewById(R.id.rear);
		cancelButton = (ImageButton) layout.findViewById(R.id.cancel);
		progressDesc = (TextView) layout.findViewById(R.id.progressDesc);
		progress = (ProgressBar) layout.findViewById(R.id.progress);
		progressLayout = (RelativeLayout) layout
				.findViewById(R.id.progressLayout);
		buttonLayout = (LinearLayout) layout.findViewById(R.id.buttonLayout);

		frontButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 发送信息到指定位置
				startTestMail();

			}
		});

		rearButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 发送信息到指定位置
				startTestBackMail();

			}
		});

		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDialog.dismiss();

			}
		});

	}

	/***/
	private void startTestMail() {
		TimeStrategy.TESTFORSETTING.sendTimeToGuardCommand(context,
				CameraStrategy.TestFront);

		sendBroad(context, context.getString(R.string.dialog_command_sended),
				10);

		// 失败命令,50秒还没有搞定就判定错误
		handler.postDelayed(failRunnable, 90000);

	}

	/***/
	private void startTestBackMail() {
		TimeStrategy.TESTFORSETTING.sendTimeToGuardCommand(context,
				CameraStrategy.BACKTest);
		sendBroad(context, context.getString(R.string.dialog_command_sended),
				10);
		// 失败命令,30秒还没有搞定就判定错误
		handler.postDelayed(failRunnable, 90000);

	}

	public static void sendBroad(Context context, String name, int progress) {
		Intent intent = new Intent(MainActivity.ACTION_PROGRESS);
		intent.putExtra("name", name);
		intent.putExtra("progress", progress);
		context.sendBroadcast(intent);
	}

	/***
	 * 获取对话框是否显示
	 * 
	 * @return
	 */
	public boolean isShow() {
		return mDialog.isShowing();
	}
}
