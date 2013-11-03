package com.zuiniuwang.android.guardthief.bean;

import android.content.Context;

import com.zuiniuwang.android.guardthief.util.JsonUtil;

/**
 * 聊天Bean
 * 
 * @author guoruiliang
 * 
 */
public class HelpBean {

	private static final String TAG = HelpBean.class.getSimpleName();

	/** 内容 */
	public String text;
	/** 是否是来的消息 */
	public boolean isLeft = true;

	private HelpBean() {
	}

	public HelpBean(String text, boolean isLeft) {
		this.text = text;
		this.isLeft = isLeft;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + (isLeft ? 0 : 1);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HelpBean other = (HelpBean) obj;

		return (isLeft == other.isLeft)
				&& (text != null ? text.equals(other.text) : other.text == null);
	}

	@Override
	public String toString() {
		return JsonUtil.toString(HelpBean.class, this);
	}

	public String toJson() {
		return JsonUtil.toJson(HelpBean.class, this);
	}

	public static HelpBean fromJson(String json) {
		return JsonUtil.fromJson(HelpBean.class, json);
	}

}
