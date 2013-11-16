package com.zuiniuwang.android.guardthief.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.util.AwakeningUtil;
import com.zuiniuwang.android.guardthief.util.UiUtil;

public abstract class AbsActivity extends SherlockActivity implements
		OnFocusChangeListener {
	protected static final String LOG_TAG = Const.LOG_TAG;

	protected Context mContext;
	protected View mView;

	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.activity_scroll_from_right,R.anim.activity_fadeout);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
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
//
//	/** 点回退键，需要直接退出 */
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			gotoMain();
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}

	
	

	/**
	 * 进入主设置页面
	 */
	public void gotoMain() {
		finish();
		overridePendingTransition(R.anim.activity_fadein,R.anim.activity_scroll_to_right);
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


	
	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		if (paramMenuItem.getItemId() == android.R.id.home) {
			gotoMain();
			return true;
		}
		return super.onOptionsItemSelected(paramMenuItem);
	}
}
