package com.zuiniuwang.android.guardthief.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.util.AwakeningUtil;
import com.zuiniuwang.android.guardthief.util.CustomConfiguration;
import com.zuiniuwang.android.guardthief.util.NavigationUtil;
import com.zuiniuwang.android.guardthief.util.UiUtil;

public class Validate extends Activity implements OnClickListener,OnFocusChangeListener {
	View mView;
	private EditText passEdit;

	private Button nextButton;
	Handler mHandler = new Handler();

	String extra_validate = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		mView = LayoutInflater.from(Validate.this).inflate(R.layout.validate,
				null);
		setContentView(mView);
		extra_validate = getIntent().getStringExtra(
				AwakeningUtil.EXTRA_VALIDATE);
		findViews();


		/** 进入页面设置密码输入框选中，并弹出对话框 */
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				UiUtil.showInputMethod(Validate.this, passEdit);
			}
		}, 1000);
		
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
		
		passEdit.setOnFocusChangeListener(this);
		
	}

	private void findViews() {
		passEdit = (EditText) findViewById(R.id.pass);

		nextButton = (Button) findViewById(R.id.next);
		nextButton.setOnClickListener(this);

		passEdit.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				checkValidate();
				return true;
			}
		});

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.next:
			checkValidate();

		default:
			break;
		}

	}

	private void checkValidate() {
		String text = passEdit.getText().toString();
		// 必须输入密码
		if (TextUtils.isEmpty(text)) {
			UiUtil.showText(getString(R.string.validate_password_empty));
			return;
		}
		// 密码不正确
		if (!CustomConfiguration.isPasswordCorrect(text)) {
			UiUtil.showText(getString(R.string.validate_password_error));
			passEdit.setText("");
			return;
		}
		// 到主页面
		enterActivity();

	}

	/**
	 * 进入主设置页面
	 */
	public void enterActivity() {
		/** 如果是第一次进入验证的页面，不是从后台判断启动的 */
		if (TextUtils.isEmpty(extra_validate)) {
			NavigationUtil.gotoMainPage(Validate.this, false);
		}
		finish();
		NavigationUtil.gotoNextFade(this);

		/** 验证成功，取消后台状态 */
		AwakeningUtil.validateSuccess();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			GuardApplication.appInstance.exit();
			NavigationUtil.gotoNextFade(this);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 如果没有焦点了，则关闭软键盘
	 */
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (!hasFocus) {
			dismissIMM();
		}
	}
	
	/** 关闭输入框 */
	public boolean dismissIMM() {
		boolean result=false;
		SystemClock.sleep(100);
		InputMethodManager imme = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imme != null) {
			result=	imme.hideSoftInputFromWindow(mView.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
		return result;
	}
}
