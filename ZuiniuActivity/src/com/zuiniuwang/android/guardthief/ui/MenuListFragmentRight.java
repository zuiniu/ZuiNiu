package com.zuiniuwang.android.guardthief.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.bean.MenuBean;
import com.zuiniuwang.android.guardthief.ui.adapter.MenuListAdapter;

public class MenuListFragmentRight extends Fragment {

	private static final int MENUID_SETTING = 1;
	private static final int MENUID_EXIT = 7;
	private View mView;

	ListView mListView;
	MenuListAdapter mMenuListAdapter;
	TextView version;
	int selected = 1;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mView = inflater.inflate(R.layout.menulist_right, null);

		version = (TextView) mView.findViewById(R.id.version);
		version.setText(Const.VERSION);
		mListView = (ListView) mView.findViewById(R.id.listview);

		mMenuListAdapter = new MenuListAdapter(getActivity());
		mMenuListAdapter.setDatas(getDatas());
		mListView.setAdapter(mMenuListAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				MenuBean bean = (MenuBean) arg0.getAdapter().getItem(arg2);
				int id = bean.id;
				switch (id) {
				case MENUID_SETTING:

					Intent intent = new Intent(getActivity(), Setting.class);
					startActivity(intent);

					break;
				case MENUID_EXIT:
					GuardApplication.appInstance.exit();
					break;

				}

			}

		});

		return mView;
	}

	private List<MenuBean> getDatas() {
		List<MenuBean> beans = new ArrayList<MenuBean>();
		beans.add(new MenuBean(MENUID_SETTING, R.drawable.set,
				getString(R.string.menu_setting)));

		beans.add(new MenuBean(MENUID_EXIT, R.drawable.quit,
				getString(R.string.menu_exit)));


		return beans;
	}

}
