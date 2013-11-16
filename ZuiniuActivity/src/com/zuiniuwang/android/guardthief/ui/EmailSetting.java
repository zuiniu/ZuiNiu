package com.zuiniuwang.android.guardthief.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.util.CustomConfiguration;
import com.zuiniuwang.android.guardthief.util.UiUtil;

public class EmailSetting extends AbsSettingActivity implements OnClickListener {

	private EditText emailEdit;

	private Button nextButton;
	Handler mHandler = new Handler();

	ImageView titleImage;
	RelativeLayout titleLayout;
	/** 邮箱后缀 */
	private String emailSuffix = "@163.com";

	private TextView emailSuffixTextView;
	private CheckBox type_163, type_126, type_yeah, type_qq;

	@Override
	View getView() {
		return LayoutInflater.from(EmailSetting.this).inflate(
				R.layout.setting_email, null);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		findViews();
		initUi();

		/** 进入页面设置密码输入框选中，并弹出对话框 */
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				UiUtil.showInputMethod(EmailSetting.this, emailEdit);
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

		emailEdit.setOnFocusChangeListener(this);

	}

	private void findViews() {
		emailSuffixTextView = (TextView) findViewById(R.id.emailSuffix);
		emailEdit = (EditText) findViewById(R.id.email);

		nextButton = (Button) findViewById(R.id.next);
		nextButton.setOnClickListener(this);
		titleImage = (ImageView) findViewById(R.id.titleImage);
		titleLayout = (RelativeLayout) findViewById(R.id.titleLayout);

		type_163 = (CheckBox) findViewById(R.id.type_163);
		type_126 = (CheckBox) findViewById(R.id.type_126);
		type_yeah = (CheckBox) findViewById(R.id.type_yeah);
		type_qq = (CheckBox) findViewById(R.id.type_qq);
		type_163.setOnClickListener(this);
		type_126.setOnClickListener(this);
		type_yeah.setOnClickListener(this);
		type_qq.setOnClickListener(this);

		// 判断是是修改密码页面还是初始设置页面
		if (TextUtils.isEmpty(getIntent().getStringExtra("name"))) {
			titleImage.setVisibility(View.VISIBLE);
			titleLayout.setVisibility(View.GONE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN); // 全屏显示
		} else {
			titleImage.setVisibility(View.GONE);
			titleLayout.setVisibility(View.VISIBLE);
			nextButton.setBackgroundResource(R.drawable.selector_confirm);
		}
	}

	/** 初始化控件 */
	private void initUi() {
		// 初始化邮件类型
		initEmailType();

		// 邮箱地址不为空
		String mail = CustomConfiguration.getEmail();
		if (!TextUtils.isEmpty(mail)) {
			emailEdit.setText(mail.replace(emailSuffix, ""));
		}

		emailEdit.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				checkEmail();
				return true;
			}
		});

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.next:
			checkEmail();
			break;
		case R.id.back:
			if (!dismissIMM()) {
				activityFinish();
			}
			break;
		case R.id.type_163:
			setSelect(type_163);
			break;
		case R.id.type_126:
			setSelect(type_126);
			break;
		case R.id.type_yeah:
			setSelect(type_yeah);
			break;
		case R.id.type_qq:
			setSelect(type_qq);
			break;
		default:
			break;
		}

	}

	private boolean checkEmail() {
		// 验证邮箱输入
		String mailText = emailEdit.getText().toString();
		String mailTextWithSuffix = mailText + emailSuffix;
		if (!CustomConfiguration.isEmailCorrect(mailTextWithSuffix)) {
			UiUtil.showText(getString(R.string.validate_email_error));
			return false;
		}

		boolean isAllSet = CustomConfiguration.isAllSetted();
		CustomConfiguration.saveEmail(mailTextWithSuffix);
		backToMainActivity(isAllSet);

		return true;
	}

	private void initEmailType() {
		String mail = CustomConfiguration.getEmail();
		if (mail.contains("163")) {
			setSelect(type_163);
		} else if (mail.contains("126")) {
			setSelect(type_126);
		} else if (mail.contains("yeah")) {
			setSelect(type_yeah);
		} else if (mail.contains("qq")) {
			setSelect(type_qq);
			
		}

	}

	private void setSelect(CheckBox checkBox) {
		type_163.setChecked(false);
		type_126.setChecked(false);
		type_yeah.setChecked(false);
		type_qq.setChecked(false);
		checkBox.setChecked(true);
		emailSuffix = checkBox.getText().toString().trim();
		emailSuffixTextView.setText(emailSuffix);
		
		if(checkBox.getId()==R.id.type_qq){
//			Toast.makeText(mContext, getString(R.string.qqmail_toast), Toast.LENGTH_LONG).show();
			
			new AlertDialog.Builder(mContext).setMessage(R.string.qqmail_toast).setCancelable(false).setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			}).show();
			
		}

	}

}
