package com.zuiniuwang.android.guardthief.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.util.NavigationUtil;

/**
 * 首页
 * <P>
 * 显示个动画就跳过
 * 
 * @author guoruiliang
 * 
 */
public class Logo2Activity extends Activity {
	private Context context;
	private ImageView image;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = Logo2Activity.this;
		setContentView(R.layout.guard_one_logo);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // 全屏显示

//		image = (ImageView) findViewById(R.id.image);
//
//		AnimationSet animationset = new AnimationSet(true);
//		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
//		alphaAnimation.setDuration(1000);
//		animationset.addAnimation(alphaAnimation);
//		image.startAnimation(animationset);

		new CountDownTimer(3000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
			}

			@Override
			public void onFinish() {
				NavigationUtil.gotoMainPage(Logo2Activity.this, true);
			    finish();
				NavigationUtil.gotoNextFade(Logo2Activity.this);
			
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
