package com.zuiniuwang.android.guardthief.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.util.AwakeningUtil;
import com.zuiniuwang.android.guardthief.util.NavigationUtil;
import com.zuiniuwang.android.guardthief.util.UiUtil;

public abstract class AbsActivity extends Activity implements
		OnFocusChangeListener {
	protected static final String LOG_TAG = Const.LOG_TAG;

	protected Context mContext;
	protected View mView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mContext = AbsActivity.this;
		mView = getView();
		setContentView(mView);

		// 魅族手机隐藏menu
		UiUtil.hideMeizuMenu(mView);

		GuardApplication.appInstance.addActivity(this);
	}

	abstract View getView();

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		AwakeningUtil.gotoStartState();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		AwakeningUtil.gotoStopState();
	}

	/** 点回退键，需要直接退出 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			gotoMain();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 进入主设置页面
	 */
	public void gotoMain() {
		finish();
		NavigationUtil.gotoNextFromRight(this);
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
