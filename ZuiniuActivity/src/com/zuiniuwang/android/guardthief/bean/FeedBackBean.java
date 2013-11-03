package com.zuiniuwang.android.guardthief.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.util.JsonUtil;
import com.zuiniuwang.android.guardthief.util.PreferenceUtil;
import com.zuiniuwang.android.guardthief.util.PreferenceUtil.Enum_ShootPreference;

/**
 * 意见反馈
 * 
 * @author guoruiliang
 * 
 */
public class FeedBackBean {

	public long time;
	public String content;
	public String userEmail;
	public String contact;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FeedBackBean other = (FeedBackBean) obj;

		return (time == other.time)
				&& (userEmail != null ? userEmail.equals(other.userEmail)
						: other.userEmail == null)
				&& (content != null ? content.equals(other.content)
						: other.content == null)
				&& (contact != null ? contact.equals(other.contact)
						: other.contact == null);
	}

	protected String toJson() {
		return JsonUtil.toJson(FeedBackBean.class, this);
	}

	protected static FeedBackBean fromJson(String json) {
		return JsonUtil.fromJson(FeedBackBean.class, json);
	}

	/**
	 * 保存反馈信息到preference
	 * 
	 * @param context
	 * @param feedbacks
	 */
	protected static void saveToJson(List<FeedBackBean> feedbacks) {
		String result = "";
		if (feedbacks != null && feedbacks.size() > 0) {
			JSONArray array = new JSONArray();
			for (FeedBackBean feedback : feedbacks) {
				array.put(feedback.toJson());
			}
			result = String.valueOf(array);
		}
		PreferenceUtil.getInstance(GuardApplication.appContext).setPreference(
				Enum_ShootPreference.FeedBack, result);

	}

	/**
	 * 从preference中获取反馈信息列表
	 * 
	 * @param context
	 * @return
	 */
	public static List<FeedBackBean> getFeedBacks() {

		List<FeedBackBean> feedBacks = new ArrayList<FeedBackBean>();
		String feedBackString = getFeedBackString();
		if (!TextUtils.isEmpty(feedBackString)) {
			feedBacks = getArrayfromJson(feedBackString);
		}

		return feedBacks;

	}

	public static String getFeedBackString() {
		String feedBackString = PreferenceUtil.getInstance(
				GuardApplication.appContext).getPreference(
				Enum_ShootPreference.FeedBack);
		return feedBackString;
	}

	/**
	 * 添加一个反馈
	 * 
	 * @param context
	 * @param feedBack
	 */
	public static void addFeedBack(FeedBackBean feedBack) {
		List<FeedBackBean> feedbacks = getFeedBacks();
		feedbacks.add(feedBack);
		saveToJson(feedbacks);
	}

	/**
	 * 清空反馈信息
	 */
	public static void clear() {
		saveToJson(null);
	}

	protected static List<FeedBackBean> getArrayfromJson(String json) {
		List<FeedBackBean> feedbacks = new ArrayList<FeedBackBean>();
		try {
			JSONArray array = new JSONArray(json);
			int count = array.length();
			for (int i = 0; i < count; i++) {
				feedbacks.add(fromJson(String.valueOf(array.get(i))));
			}
		} catch (Exception e) {
		}
		return feedbacks;
	}
}
