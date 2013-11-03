package com.zuiniuwang.android.guardthief.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.bean.HeroBean;

/**
 * 聊天Adapter
 * 
 * @author guoruiliang
 * 
 */
public class HeroViewAdapter extends BaseAdapter {

	private static final String TAG = HeroViewAdapter.class.getSimpleName();

	private List<HeroBean> mCools = new ArrayList<HeroBean>();

	private Context mContext;

	private LayoutInflater mInflater;

	public HeroViewAdapter(Context context) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
	}

	public void setDatas(List<HeroBean> cools) {
		this.mCools = cools;
	}

	public int getCount() {
		return mCools.size();
	}

	public Object getItem(int position) {
		return mCools.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		final HeroBean bean = mCools.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();

			convertView = mInflater.inflate(R.layout.hero_list_item, null);

			viewHolder.name = (TextView) convertView.findViewById(R.id.name);

			viewHolder.desc = (TextView) convertView.findViewById(R.id.desc);

			viewHolder.userHead = (ImageView) convertView
					.findViewById(R.id.userhead);
			viewHolder.point=(ImageView)convertView.findViewById(R.id.point);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.name.setText(bean.text);
		viewHolder.desc.setText(bean.desc);
		if (bean.imageResourceId > 0) {
			viewHolder.userHead.setImageResource(bean.imageResourceId);
		} else {
			viewHolder.userHead.setImageResource(R.drawable.help_chat_shadow);
		}

		if(TextUtils.isEmpty(bean.link)){
			viewHolder.point.setVisibility(View.GONE);
		}else{
			viewHolder.point.setVisibility(View.VISIBLE);
		}
		

		return convertView;
	}

	static class ViewHolder {
		public TextView name;
		public ImageView userHead;
		public TextView desc;
		public ImageView point;
	}

}
