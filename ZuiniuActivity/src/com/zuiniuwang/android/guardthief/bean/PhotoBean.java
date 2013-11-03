package com.zuiniuwang.android.guardthief.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.text.TextUtils;
import android.util.Log;

import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.util.JsonUtil;
import com.zuiniuwang.android.guardthief.util.PreferenceUtil;
import com.zuiniuwang.android.guardthief.util.PreferenceUtil.Enum_ShootPreference;

/**
 * 拍到的照片实体类
 * 
 * @author guoruiliang
 * 
 */
public class PhotoBean {

	/** 图片地址 */
	public String fileUrl;

	/** 拍摄时间 */
	public long time;

	/** 拍摄地址 */
	public String address;

	/** 是否是前置摄像头拍摄 */
	public boolean isFront;

	/** 是否有人脸 */
	public boolean isHasFace;

	/** 拍照方式 */
	public int cameraStragegyId;

	/** 拍照时机 */
	public int timeStrategyId;

	public String toJson() {
		return JsonUtil.toJson(PhotoBean.class, this);
	}

	public static PhotoBean fromJson(String json) {
		return JsonUtil.fromJson(PhotoBean.class, json);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) time;
		result = prime * result + ((fileUrl == null) ? 0 : fileUrl.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + (isFront ? 1 : 0);
		result = prime * result + (isHasFace ? 1 : 0);
		result = prime * result + cameraStragegyId;
		result = prime * result + timeStrategyId;

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
		PhotoBean other = (PhotoBean) obj;
		return (time == other.time)
				&& (fileUrl != null ? fileUrl.equals(other.fileUrl)
						: other.fileUrl == null)
				&& (address != null ? address.equals(other.address)
						: other.address == null) && (isFront == other.isFront)
				&& (isHasFace == other.isHasFace)
				&& (cameraStragegyId == other.cameraStragegyId)
				&& (timeStrategyId == other.timeStrategyId);
	}

	@Override
	public String toString() {
		return JsonUtil.toString(PhotoBean.class, this);
	}

	/**
	 * 保存拍照信息到preference
	 * 
	 * @param context
	 * @param photos
	 */
	protected static void saveToJson(List<PhotoBean> photos) {
		String result = "";
		if (photos != null && photos.size() > 0) {
			JSONArray array = new JSONArray();
			for (PhotoBean photo : photos) {
				array.put(photo.toJson());
			}
			result = String.valueOf(array);
		}
		PreferenceUtil.getInstance(GuardApplication.appContext).setPreference(
				Enum_ShootPreference.PhotoBean, result);

	}

	/**
	 * 从preference中获取拍照信息列表
	 * 
	 * @param context
	 * @return
	 */
	public static List<PhotoBean> getPhotoBeans() {

		List<PhotoBean> photos = new ArrayList<PhotoBean>();
		String photoString = getPhotoString();
		if (!TextUtils.isEmpty(photoString)) {
			photos = getArrayfromJson(photoString);
		}

		return photos;

	}

	public static String getPhotoString() {
		String photoString = PreferenceUtil.getInstance(
				GuardApplication.appContext).getPreference(
				Enum_ShootPreference.PhotoBean);
		return photoString;
	}

	/**
	 * 添加一个拍照信息
	 * 
	 * @param context
	 * @param photo
	 */
	public static void addPhotoBean(PhotoBean photo) {
		List<PhotoBean> photos = getPhotoBeans();
		photos.add(photo);
		saveToJson(photos);
	}

	/**
	 * 清空拍照信息
	 */
	public static void clear() {
		saveToJson(null);
	}

	protected static List<PhotoBean> getArrayfromJson(String json) {
		List<PhotoBean> photos = new ArrayList<PhotoBean>();
		try {
			JSONArray array = new JSONArray(json);
			int count = array.length();
			for (int i = 0; i < count; i++) {
				photos.add(fromJson(String.valueOf(array.get(i))));
			}
		} catch (Exception e) {
		}
		return photos;
	}

	/**
	 * 检测存储的照片实体类照片是否正常
	 * <P>
	 * 不正常的话就删除，并保存到preference中免得下次还有问题
	 */
	public static List<PhotoBean> checkExist() {
		List<PhotoBean> beans = getPhotoBeans();
		List<PhotoBean> needDeleted = new ArrayList<PhotoBean>();
		for (PhotoBean bean : beans) {
			String url = bean.fileUrl;
			if (TextUtils.isEmpty(url)) {
				needDeleted.add(bean);
			} else {
				File file = new File(url);
				if (!file.exists()) {
					needDeleted.add(bean);
				}

			}
		}

		beans.removeAll(needDeleted);

		//如果有需要删除的，则保存
		if(needDeleted.size()>0){
			PhotoBean.saveToJson(beans);
		}
		
		return beans;
	}

}
