package com.zuiniuwang.android.guardthief.bean;

import com.zuiniuwang.android.guardthief.util.JsonUtil;

/**
 * 最牛英雄榜
 * 
 * @author guoruiliang
 * 
 */
public class MenuBean {

	private static final String TAG = MenuBean.class.getSimpleName();

	public int id;
	
	public int imageResourceId;	
	
	/** 内容 */
	public String name;
	

	public MenuBean(int id,int imageResourceId,String name) {
		this.id=id;
		this.name = name;
		this.imageResourceId = imageResourceId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + imageResourceId;
		result = prime * result + id;
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
		MenuBean other = (MenuBean) obj;

		return (imageResourceId == other.imageResourceId)
				&& (name != null ? name.equals(other.name) : other.name == null)
				&& (id==other.id)
				;
	}

	@Override
	public String toString() {
		return JsonUtil.toString(MenuBean.class, this);
	}

	public String toJson() {
		return JsonUtil.toJson(MenuBean.class, this);
	}

	public static MenuBean fromJson(String json) {
		return JsonUtil.fromJson(MenuBean.class, json);
	}

}
