package com.zuiniuwang.android.guardthief.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.GuardService;
import com.zuiniuwang.android.guardthief.PocketWidgetProvider;
import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.camera.CameraManager;
import com.zuiniuwang.android.guardthief.dialog.DialogPicBean;
import com.zuiniuwang.android.guardthief.dialog.PopDialog;
import com.zuiniuwang.android.guardthief.dialog.TestDialogBuilder;
import com.zuiniuwang.android.guardthief.util.AwakeningUtil;
import com.zuiniuwang.android.guardthief.util.CustomConfiguration;
import com.zuiniuwang.android.guardthief.util.DensityUtil;
import com.zuiniuwang.android.guardthief.util.NavigationUtil;
import com.zuiniuwang.android.guardthief.util.UiUtil;

public class MainActivity extends BaseActivity implements OnClickListener {

	public static final String ACTION_REFRESH = "refresh";
	public static final String ACTION_PROGRESS = "progress";
	private RefreshReceiver receiver;
	Handler handler = new Handler();
	ImageButton testImageButton, menuImageButton;
	ImageView logoImageButton;
	private Context mContext;
	ImageView hideSwitch, autoSwitch, pocketSwitch, messageStatus;

	TextView textMessage;
	TestDialogBuilder cameraDialogBuilder;
	Dialog cameraDialog;

	PopDialog popDialogAuto, popDialogHide, popDialogPocket;
	/** 是否开启测试 */
	private boolean isStartTest = false;

	/** 版本验证 */
	TextView licenseText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		View mainView = LayoutInflater.from(MainActivity.this).inflate(
				R.layout.home_frame, null);
		mContext = MainActivity.this;

