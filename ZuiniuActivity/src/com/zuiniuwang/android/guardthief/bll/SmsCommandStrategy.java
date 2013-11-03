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
 * 短信策略类
 * 
 * @author guoruiliang
 * 
 */
public enum SmsCommandStrategy {
	
	
	/** 默认拍照时机 */
	DEFAULT(0, "default", "默认") ,
	/**Setting*/
	STARTGUARD(1,"#zuiniu@自动监听开#","监听开"),
	
	STOPGUARD(2,"#zuiniu@自动监听关#","监听关"),
	STARTNETWORK(3,"#zuiniu@打开网络#","打开网络"),
	ZHUAPAI(5, "#zuiniu@抓拍一次#", "抓拍"),
	
	;

	/** 拍照类型 */
	private int id;

	/** 策略actionName,广播用 */
	private String actionName = "";
	/** 描述信息 */
	private String descName = "";
	

	SmsCommandStrategy(int id, String actionName, String desc) {
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
	public static SmsCommandStrategy getStrategyByActionName(String actionName) {
		for (SmsCommandStrategy strategy : SmsCommandStrategy.values()) {
			if (strategy.getActionName().equalsIgnoreCase(actionName)) {
				return strategy;
			}
		}
		return SmsCommandStrategy.DEFAULT;
	}

	/** 根据id获取相应策略 */
	public static String getStrategyDescById(int id) {
		for (SmsCommandStrategy strategy : SmsCommandStrategy.values()) {
			if (strategy.getid() == id) {
				return strategy.getDesc();
			}
		}
		return SmsCommandStrategy.DEFAULT.getDesc();
	}

	/**启动相机拍摄*/
	public   void sendSmsCommand(Context context,SmsCommandStrategy strategy){
		Intent intent=new Intent(actionName);
		intent.putExtra("name", strategy.getActionName());
		context.sendBroadcast(intent);
	}
	
}
