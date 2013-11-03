package com.zuiniuwang.android.guardthief.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zuiniuwang.android.guardthief.GuardService;
import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.bean.FeedBackBean;
import com.zuiniuwang.android.guardthief.util.CustomConfiguration;
import com.zuiniuwang.android.guardthief.util.UiUtil;

/**
 * 服务状态界面
 * 
 * @author guoruiliang
 * 
 */
public class FeedBack extends AbsActivity implements OnClickListener,
		OnFocusChangeListener {
	/** handler */
	Handler handler = new Handler();
	GuardService guardService;
	EditText edit, contact;
	TextView contactDesc;

	@Override
	View getView() {
		return LayoutInflater.from(mContext).inflate(R.layout.feedback, null);
	}

	/**
	 * 
	 * 构造函数
	 * 
	 * @param savedInstanceState
	 *            状态保存
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		guardService = GuardService.guardService;
		edit = (EditText) findViewById(R.id.edit);
		contact = (EditText) findViewById(R.id.contact);
		contactDesc = (TextView) findViewById(R.id.contactDesc);

		SpannableStringBuilder style = new SpannableStringBuilder(
				getString(R.string.feedback_contact));
		style.setSpan(new ForegroundColorSpan(Color.RED), 0, 1,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		contactDesc.setText(style);

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

		edit.setOnFocusChangeListener(this);
		contact.setOnFocusChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		if (guardService == null) {
			return;
		}
		switch (v.getId()) {
		case R.id.submit:
			String content = String.valueOf(edit.getText());
			String contactText = String.valueOf(contact.getText());
			if (!TextUtils.isEmpty(content) && !TextUtils.isEmpty(contactText)) {
				FeedBackBean feedBack = new FeedBackBean();
				feedBack.time = System.currentTimeMillis();
				feedBack.content = content;
				feedBack.userEmail = CustomConfiguration.getEmail();
				feedBack.contact = contactText;
				FeedBackBean.addFeedBack(feedBack);
				UiUtil.showText(getString(R.string.feedback_submit_success));
				gotoMain();
			} else {

				if (TextUtils.isEmpty(content)) {
					edit.requestFocus();
					UiUtil.showText(getString(R.string.validate_feedback_empty));
				} else {
					contact.requestFocus();
					UiUtil.showText(getString(R.string.validate_contact_empty));
				}

			}

			break;

		case R.id.back:

			if (!dismissIMM()) {
				// 到主页面
				gotoMain();
			}

			break;

		default:
			break;

		}

	}

}
