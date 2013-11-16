package com.zuiniuwang.android.guardthief.ui;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.util.CustomConfiguration;
import com.zuiniuwang.android.guardthief.util.UiUtil;

public class PasswordSetting extends AbsSettingActivity implements
		OnClickListener {
	private EditText passEdit, repeatEdit;

	/**是否是设置密码页面*/
	boolean isChangePassPage=false;
	
	private Button nextButton;
	Handler mHandler = new Handler();

	ImageView titleImage;
	RelativeLayout titleLayout;

	@Override
	View getView() {
		return LayoutInflater.from(PasswordSetting.this).inflate(
				R.layout.setting_pass, null);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!TextUtils.isEmpty(getIntent().getStringExtra("name"))) {
			isChangePassPage=true;
		}else{
			isChangePassPage=false;
		}
		
		
		findViews();

		/** 进入页面设置密码输入框选中，并弹出对话框 */
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				UiUtil.showInputMethod(PasswordSetting.this, passEdit);
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
		repeatEdit.setOnFocusChangeListener(this);
		
	}

	private void findViews() {
		passEdit = (EditText) findViewById(R.id.pass);
		repeatEdit = (EditText) findViewById(R.id.repeat);
		nextButton = (Button) findViewById(R.id.next);
		titleImage = (ImageView) findViewById(R.id.titleImage);
		titleLayout = (RelativeLayout) findViewById(R.id.titleLayout);

		nextButton.setOnClickListener(this);

		passEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if(!isChangePassPage){
					if (s != null && s.length() > 5) {
						nextButton.setEnabled(true);
					} else {
						nextButton.setEnabled(false);
					}
				}
				
			}
		});

		repeatEdit.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				checkPass();
				return true;
			}
		});

		// 判断是是修改密码页面还是初始设置页面
		if(isChangePassPage){
			titleImage.setVisibility(View.GONE);
			titleLayout.setVisibility(View.VISIBLE);
			nextButton.setEnabled(true);
			nextButton.setBackgroundResource(R.drawable.selector_confirm);
		}else{
			titleImage.setVisibility(View.VISIBLE);
			titleLayout.setVisibility(View.GONE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN); // 全屏显示
		}
		
	

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.next:
			checkPass();
			break;
		case R.id.back:
			if (!dismissIMM()) {
				activityFinish();
			}

			break;

		default:
			break;
		}

	}

	private boolean checkPass() {
		String text = passEdit.getText().toString();
		String textConfirm = repeatEdit.getText().toString();
		if (text.length() < 6) {
			UiUtil.showText(getString(R.string.validate_password_needsix));
			return false;
		} else if (!text.equalsIgnoreCase(textConfirm)) {
			UiUtil.showText(getString(R.string.validate_password_notequals));
			return false;
		}

		boolean isAllSet = CustomConfiguration.isAllSetted();
		CustomConfiguration.savePassword(text);
		backToMainActivity(isAllSet);
		return true;

	}

}
