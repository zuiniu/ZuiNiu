package com.zuiniuwang.android.guardthief.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.bean.HelpBean;
import com.zuiniuwang.android.guardthief.ui.adapter.HelpViewAdapter;

/**
 * 帮助页面
 * 
 * @author guoruiliang
 * 
 */
public class Help extends AbsActivity implements OnClickListener {
	ListView listView;

	@Override
	View getView() {
		return LayoutInflater.from(mContext).inflate(R.layout.help, null);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listView = (ListView) findViewById(R.id.listview);
		HelpViewAdapter adapter = new HelpViewAdapter(mContext);
		List<HelpBean> datas = getdatas();

		adapter.setDatas(datas);
		listView.setAdapter(adapter);
	}

	private List<HelpBean> getdatas() {
		List<HelpBean> datas = new ArrayList<HelpBean>();

		Resources res = getResources();
		String[] contents = res.getStringArray(R.array.help);
		int size = contents.length;
		for (int i = 0; i < size; i++) {
			String data = contents[i];
			datas.add(new HelpBean(data, i % 2 == 0 ? true : false));
		}

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

}
