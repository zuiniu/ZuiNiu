package com.zuiniuwang.android.guardthief.baidu;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zuiniuwang.android.guardthief.Const;

public class BaiduLocation {
	public static String baiduUrl="\"http://api.map.baidu.com/marker?location=%f,%f&content=%s&output=html\"";
	
	/**位置刷新时间 10分钟*/
	public static  int freshTime = Const.LOCATION_REFRESH_GAP;

	public LocationClient mLocationClient = null;

	private Context mContext;
	public MyLocationListenner myListener = new MyLocationListenner();

	private BDLocation mBDLocation;

	public BaiduLocation(Context context) {
		mContext = context;
		mLocationClient = new LocationClient(context);
		mLocationClient.registerLocationListener(myListener);
		setLocationOption();
	}

	public BDLocation getBDLocation() {
		return mBDLocation;
	}

	public String getAddress() {
		if (mBDLocation != null) {
			return mBDLocation.getAddrStr();
		}
		return "";
	}

	public void start() {
		mLocationClient.start();
	}

	public void stop() {
		mLocationClient.stop();
	}

	public void refresh() {
		if (mLocationClient.isStarted()) {
			setLocationOption();
			mLocationClient.requestLocation();
		}

	}

	public void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(false);
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setPoiExtraInfo(true);
		option.setCoorType("bd09ll");

		option.setAddrType("all");
		option.setScanSpan(freshTime);// 扫描间隔
		option.setPriority(LocationClientOption.NetWorkFirst);

		option.setPoiNumber(10);
		option.disableCache(true);
		mLocationClient.setLocOption(option);
	}

	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			//必须获取到位置再更新
			if(!TextUtils.isEmpty(location.getAddrStr())){
				mBDLocation = location;
			}
		
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			}
			sb.append("\nsdk version : ");
			sb.append(mLocationClient.getVersion());
			sb.append("\nisCellChangeFlag : ");
			sb.append(location.isCellChangeFlag());
			logMsg(sb.toString());
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(poiLocation.getTime());
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			}
			if (poiLocation.hasPoi()) {
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			} else {
				sb.append("noPoi information");
			}
			logMsg(sb.toString());
		}
	}

	public void logMsg(String str) {
		// Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
	}

}
