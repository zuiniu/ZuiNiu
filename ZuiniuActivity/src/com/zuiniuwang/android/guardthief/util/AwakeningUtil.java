package com.zuiniuwang.android.guardthief.util;

import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.ui.Validate;


/**
 * 唤醒工具类
 * <P>
 * 当activity onstop的时候启动判断，15秒后如果没有进入onstart则判定刚进入后台
 * <P>
 * 当onstart的时候调用是否处于后台状态，如果是则启动密码界面，否则设置isStartState变量为true
 * @author guoruiliang
 *
 */
public class AwakeningUtil {

	/** 是否跑到后台了 */
	public static boolean isAppGotoBack = false;

	/**是否进入onstop状态*/
	public static boolean isStartState = false;

	
	/**多少秒后判定进入后台*/
	public static final int WAKE_TIME=15000	;
	
	/**进入验证页面带的参数*/
	public static final String EXTRA_VALIDATE="extra_name";
	
	private static Runnable stopRunnable=new Runnable() {
		
		@Override
		public void run() {
			if (!isStartState) {
				isAppGotoBack = true;
			}
		}
	};
	
	/**
	 * 判断是否跑到后台
	 * <P>
	 * onstop的时候调用，N秒后判断是否resume，如果没有，则认为是跑到后台了
	 * */
	public static void gotoStopState() {
		isStartState = false;
		GuardApplication.appHandler.removeCallbacks(stopRunnable);//把前面的回收，防止重复
		GuardApplication.appHandler.postDelayed(stopRunnable, WAKE_TIME);
	}

	/**
	 * onstat的时候调用
	 */
	public static void gotoStartState() {
		
		// 处于后台，调用验证
		if (isAppGotoBack) {
			Intent intent = new Intent(GuardApplication.appContext, Validate.class);
			intent.putExtra(EXTRA_VALIDATE, "validate");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			GuardApplication.appContext.startActivity(intent);
		} else {
			//因为会先调用onstart,然后再调用上一级页面的onstop方法，所以这类需要停顿一下
			GuardApplication.appHandler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					isStartState = true;
				}
			}, 1000);
			
		}
	}
	
	
	/**
	 * 验证成功了
	 */
	public static void validateSuccess(){
		isAppGotoBack=false;//设置不处于后台
	}
}
