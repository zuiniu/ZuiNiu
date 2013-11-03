package com.zuiniuwang.android.guardthief.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.bean.HeroBean;
import com.zuiniuwang.android.guardthief.bean.MenuBean;

/**
 * 菜单列表adapter
 * 
 * @author guoruiliang
 * 
 */
public class MenuListAdapter extends BaseAdapter {

	private static final String TAG = MenuListAdapter.class.getSimpleName();

	private List<MenuBean> mMenuBeans = new ArrayList<MenuBean>();

	private Context mContext;

	private LayoutInflater mInflater;

	public MenuListAdapter(Context context) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
	}

	public void setDatas(List<MenuBean> beans) {
		this.mMenuBeans = beans;
	}

	public int getCount() {
		return mMenuBeans.size();
	}

	public Object getItem(int position) {
		return mMenuBeans.get(position);
	}

	public long getItemId(int position) {
		return position;
	}


	public View getView(int position, View convertView, ViewGroup parent) {

		MenuBean bean = mMenuBeans.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();

			convertView = mInflater.inflate(R.layout.menu_list_item, null);

			viewHolder.name = (TextView) convertView.findViewById(R.id.name);

			viewHolder.icon = (ImageView) convertView
					.findViewById(R.id.icon);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.name.setText(bean.name);
		viewHolder.icon.setImageResource(bean.imageResourceId);
		return convertView;
	}

	static class ViewHolder {
		public TextView name;
		public ImageView icon;
	}

}
