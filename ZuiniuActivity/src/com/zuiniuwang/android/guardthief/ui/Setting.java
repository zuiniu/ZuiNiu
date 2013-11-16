package com.zuiniuwang.android.guardthief.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;
import com.umeng.common.net.r;
import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.util.AwakeningUtil;
import com.zuiniuwang.android.guardthief.util.CustomConfiguration;
import com.zuiniuwang.android.guardthief.util.UiUtil;

/**
 * 帮助页面
 * 
 * @author guoruiliang
 * 
 */
public class Setting extends SherlockPreferenceActivity implements
		View.OnClickListener, Preference.OnPreferenceChangeListener {
	Preference passwordPreference, emailPreference, frenquncy, pocket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.activity_scroll_from_right,
				R.anim.activity_fadeout);
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(R.string.menu_setting);

		addPreferencesFromResource(R.xml.setting);
		passwordPreference = (Preference) findPreference("pref_password");
		passwordPreference
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {
						Intent intent = new Intent(Setting.this,
								PasswordSetting.class);
						intent.putExtra("name", "change");
						startActivity(intent);
						return true;
					}
				});

		emailPreference = (Preference) findPreference("pref_email");
		emailPreference
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {
						Intent intent = new Intent(Setting.this,
								EmailSetting.class);
						intent.putExtra("name", "edit");
						startActivity(intent);
						return true;
					}
				});

		frenquncy = (Preference) findPreference("pref_frenquncy");

		pocket = (Preference) findPreference("pref_pocket");
		pocket.setOnPreferenceChangeListener(this);
		frenquncy.setOnPreferenceChangeListener(this);
		initPreference();

		GuardApplication.appInstance.addActivity(this);

	}

	private void initPreference() {
		frenquncy.setSummary(String.valueOf(CustomConfiguration
				.getEmailFreequncy() / 3600000) + "小时");
		pocket.setSummary(String.valueOf(CustomConfiguration.getPocketTime() / 1000)
				+ "秒");
		emailPreference.setSummary(CustomConfiguration.getEmail());
	}

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

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}

	}

	
	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		if (paramMenuItem.getItemId() == android.R.id.home) {
			finish();
			overridePendingTransition(R.anim.activity_fadein,R.anim.activity_scroll_to_right);
			return true;
		}
		return super.onOptionsItemSelected(paramMenuItem);
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		new Thread() {
			public void run() {
				SystemClock.sleep(1000);
				runOnUiThread(new Runnable() {
					public void run() {
						initPreference();
					}
				});
			}
		}.start();

		return true;
	}

}
