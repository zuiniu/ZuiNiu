package com.zuiniuwang.android.guardthief.bean;

import android.content.Context;

import com.zuiniuwang.android.guardthief.util.JsonUtil;

/**
 * 最牛英雄榜
 * 
 * @author guoruiliang
 * 
 */
public class HeroBean {

	private static final String TAG = HeroBean.class.getSimpleName();

	public int imageResourceId;	
	
	/** 内容 */
	public String text;
	
	/** 描述 */
	public String desc;

	/**链接 */
	public String link;
	
	public HeroBean(int imageResourceId,String text,String desc,String link) {
		this.text = text;
		this.desc=desc;
		this.imageResourceId = imageResourceId;
		this.link=link;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + imageResourceId;
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
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
		HeroBean other = (HeroBean) obj;

		return (imageResourceId == other.imageResourceId)
				&& (text != null ? text.equals(other.text) : other.text == null)
				&& (desc != null ? desc.equals(other.desc) : other.desc == null)
				;
	}

	@Override
	public String toString() {
		return JsonUtil.toString(HeroBean.class, this);
	}

	public String toJson() {
		return JsonUtil.toJson(HeroBean.class, this);
	}

	public static HeroBean fromJson(String json) {
		return JsonUtil.fromJson(HeroBean.class, json);
	}

}
