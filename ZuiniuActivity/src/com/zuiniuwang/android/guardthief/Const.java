package com.zuiniuwang.android.guardthief;

/**
 * 系统常量
 * 
 * @author guoruiliang
 * 
 */
public class Const {
 
	// 从魅族开发者中心里获取到的属于该第三方应用的公钥
	public static final String APKPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmXRMfp9KpD7Wv3do8OT5m6vdIdDM6pr84m1SY/8RQCcP1dza9ImwbpsQRAig4gFR9D4kPGNYqLwKHma7pzw0TuginQYyZsjjhVYHhbBe9sazo+FAeR6HU4MpKA4onTPqXAlCGofB0suAYURq/VSUfYYv5DraU43v6j3CWLtkQhQIDAQAB";
	// 软件ID
	public static final String MSTORE_IDENTIFY = "9e31eee3bf4542cf8022d5402ecde775";

	/**是否检测魅族版本*/
	public static final boolean isCheckVersionForMeizu=false;
	  
	/**密碼*/
	public static final String rootPass = "123";  
	
	/**开启root,则权限吗可以设置*/
	public static final boolean isRoot=false;
	
	/** 当前权限 */
	public static final String ManageCode = "guorl";
	/**测试用户的权限ID，需要isManager=false*/
	public static final String TestVesionManagerCode="guorl"; 

	/** 日志标签 */
	public static final String LOG_TAG = "zuiniu";

	/** 反馈邮箱 */
	public static final String FeedBackMail = "zuiniuyihao9@163.com";
	public static final String BackMailPass = "";
	public static final String BackMailPassForQQ = "";
	
	/** 记录屏幕开启次数 */
	public static long ScreenUnlockTime = 0;

	/** 版本号 */
	public static final String VERSION = "v1.0.1beta";

	/** 图片保存路径 */
	public static final String FACES_SAVED_PATH = "mnt/sdcard/"
			+ "guardthief/image/";
	/** 录音保存路径 */
	public static final String RECORDS_SAVED_PATH = "mnt/sdcard/"
			+ "guardthief/record/";

	/** 更新配置间隔 */
	public static final long REFRESH_CONFIG_GAP = 2 * 60 * 60 * 1000;

	/** 位置刷新时间 */
	public static final int LOCATION_REFRESH_GAP = 30 * 60 * 1000;

	/** 自动监控关闭状态下位置刷新时间 */
	public static final int LOCATION_REFRESH_GAP_STOP = 4 * 60 * 60 * 1000;

	/** 开启屏幕多长时间后开启摄像头 */
	public static final long screenTimeToStartCamera = 20 * 1000;

	/** 打电话多长时间后开启摄像头 */
	public static final long PhoneTimeToStartBackCamera = 10 * 1000;

	/********* 配置名称 **********/
	public static final String ConfigName_Mail = "MailConfig";
	public static final String ConfigName_Basic = "BasicConfig";
	public static final String ConfigName_User = "UserConfig";
	public static final String ConfigName_Notice = "NoticeConfig";
	/** 开启屏幕时两次扫描的间隔时间，测试可以60秒，正式最好10分钟以上。屏幕关闭后无效 */
	public static final long GuardPeriod = 15 * 60 * 1000;
	/** 系统保存的最大的人脸拍摄数目，到达后就不会再拍了 */
	public static final long MaxPic = 50;

	/** 邮件间隔发送时间 */
	public static final long MailPeriod = 4 * 60 * 60 * 1000;
 
	/** 最大录音size 1.5M */
	public static final long MaxRecordSize = 1500 * 1000;

	/** 默认录音次数 */
	public static final long MaxRecordCount = 10;

	/** 固定时间检测一次邮件状态 */
	public static final long CheckMailTimePeriod = 1 * 60 * 60 * 1000;
	
	
	/**6小时检测一次版本是否是正式版*/
	public static final long CHECKVERSION_GAPTIME=6*60*60*1000;
	
}
