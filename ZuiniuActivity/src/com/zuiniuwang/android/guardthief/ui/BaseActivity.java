package com.zuiniuwang.android.guardthief.ui;

import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.util.DensityUtil;
import com.zuiniuwang.android.guardthief.util.UiUtil;

public class BaseActivity extends SlidingFragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setSecondaryShadowDrawable(R.drawable.shadow);
		sm.setBehindOffset(DensityUtil.getScreenWidth(BaseActivity.this)/2);
		sm.setFadeEnabled(false);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		sm.setShadowWidth(20);
	}

}
