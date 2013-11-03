package com.zuiniuwang.android.guardthief.config;

import com.zuiniuwang.android.guardthief.util.JsonUtil;

/**
 *通知
 * 
 * @author guoruiliang
 * 
 */
public class NoticeBean {

	private static final String TAG = NoticeBean.class.getSimpleName();
	public String id;	
	
	public String time;	
	
	/** 标题 */
	public String title;
	
	/** 描述 */
	public String content;

	public NoticeBean(){
		
	}
	
	
	public NoticeBean(String id,String title,String content,String time) {
		this.time = time;
		this.title=title;
		this.id = id;
		this.content=content;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
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
		NoticeBean other = (NoticeBean) obj;

		return(id != null ? id.equals(other.id) : other.id == null)
				&& (title != null ? title.equals(other.title) : other.title == null)
				&& (content != null ? content.equals(other.content) : other.content == null)
				;
	}

	@Override
	public String toString() {
		return JsonUtil.toString(NoticeBean.class, this);
	}

	public String toJson() {
		return JsonUtil.toJson(NoticeBean.class, this);
	}

	public static NoticeBean fromJson(String json) {
		return JsonUtil.fromJson(NoticeBean.class, json);
	}

}
