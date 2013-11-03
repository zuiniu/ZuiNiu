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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.bean.MenuBean;
import com.zuiniuwang.android.guardthief.ui.adapter.MenuListAdapter;
import com.zuiniuwang.android.guardthief.util.NavigationUtil;

public class MenuListFragment extends Fragment {

	private static final int MENUID_CHANGEPASS = 1;
	private static final int MENUID_FEEDBACK = 2;
	private static final int MENUID_UPDATE = 3;
	private static final int MENUID_HELP = 4;
	private static final int MENUID_EMAIL = 7;
	
	private static final int MENUID_HERO = 5;
	private static final int MENUID_NAVIGATION = 6;
	private static final int MENUID_NOTICE = 8;
	private View mView;

	ListView mListView;
	MenuListAdapter mMenuListAdapter;

	TextView version;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mView = inflater.inflate(R.layout.menulist, null);

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
				case MENUID_CHANGEPASS:
					Intent intent = new Intent(getActivity(),
							PasswordSetting.class);
					intent.putExtra("name", "change");
					startActivity(intent);
					NavigationUtil.gotoNextFromLeft(getActivity());
					break;
				case MENUID_FEEDBACK:
					startActivity(new Intent(getActivity(), FeedBack.class));
					NavigationUtil.gotoNextFromLeft(getActivity());
					break;
				case MENUID_UPDATE:
					update();
					break;
				case MENUID_HELP:
					startActivity(new Intent(getActivity(), Help.class));
					NavigationUtil.gotoNextFromLeft(getActivity());
					break;
				case MENUID_EMAIL:
					Intent emailIntent=new Intent(getActivity(),EmailSetting.class);
					emailIntent.putExtra("name", "edit");
					startActivity(emailIntent);
					NavigationUtil.gotoNextFromLeft(getActivity());
					break;
					
				case MENUID_HERO:
					startActivity(new Intent(getActivity(), Hero.class));
					NavigationUtil.gotoNextFromLeft(getActivity());
					break;
				case MENUID_NAVIGATION:
					startActivity(new Intent(getActivity(), Navigation.class));
					NavigationUtil.gotoNextFromLeft(getActivity());
					break;
				case MENUID_NOTICE:
					startActivity(new Intent(getActivity(), Notice.class));
					NavigationUtil.gotoNextFromLeft(getActivity());
					break;
				default:
					break;
				}

			}

		});

		return mView;
	}

	private List<MenuBean> getDatas() {
		List<MenuBean> beans = new ArrayList<MenuBean>();
		beans.add(new MenuBean(MENUID_CHANGEPASS, R.drawable.more_password,
				getString(R.string.menu_changepass)));
		
		beans.add(new MenuBean(MENUID_EMAIL, R.drawable.more_email,
				getString(R.string.menu_email)));
		
		beans.add(new MenuBean(MENUID_HELP, R.drawable.more_help,
				getString(R.string.menu_help)));

		beans.add(new MenuBean(MENUID_HERO, R.drawable.more_cool,
				getString(R.string.menu_hero)));
		/** 版本不需要更新 */
		// beans.add(new MenuBean(MENUID_UPDATE, R.drawable.more_update,
		// getString(R.string.menu_update)));

		beans.add(new MenuBean(MENUID_FEEDBACK, R.drawable.more_advise,
				getString(R.string.menu_feedback)));

		beans.add(new MenuBean(MENUID_NAVIGATION, R.drawable.more_lookover,
				getString(R.string.menu_guard)));
		beans.add(new MenuBean(MENUID_NOTICE, R.drawable.notice,
				getString(R.string.menu_notice)));
		

		return beans;
	}

	/** 升级 */
	private void update() {
		UmengUpdateAgent.update(getActivity());
		UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
			@Override
			public void onUpdateReturned(int updateStatus,
					UpdateResponse updateInfo) {
				switch (updateStatus) {
				case 0: // has update
					UmengUpdateAgent
							.showUpdateDialog(getActivity(), updateInfo);
					break;
				case 1: // has no update
					Toast.makeText(getActivity(), R.string.update_no_update,
							Toast.LENGTH_SHORT).show();
					break;
				case 2: // none wifi
					Toast.makeText(getActivity(), R.string.update_only_wifi,
							Toast.LENGTH_SHORT).show();
					break;
				case 3: // time out
					Toast.makeText(getActivity(), R.string.update_timeout,
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		});
	}

}
