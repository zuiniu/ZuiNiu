package com.zuiniuwang.android.guardthief.bll;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.SystemClock;

import com.zuiniuwang.android.guardthief.GuardApplication;
import com.zuiniuwang.android.guardthief.GuardService;
import com.zuiniuwang.android.guardthief.R;
import com.zuiniuwang.android.guardthief.bean.PhotoBean;
import com.zuiniuwang.android.guardthief.camera.CameraCallBack;
import com.zuiniuwang.android.guardthief.camera.CameraManager;
import com.zuiniuwang.android.guardthief.dialog.TestDialogBuilder;

/**
 * 拍照策略类
 * 
 * @author guoruiliang
 * 
 */
public enum CameraStrategy {

	/** 默认 */
	DEFAULT(0, "default", "默认") {

		@Override
		public void openCamera(Context context, TimeStrategy timeStrategy) {

		}
	},
	/** 开启前置摄像头拍摄 */
	FrontWithFace(1, "FrontWithFace", "前置摄像头拍摄") {
		@Override
		public void openCamera(final Context context,
				final TimeStrategy timeStrategy) {

			CameraManager.startToGuard(context, true, new CameraCallBack() {

				public void executeBitmap(final Bitmap bitmap, boolean isface) {
					if (isface) {
						PhotoBean photoBean = new PhotoBean();
						photoBean.isFront = true;
						photoBean.isHasFace = isface;
						photoBean.cameraStragegyId = CameraStrategy.FrontWithFace
								.getid();
						photoBean.time = System.currentTimeMillis();
						photoBean.timeStrategyId = timeStrategy.getid();
						CameraBll.getPic(context, photoBean, bitmap);
					}
				}

			});
		}
	},
	/** 开启前置摄像头拍摄 */
	FrontNotWithFace(2, "FrontNotWithFace", "前置摄像头拍摄") {
		@Override
		public void openCamera(final Context context,
				final TimeStrategy timeStrategy) {

			CameraManager.startToGuard(context, true, new CameraCallBack() {

				public void executeBitmap(final Bitmap bitmap, boolean isface) {
					PhotoBean photoBean = new PhotoBean();
					photoBean.isFront = true;
					photoBean.isHasFace = isface;
					photoBean.cameraStragegyId = CameraStrategy.FrontNotWithFace
							.getid();
					photoBean.time = System.currentTimeMillis();
					photoBean.timeStrategyId = timeStrategy.getid();
					CameraBll.getPic(context, photoBean, bitmap);
					GuardApplication.excutorService.execute(new Runnable() {

						public void run() {
							SystemClock.sleep(3000);
							GuardService.guardService.sendFaceMail();

						}

					});
				}

			});
		}
	},

	/** 开启前置摄像头拍摄 */
	TestFront(3, "TestFront", "前置摄像头拍摄") {
		@Override
		public void openCamera(final Context context,
				final TimeStrategy timeStrategy) {

			CameraManager.startToGuard(context, true, new CameraCallBack() {

				public void executeBitmap(final Bitmap bitmap, boolean isface) {
					PhotoBean photoBean = new PhotoBean();
					photoBean.isFront = true;
					photoBean.isHasFace = isface;
					photoBean.cameraStragegyId = CameraStrategy.TestFront
							.getid();
					photoBean.time = System.currentTimeMillis();
					photoBean.timeStrategyId = timeStrategy.getid();

					TestDialogBuilder.sendBroad(context,
							context.getString(R.string.dialog_sendmail), 60);
					// UiUtil.showText("拍摄成功");
					CameraBll.getPic(context, photoBean, bitmap);

					GuardApplication.excutorService.execute(new Runnable() {

						public void run() {
							SystemClock.sleep(3000);

							if (GuardService.guardService.sendFaceMail()) {

								TestDialogBuilder.sendBroad(context, context
										.getString(R.string.dialog_success),
										100);

							} else {
								TestDialogBuilder.sendBroad(
										context,
										context.getString(R.string.dialog_mail_failed),
										101);

							}

						}

					});

				}

			});
		}
	},

	/** 后置摄像头拍摄 */
	BACKTest(5, "BACKTest", "后置摄像头拍摄") {
		@Override
		public void openCamera(final Context context,
				final TimeStrategy timeStrategy) {

			CameraManager.startToGuard(context, false, new CameraCallBack() {

				public void executeBitmap(final Bitmap bitmap, boolean isface) {
					PhotoBean photoBean = new PhotoBean();
					photoBean.isFront = false;
					photoBean.isHasFace = isface;
					photoBean.cameraStragegyId = CameraStrategy.BACKTest
							.getid();
					photoBean.time = System.currentTimeMillis();
					photoBean.timeStrategyId = timeStrategy.getid();
					CameraBll.getPic(context, photoBean, bitmap);
					TestDialogBuilder.sendBroad(context,
							context.getString(R.string.dialog_sendmail), 60);

					GuardApplication.excutorService.execute(new Runnable() {

						public void run() {
							SystemClock.sleep(3000);
							if (GuardService.guardService.sendFaceMail()) {

								TestDialogBuilder.sendBroad(context, context
										.getString(R.string.dialog_success),
										100);

							} else {
								TestDialogBuilder.sendBroad(
										context,
										context.getString(R.string.dialog_mail_failed),
										101);
							}

						}

					});

				}

			});
		}
	},

	;

	/** 拍照类型 */
	private int id;

	/** 策略actionName,广播用 */
	private String actionName = "";
	/** 描述信息 */
	private String descName = "";

	public abstract void openCamera(final Context context,
			TimeStrategy timeStrategy);

	CameraStrategy(int id, String actionName, String desc) {
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

	/** 根据action名称获取相应拍照策略 */
	public static CameraStrategy getStrategyByActionName(String actionName) {
		for (CameraStrategy strategy : CameraStrategy.values()) {
			if (strategy.getActionName().equalsIgnoreCase(actionName)) {
				return strategy;
			}
		}
		return CameraStrategy.DEFAULT;
	}

	/** 根据id获取相应拍照策略 */
	public static String getStrategyDescById(int id) {
		for (CameraStrategy strategy : CameraStrategy.values()) {
			if (strategy.getid() == id) {
				return strategy.getDesc();
			}
		}
		return CameraStrategy.DEFAULT.getDesc();
	}

}
