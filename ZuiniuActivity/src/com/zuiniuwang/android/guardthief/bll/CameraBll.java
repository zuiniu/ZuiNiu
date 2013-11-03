package com.zuiniuwang.android.guardthief.bll;

import com.baidu.location.BDLocation;
import com.zuiniuwang.android.guardthief.Const;
import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.baidu.BaiduLocation;
import com.zuiniuwang.android.guardthief.bean.PhotoBean;
import com.zuiniuwang.android.guardthief.receiver.ShootThread;
import com.zuiniuwang.android.guardthief.util.FileUtil;
import com.zuiniuwang.android.guardthief.util.LogUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

public class CameraBll {
	private static final String LOG_TAG = Const.LOG_TAG;
	public static String className = CameraBll.class.getSimpleName();

	public static void getPic(Context context, PhotoBean photoBean, Bitmap bitmap) {
		

		String url = FileUtil.saveBitMap(
				FileUtil.getPhotoPicName(""),
				bitmap);
		photoBean.fileUrl = url;
		// 如果保存地址有效
		if (!TextUtils.isEmpty(url)) {
			photoBean.address=getBDLocation();//获取位置信息
			PhotoBean.addPhotoBean(photoBean);// 保存下来
		}
		LogUtil.logI(className + " photoBean:" + photoBean);
	}

	public static String getBDLocation() {
		String url = "";
		BDLocation mBdLocation = GuardApplication.mBaiduLocation
				.getBDLocation();

		if (mBdLocation != null && !TextUtils.isEmpty(mBdLocation.getAddrStr())) {
			String baiduURi = String.format(BaiduLocation.baiduUrl,
					mBdLocation.getLatitude(), mBdLocation.getLongitude(),
					mBdLocation.getAddrStr());

			url = "<font color=red>位置信息:" + mBdLocation.getAddrStr()
					+ "<a href= " + baiduURi + ">  在百度地图中查看</a></font> <br>";

		}
		return url;

	}

}
