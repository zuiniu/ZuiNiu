package com.zuiniuwang.android.guardthief.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.ui.Logo2Activity;

/** 界面工具类 */
public class UiUtil {
	static int SDK_VERSION = VERSION.SDK_INT;

	/** 魅族隐藏menu,当android4.0以上时采用 */
	public static void hideMeizuMenu(View view) {
		if (SDK_VERSION >= 14) {
			try {
//				view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	/** 弹出提示信息 */
	public static void showText(final String text) {
		GuardApplication.appHandler.post(new Runnable() {
			public void run() {
				Toast.makeText(GuardApplication.appContext, text,
						Toast.LENGTH_SHORT).show();
			}
		});

	}

	/**
	 * 显示输入法(软键盘)
	 * 
	 * @param focusEditText
	 *            需要获得焦点的编辑框
	 */
	public static void showInputMethod(Context context, View focusEditText) {
		if (focusEditText == null) {
			return;
		}

		focusEditText.requestFocus();
		InputMethodManager inputMethodManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputMethodManager != null) {
			inputMethodManager.showSoftInput(focusEditText, 0);
		}
	}

	/**
	 * 隐藏输入法(软键盘)
	 * 
	 * @param focusEditText
	 *            焦点所在的编辑框
	 */
	public static void hideInputMethod(Context context, View focusEditText) {
		if (focusEditText != null) {
			InputMethodManager inputMethodManager = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(
					focusEditText.getWindowToken(), 0);
		}
	}


	public static void startActivtyBack(Context context) {
		context.startActivity(new Intent(context, Logo2Activity.class));
	}
}
