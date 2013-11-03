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
import com.zuiniuwang.android.guardthief.bean.NoticeDao;
import com.zuiniuwang.android.guardthief.config.NoticeBean;
import com.zuiniuwang.android.guardthief.ui.adapter.HeroViewAdapter;
import com.zuiniuwang.android.guardthief.ui.adapter.NoticeViewAdapter;
import com.zuiniuwang.android.guardthief.util.CustomConfiguration;

/**
 * 通知
 * 
 * @author guoruiliang
 * 
 */
public class Notice extends AbsActivity implements OnClickListener {
	ListView listView;
	NoticeDao noticeDao;
	@Override
	View getView() {
		return LayoutInflater.from(mContext).inflate(R.layout.notice, null);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listView = (ListView) findViewById(R.id.listview);
		NoticeViewAdapter adapter = new NoticeViewAdapter(mContext);
		noticeDao=new NoticeDao(mContext);
		List<NoticeBean> datas = getdatas();

		adapter.setDatas(datas);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

			}

		});
		
		
		
	}

	private List<NoticeBean> getdatas() {
		return noticeDao.getNoticesFromPref();
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

	
	
}
