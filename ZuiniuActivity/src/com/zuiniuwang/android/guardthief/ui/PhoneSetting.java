package com.zuiniuwang.android.guardthief.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.dialog.DialogPicBean;
import com.zuiniuwang.android.guardthief.dialog.PopDialog;
import com.zuiniuwang.android.guardthief.dialog.PopDialogForSms;
import com.zuiniuwang.android.guardthief.util.CustomConfiguration;
import com.zuiniuwang.android.guardthief.util.DensityUtil;
import com.zuiniuwang.android.guardthief.util.UiUtil;

public class PhoneSetting extends AbsActivity implements OnClickListener {

	private EditText phoneEdit;

	private Button nextButton;
	Handler mHandler = new Handler();

	private Button settingButton;
	Handler handler = new Handler();

	@Override
	View getView() {
		return LayoutInflater.from(PhoneSetting.this).inflate(
				R.layout.setting_phone, null);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		findViews();
		initUi();
		/** 设置主框架点击后获取焦点，这样可以调用关闭软键盘相关的事件 */
		mView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				mView.setFocusable(true);
				mView.setFocusableInTouchMode(true);
				mView.requestFocus();

				return false;
			}
		});

		float scale=1;
		//宽度小于320，mx
		if(DensityUtil.getMaxWidthDp(mContext)<=320	){
			scale=0.75f;
		}
		// 弹出pop
		PopDialogForSms popDialog = new PopDialogForSms(mContext, handler,
				new DialogPicBean(R.drawable.set_pic, DensityUtil.dip2px(
						mContext, 20*scale)), new DialogPicBean(R.drawable.set_text,
						DensityUtil.dip2px(mContext, 390*scale)), new DialogPicBean(
						R.drawable.set_btn, DensityUtil.dip2px(mContext, 508*scale)));

		if (!popDialog.getIsStart()) {
			if (popDialog.getDialog() == null) {
				popDialog.BulidDialog();
			}
			popDialog.setIsStart(true);
			popDialog.getDialog().show();
		}

	}

	private void findViews() {
		phoneEdit = (EditText) findViewById(R.id.phone);
		phoneEdit.setOnFocusChangeListener(this);

		nextButton = (Button) findViewById(R.id.next);
		nextButton.setOnClickListener(this);

		settingButton = (Button) findViewById(R.id.setting);
		settingButton.setOnClickListener(this);

	}

	/** 初始化控件 */
	private void initUi() {
		// 邮箱地址不为空
		phoneEdit.setText(CustomConfiguration.getPhone());

		phoneEdit.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				checkPhone();
				return true;
			}
		});

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.next:
			checkPhone();
			break;
		case R.id.back:
			if (!dismissIMM()) {
				gotoMain();
			}

			break;
		case R.id.setting:
			gotoSmsSettting();
			break;
		default:
			break;
		}

	}

	private void gotoSmsSettting() {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName("com.android.settings",
				"com.android.settings.applications.AppsCheckReadPermission"));

		try {
			startActivity(intent);

		} catch (Exception e) {
			this.startActivity(new Intent(new Intent(
					android.provider.Settings.ACTION_SECURITY_SETTINGS)));
		}

	}

	private boolean checkPhone() {
		String textPhone = phoneEdit.getText().toString();
		if (TextUtils.isEmpty(textPhone)) {
			UiUtil.showText(getString(R.string.validate_content_empty));
			return false;
		} else if (!CustomConfiguration.isPhoneCorrect(textPhone)) {
			UiUtil.showText(getString(R.string.validate_phone_error));
			return false;
		}

		CustomConfiguration.savePhone(textPhone);
		gotoMain();
		return true;
	}

}