		// set the Above View
		setContentView(mainView);
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuListFragment()).commit();

		getSlidingMenu().setSecondaryMenu(R.layout.menu_frame_right);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame_right, new MenuListFragmentRight())
				.commit();

		findviewById();

		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_REFRESH);// 界面刷新广播
		filter.addAction(ACTION_PROGRESS);// 进度广播
		filter.addAction(CameraManager.CAMERA_ACTION_START);// 相机开始广播
		filter.addAction(CameraManager.CAMERA_ACTION_RECYCLE);// 相机结束广播

		receiver = new RefreshReceiver();
		registerReceiver(receiver, filter);

		cameraDialogBuilder = new TestDialogBuilder(mContext, handler);
		cameraDialog = cameraDialogBuilder.BulidDialog();

		// 魅族手机隐藏menu
		UiUtil.hideMeizuMenu(mainView);
		GuardApplication.appInstance.addActivity(this);
		
		float scale=1;
		//宽度小于320，mx
		if(DensityUtil.getMaxWidthDp(mContext)<=320	){
			scale=0.75f;
		}
		
		popDialogAuto = new PopDialog(mContext, handler, new DialogPicBean(
				R.drawable.pop_auto_text1, DensityUtil.dip2px(mContext, 55*scale)),
				new DialogPicBean(R.drawable.pop_auto_pic, DensityUtil.dip2px(
						mContext, 295*scale)), new DialogPicBean(
						R.drawable.pop_auto_text2, DensityUtil.dip2px(mContext,
								395*scale)));
		popDialogHide = new PopDialog(mContext, handler, new DialogPicBean(
				R.drawable.pop_hide_text, DensityUtil.dip2px(mContext, 265*scale)),
				new DialogPicBean(R.drawable.pop_hide_pic, DensityUtil.dip2px(
						mContext, 450*scale)));
		popDialogPocket = new PopDialog(mContext, handler, new DialogPicBean(
				R.drawable.pop_pocket_text, DensityUtil.dip2px(mContext, 130*scale)),
				new DialogPicBean(R.drawable.pop_pocket_pic, DensityUtil
						.dip2px(mContext, 370*scale)));

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (receiver != null) {
			unregisterReceiver(receiver);
			receiver = null;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		AwakeningUtil.gotoStopState();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		AwakeningUtil.gotoStartState();
		MobclickAgent.onResume(this);

		if (CustomConfiguration.isSettedPhone()) {
			messageStatus.setBackgroundResource(R.drawable.switch_right);
		} else {
			messageStatus.setBackgroundResource(R.drawable.switch_error);
		}

		// 设置短信提示
		String phone = CustomConfiguration.getPhone();
		if (TextUtils.isEmpty(phone)) {
			textMessage.setText(R.string.home_text_message_desc_none);
		} else {
			textMessage.setText(getString(R.string.home_text_message_desc,
					CustomConfiguration.getPhone()));

		}
		initSwitch();
	}

	private void findviewById() {
		menuImageButton = (ImageButton) findViewById(R.id.menuImageButton);
		testImageButton = (ImageButton) findViewById(R.id.testImageButton);

		logoImageButton = (ImageView) findViewById(R.id.menuLogo);

		hideSwitch = (ImageView) findViewById(R.id.hideSwitch);
		autoSwitch = (ImageView) findViewById(R.id.autoSwitch);
		pocketSwitch = (ImageView) findViewById(R.id.pocketSwitch);
		messageStatus = (ImageView) findViewById(R.id.messageStatus);

		textMessage = (TextView) findViewById(R.id.textMessage);

		menuImageButton.setOnClickListener(this);
		logoImageButton.setOnClickListener(this);

		testImageButton.setOnClickListener(this);
		autoSwitch.setOnClickListener(this);
		hideSwitch.setOnClickListener(this);
		pocketSwitch.setOnClickListener(this);


	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.menuImageButton:
			toggle();
			break;
		case R.id.menuLogo:
			showSecondaryMenu();
			break;

		case R.id.testImageButton:
			test();
			break;

		case R.id.autoSwitch:
			boolean isStart = CustomConfiguration.isStartGuard();
			if (isStart) {
				CustomConfiguration.setIsStartGuard(false);
				initSwitch();
			} else {
				CustomConfiguration.setIsStartGuard(true);
				initSwitch();
				if (!popDialogAuto.getIsStart()) {
					if (popDialogAuto.getDialog() == null) {
						popDialogAuto.BulidDialog();
					}
					popDialogAuto.getDialog().show();
				}
			}

			break;
		case R.id.hideSwitch:
			boolean isHide = CustomConfiguration.getIsHideIcon();
			if (isHide) {
				CustomConfiguration.setIsHideIcon(false);
				initSwitch();
			} else {
				CustomConfiguration.setIsHideIcon(true);
				initSwitch();

				if (!popDialogHide.getIsStart()) {
					if (popDialogHide.getDialog() == null) {
						popDialogHide.BulidDialog();
					}
					popDialogHide.getDialog().show();
				}

			}

			break;
		case R.id.messageLayout:
			startActivity(new Intent(mContext, PhoneSetting.class));
			break;

		case R.id.pocketSwitch:
			boolean isPocketOn = CustomConfiguration.getIsPocketOn();

			if (isPocketOn) {
				initSwitch();
			} else {
				initSwitch();
				if (!popDialogPocket.getIsStart()) {
					if (popDialogPocket.getDialog() == null) {
						popDialogPocket.BulidDialog();
					}
					popDialogPocket.getDialog().show();
				}
			}

			break;

		default:
			break;
		}

	}

	private void test() {
		if (!GuardService.grpsUtil.isNetworkAvailable()) {
			Toast.makeText(mContext, R.string.network_disabled,
					Toast.LENGTH_LONG).show();
		} else {
			cameraDialog.show();
		}

	}

	/** 初始化监控控件 文字信息 */
	private void initSwitch() {
		if (CustomConfiguration.isStartGuard()) {
			autoSwitch.setBackgroundResource(R.drawable.switch_on);

		} else {
			autoSwitch.setBackgroundResource(R.drawable.switch_off);
		}

		if (CustomConfiguration.getIsHideIcon()) {
			hideSwitch.setBackgroundResource(R.drawable.switch_on);
		} else {
			hideSwitch.setBackgroundResource(R.drawable.switch_off);
		}
		if (CustomConfiguration.getIsPocketOn()) {
			pocketSwitch.setBackgroundResource(R.drawable.switch_on);
		} else {
			pocketSwitch.setBackgroundResource(R.drawable.switch_off);
		}
	}

	private long mExitTime;

	/** 按返回键盘连续2次退出 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, R.string.double_exist_prompt,
						Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();

			} else {
				finish();
				NavigationUtil.gotoNextFade(MainActivity.this);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	class RefreshReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(final Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equalsIgnoreCase(ACTION_REFRESH)) {
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						initSwitch();

					}
				}, 1000);
			} else if (action.equalsIgnoreCase(ACTION_PROGRESS)) {
				String name = intent.getStringExtra("name");
				int progress = intent.getIntExtra("progress", 0);
				if (progress <= 10) {
					isStartTest = true;
				} else if (progress == -1 || progress >= 100) {
					isStartTest = false;
				}

				if (cameraDialogBuilder != null) {
					cameraDialogBuilder.setProgress(name, progress);
					// 如果对话框关闭状态
					if (!cameraDialogBuilder.isShow()) {
						Toast.makeText(mContext, "拍摄进度：" + name,
								Toast.LENGTH_SHORT).show();
					}

				}
			}
			// 相机开启
			else if (action.equalsIgnoreCase(CameraManager.CAMERA_ACTION_START)) {
				if (isStartTest) {
					TestDialogBuilder.sendBroad(context,
							context.getString(R.string.dialog_cameraStart), 20);
				}

			}
			// 相机关闭
			else if (action
					.equalsIgnoreCase(CameraManager.CAMERA_ACTION_RECYCLE)) {
				if (!isStartTest) {
					return;
				}

				int event = intent.getIntExtra(CameraManager.CAMERA_EXTRA, 0);
				switch (event) {
				case CameraManager.CAMERA_EVENT_ERROR:
					TestDialogBuilder
							.sendBroad(
									context,
									context.getString(R.string.dialog_camerarecycle_error),
									40);
					handler.postDelayed(new Runnable() {

						@Override
						public void run() {
							TestDialogBuilder.sendBroad(context, context
									.getString(R.string.dialog_camera_failed),
									101);

						}
					}, 2000);

					break;
				case CameraManager.CAMERA_EVENT_NORMAL:

					TestDialogBuilder.sendBroad(context,
							context.getString(R.string.dialog_camerarecycle),
							40);

					break;
				case CameraManager.CAMERA_EVENT_TIMEOUT:

					TestDialogBuilder
							.sendBroad(
									context,
									context.getString(R.string.dialog_camerarecycle_error),
									40);
					handler.postDelayed(new Runnable() {

						@Override
						public void run() {
							TestDialogBuilder.sendBroad(context, context
									.getString(R.string.dialog_camera_failed),
									101);

						}
					}, 2000);
					break;
				default:
					break;
				}

			}

		}

	}


}
