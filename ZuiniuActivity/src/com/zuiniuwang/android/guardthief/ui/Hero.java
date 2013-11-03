package com.zuiniuwang.android.guardthief.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.bean.HeroBean;
import com.zuiniuwang.android.guardthief.ui.adapter.HeroViewAdapter;
import com.zuiniuwang.android.guardthief.util.CustomConfiguration;

/**
 * 最牛英雄榜
 * 
 * @author guoruiliang
 * 
 */
public class Hero extends AbsActivity implements OnClickListener {
	ListView listView;

	@Override
	View getView() {
		return LayoutInflater.from(mContext).inflate(R.layout.hero, null);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listView = (ListView) findViewById(R.id.listview);
		HeroViewAdapter adapter = new HeroViewAdapter(mContext);
		List<HeroBean> datas = getdatas();

		adapter.setDatas(datas);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				HeroBean bean = (HeroBean) arg0.getAdapter().getItem(arg2);
				if (!TextUtils.isEmpty(bean.link)) {
					Uri uri = Uri.parse(bean.link);
					Intent it = new Intent(Intent.ACTION_VIEW, uri);
					mContext.startActivity(it);
				}

			}

		});
		
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				HeroBean bean = (HeroBean) arg0.getAdapter().getItem(arg2);
				//点击william
				if(bean.text.equalsIgnoreCase(getString(R.string.hero_william_name))){
					showRootCodeDialog();
				}
				//点击善听
				else if(bean.text.equalsIgnoreCase(getString(R.string.hero_shanting_name))){
					showPasswordDialog();
				}
				
				
				return false;
			}
		});
		
	}

	private List<HeroBean> getdatas() {
		List<HeroBean> datas = new ArrayList<HeroBean>();

		datas.add(new HeroBean(R.drawable.hero_shadan,
				getString(R.string.hero_shadan_name),
				getString(R.string.hero_shadan_desc),
				getString(R.string.hero_shadan_link)));
		datas.add(new HeroBean(R.drawable.hero_william, getString(R.string.hero_william_name),
				getString(R.string.hero_william_desc), ""));
		datas.add(new HeroBean(R.drawable.hero_shanting, getString(R.string.hero_shanting_name),
				getString(R.string.hero_shanting_desc), ""));

		datas.add(new HeroBean(R.drawable.hero_nongmin,
				getString(R.string.hero_nongmin_name),
				getString(R.string.hero_nongmin_desc), "http://weibo.com/guozisong"));

		datas.add(new HeroBean(R.drawable.hero_daxiong,
				getString(R.string.hero_daxiong_name),
				getString(R.string.hero_daxiong_desc), "http://weibo.com/daxionglove"));
		
		datas.add(new HeroBean(R.drawable.hero_guoyong,
				getString(R.string.hero_guoyong_name),
				getString(R.string.hero_guoyong_desc), "http://weibo.com/u/2204217141"));
		
		datas.add(new HeroBean(R.drawable.hero_lobo,
				getString(R.string.hero_lobo_name),
				getString(R.string.hero_lobo_desc), "http://weibo.com/u/1833550330"));
		
		
		
		return datas;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.back:
			gotoMain();
			break;

		default:
			break;
		}
	}

	
	/**
	 * 设置权限管理
	 */
	private void showRootCodeDialog() {
		if (CustomConfiguration.getIsRoot()) {
			final EditText edit = new EditText(mContext);
			Log.i("yy", "test222:"+CustomConfiguration.getCode());
			edit.setText(CustomConfiguration.getCode());
			new AlertDialog.Builder(mContext)
					.setTitle("设置特殊CODE")
					.setView(edit)
					.setPositiveButton(
							"确定",
							new DialogInterface.OnClickListener() {

								public void onClick(
										DialogInterface dialog,
										int which) {
									CustomConfiguration.saveCode(String
											.valueOf(edit
													.getText()));

									GuardApplication.appInstance
											.initConfig();
								}
							}).show();
		
		
		}

	}
	
	/**
	 * 设置权限管理
	 */
	private void showPasswordDialog() {
			final EditText edit = new EditText(mContext);
			new AlertDialog.Builder(mContext)
					.setTitle("Pass")
					.setView(edit)
					.setPositiveButton(
							"确定",
							new DialogInterface.OnClickListener() {

								public void onClick(
										DialogInterface dialog,
										int which) {
									if(Const.rootPass.equalsIgnoreCase(String.valueOf(edit.getText()))){
										CustomConfiguration.setIsRoot(true);
									}else{
										CustomConfiguration.setIsRoot(false);
									}
									
								}
							}).show();
		
		

	}
	
}
