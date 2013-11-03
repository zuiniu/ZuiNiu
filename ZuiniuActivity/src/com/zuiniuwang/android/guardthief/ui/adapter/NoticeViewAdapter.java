package com.zuiniuwang.android.guardthief.ui.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.config.NoticeBean;

/**
 * 聊天Adapter
 * 
 * @author guoruiliang
 * 
 */
public class NoticeViewAdapter extends BaseAdapter {

	private static final String TAG = NoticeViewAdapter.class.getSimpleName();

	private List<NoticeBean> mNotices = new ArrayList<NoticeBean>();

	private Context mContext;

	private LayoutInflater mInflater;

	public NoticeViewAdapter(Context context) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
	}

	public void setDatas(List<NoticeBean> notices) {
		this.mNotices = notices;
	}

	public int getCount() {
		return mNotices.size();
	}

	public Object getItem(int position) {
		return mNotices.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		final NoticeBean bean = mNotices.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();

			convertView = mInflater.inflate(R.layout.notice_list_item, null);

			viewHolder.title = (TextView) convertView.findViewById(R.id.title);

			viewHolder.content = (TextView) convertView.findViewById(R.id.content);

			viewHolder.time = (TextView) convertView
					.findViewById(R.id.time);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.title.setText(bean.title);
		viewHolder.content.setText(bean.content);
		
		String date = new SimpleDateFormat(
				"yyyy年MM月dd日  ").format(Long.parseLong(bean.time));
		viewHolder.time.setText(date);
		

		return convertView;
	}

	static class ViewHolder {
		public TextView title;
		public TextView content;
		public TextView time;
	}

}
