package com.zuiniuwang.android.guardthief.bll;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.zuiniuwang.android.guardthief.GuardService;
import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.camera.CameraCallBack;
import com.zuiniuwang.android.guardthief.camera.CameraManager;
import com.zuiniuwang.android.guardthief.util.UiUtil;

/**
 * 拍照时机策略类
 * 
 * @author guoruiliang
 * 
 */
public enum TimeStrategy {
	
	
	/** 默认拍照时机 */
	DEFAULT(0, "default", "默认") ,
	/**Setting*/
	TESTFORSETTING(1,"setting","设置页面测试"),
	
	/**短信命令*/
	TESTFORPHONE(2,"phone","短信命令拍摄"),
	
	/** 屏幕开启 */
	SCREENOPEN_DELAY_20(3, "SCREENOPEN_DELAY_20", "屏幕开启延迟20秒"),

	/** 开启前置摄像头拍摄 */
	SCREENON_15MIN(4, "SCREENON_15MIN", "屏幕开启15分钟时"),

	/** 打电话开启后置摄像头拍摄 */
	PHONE_BACK(5, "PHONE_BACK", "打电话时候时"),
	
	/** 打电话开启后置摄像头拍摄 */
	PHONE_RECORD(6, "PHONE_RECORD", "电话录音"),
	
	;

	/** 拍照类型 */
	private int id;

	/** 策略actionName,广播用 */
	private String actionName = "";
	/** 描述信息 */
	private String descName = "";
	

	public  void openCamera(final Context context,CameraStrategy cameraStrategy){
		cameraStrategy.openCamera(context,this);
	}

	TimeStrategy(int id, String actionName, String desc) {
		this.id = id;
		this.actionName = actionName;
		this.descName = desc;
	}

	public String getActionName() {
		return this.actionName;
	}

	public int getid() {
		return this.id;
	}

	public String getDesc() {
		return this.descName;
	}

	/** 根据action名称获取相应拍照时机策略 */
	public static TimeStrategy getStrategyByActionName(String actionName) {
		for (TimeStrategy strategy : TimeStrategy.values()) {
			if (strategy.getActionName().equalsIgnoreCase(actionName)) {
				return strategy;
			}
		}
		return TimeStrategy.DEFAULT;
	}

	/** 根据id获取相应策略 */
	public static String getStrategyDescById(int id) {
		for (TimeStrategy strategy : TimeStrategy.values()) {
			if (strategy.getid() == id) {
				return strategy.getDesc();
			}
		}
		return TimeStrategy.DEFAULT.getDesc();
	}

	/**启动相机拍摄*/
	public   void sendTimeToGuardCommand(Context context,CameraStrategy cameraStrategy){
		Intent intent=new Intent(actionName);
		intent.putExtra("name", cameraStrategy.getActionName());
		context.sendBroadcast(intent);
	}
	
}
