package com.zuiniuwang.android.guardthief.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.util.NavigationUtil;
import com.zuiniuwang.android.guardthief.util.UiUtil;

/**
 * 首页
 * <P>
 * 显示个动画就跳过
 * 
 * @author guoruiliang
 * 
 */
public class LogoActivity extends Activity {
	private Context mContext;
	private ImageView image;
	private View mView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.mContext = LogoActivity.this;
		mView=LayoutInflater.from(mContext).inflate(R.layout.guard_one_logo, null);
		
		setContentView(mView);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // 全屏显示
		// 魅族手机隐藏menu
		UiUtil.hideMeizuMenu(getWindow().getDecorView());
//
//		AnimationSet animationset = new AnimationSet(true);
//		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
//		alphaAnimation.setDuration(1000);
//		animationset.addAnimation(alphaAnimation);
//		mView.startAnimation(animationset);

		new CountDownTimer(3000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
			}

			@Override
			public void onFinish() {
				NavigationUtil.gotoMainPage(LogoActivity.this, true);
			    finish();
				NavigationUtil.gotoNextFade(LogoActivity.this);
			
			}
		}.start();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 在欢迎界面屏蔽BACK键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return false;
	}

}
