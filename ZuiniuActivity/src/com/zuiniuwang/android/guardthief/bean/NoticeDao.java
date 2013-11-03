package com.zuiniuwang.android.guardthief.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.zuiniuwang.android.guardthief.config.NoticeBean;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * 通知数据层
 * @author guoruiliang
 *
 */
public class NoticeDao  {

	private SharedPreferences preferences = null;
	private static final String PrefName = "notice";
	private Context mContext;
	SharedPreferences.Editor editor = null;

	public NoticeDao(Context context) {
		this.mContext=context;
		this.preferences = context.getSharedPreferences(PrefName,
				Context.MODE_PRIVATE);
		this.editor = preferences.edit();
	
	}
 
	public List<NoticeBean> getNoticesFromPref() {
		String json = preferences.getString(PrefName, "");
		List<NoticeBean> chats = new ArrayList<NoticeBean>();
		if (!TextUtils.isEmpty(json)) {  
			try {
				JSONArray array = new JSONArray(json);
				for (int i = array.length()-1; i >=0; i--) {
					NoticeBean msg = NoticeBean.fromJson(String
							.valueOf(array.get(i)));
					chats.add(msg);
				}
			} catch (Exception e) {
			}
		}

		return chats;
	}

	private void saveNoticesToPref(List<NoticeBean> chats) {
		String value = "";
		if (chats != null && chats.size() > 0) {
			JSONArray array = new JSONArray();
			for (NoticeBean chat : chats) {
				array.put(chat.toJson());
			}
			value = String.valueOf(array);
		}
		editor.putString(PrefName, String.valueOf(value));
		editor.commit();
	}


	public NoticeBean getLastNotice() {

		List<NoticeBean> results = getNoticesFromPref();
		if (results != null && results.size() > 0) {
			return results.get(results.size()-1);
		} else {
			return null;
		}

	}

	
	
	
	public void addNotice(NoticeBean chat) {
		List<NoticeBean> results = getNoticesFromPref();
		if (!results.contains(chat)) {
			results.add(chat);
		}

		saveNoticesToPref(results);
	}

	public void removeNotice(NoticeBean chat) {
		List<NoticeBean> values = getNoticesFromPref();
		values.remove(chat);
		saveNoticesToPref(values);

	}

	public void clear() {
		saveNoticesToPref(new ArrayList<NoticeBean>());
	}
	

}
