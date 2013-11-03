package com.zuiniuwang.android.guardthief.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.bean.HelpBean;
import com.zuiniuwang.android.guardthief.dialog.PopForHelpDialog;
import com.zuiniuwang.android.guardthief.util.DensityUtil;

/**
 * 聊天Adapter
 * 
 * @author guoruiliang
 * 
 */
public class HelpViewAdapter extends BaseAdapter {

	private static final String TAG = HelpViewAdapter.class.getSimpleName();

	private List<HelpBean> mChats = new ArrayList<HelpBean>();

	private Context mContext;

	private LayoutInflater mInflater;

	int imageHeight, imageWidth;

	public HelpViewAdapter(Context context) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		imageHeight = DensityUtil.dip2px(mContext, 92);
		imageWidth = DensityUtil.dip2px(mContext, 64);
	}

	public void setDatas(List<HelpBean> chats) {
		this.mChats = chats;
	}

	public int getCount() {
		return mChats.size();
	}

	public Object getItem(int position) {
		return mChats.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		HelpBean bean = (HelpBean) getItem(position);
		return bean.isLeft ? 0 : 1;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		HelpBean bean = mChats.get(position);
		boolean isLeft = bean.isLeft;
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();

			if (isLeft) {
				convertView = mInflater.inflate(R.layout.help_msg_text_left,
						null);
			} else {
				convertView = mInflater.inflate(R.layout.help_msg_text_right,
						null);
			}

			viewHolder.text = (TextView) convertView
					.findViewById(R.id.chatcontent);
			viewHolder.isLeft = isLeft;
			viewHolder.userHead = (ImageView) convertView
					.findViewById(R.id.userhead);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.text.setText(bean.text);

		if (position == 5) {
			Drawable drawable = mContext.getResources().getDrawable(
					R.drawable.pop_auto_help);
			// / 这一步必须要做,否则不会显示.
			drawable.setBounds(0, 0, imageWidth, imageHeight);
			viewHolder.text.setCompoundDrawables(null, null, null, drawable);
			viewHolder.text.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					new PopForHelpDialog(mContext, R.drawable.pop_auto_help)
							.BulidDialog().show();

				}
			});

		} else if (position == 11) {
			Drawable drawable = mContext.getResources().getDrawable(
					R.drawable.pop_hide_help);
			// / 这一步必须要做,否则不会显示.
			drawable.setBounds(0, 0, imageWidth, imageHeight);
			viewHolder.text.setCompoundDrawables(null, null, null, drawable);
			viewHolder.text.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					new PopForHelpDialog(mContext, R.drawable.pop_hide_help)
							.BulidDialog().show();

				}
			});

		}

		else {
			viewHolder.text.setCompoundDrawables(null, null, null, null);
			viewHolder.text.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

				}
			});
		}

		if (viewHolder.isLeft) {
			viewHolder.userHead.setImageResource(R.drawable.help_xiaobai);
		} else {
			viewHolder.userHead.setImageResource(R.drawable.help_niudan);
		}

		return convertView;
	}

	static class ViewHolder {
		public TextView text;
		public ImageView userHead;
		public boolean isLeft = true;
	}

}
