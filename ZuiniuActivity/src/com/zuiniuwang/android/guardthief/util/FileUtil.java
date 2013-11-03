package com.zuiniuwang.android.guardthief.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.zuiniuwang.android.guardthief.Const;

/**
 * 文件工具类
 * <P>
 * 保存，获取头牌的图片等相关操作
 * 
 * @author guoruiliang
 * 
 */
public class FileUtil {
	private static final String LOG_TAG = Const.LOG_TAG;

	/**
	 * 删除拍到的所有图片内容
	 */
	public static void clearGuardPic() {
		try {
			File[] files = getGuardPics();
			if (files != null) {
				for (File f : files) {
					f.delete();
				}
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "FileUtil clearGuardPic error");
		}

	}

	public static void clearRecords() {
		try {
			File[] files = getRecords();
			if (files != null) {
				for (File f : files) {
					f.delete();
				}
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "FileUtil clearRecords error");
		}

	}

	/**
	 * 获取拍到的图片
	 * 
	 * @return
	 */
	public static File[] getGuardPics() {
		File[] picFiles = null;
		try {
			File file = new File(Const.FACES_SAVED_PATH);
			picFiles = file.listFiles();
		} catch (Exception e) {
		}
		return picFiles;
	}

	/**
	 * 获取records
	 * 
	 * @return
	 */
	public static File[] getRecords() {
		File[] picFiles = null;
		try {
			File file = new File(Const.RECORDS_SAVED_PATH);
			picFiles = file.listFiles();
		} catch (Exception e) {
		}
		return picFiles;
	}

	/**
	 * 获取拍到的图片数目
	 * 
	 * @return
	 */
	public static int getGuardPicNumber() {
		File[] picFiles = getGuardPics();
		if (picFiles != null) {
			return picFiles.length;
		} else {
			return 0;
		}
	}
	
	/**
	 * 获取录音数目
	 * @return
	 */
	public static int getGuardRecordNumber() {
		File[] picFiles = getRecords();
		if (picFiles != null) {
			return picFiles.length;
		} else {
			return 0;
		}
	}
	
	

	/**
	 * 创建路径
	 */
	public static void mkDirs() {
//		Log.i(LOG_TAG, "FileUtil mkDirs");
		try {
			File file = new File(Const.FACES_SAVED_PATH);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(Const.RECORDS_SAVED_PATH);
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "FileUtil mkDirs error");
		}

	}

	/**
	 * 获取一个按时间排序的文件名
	 * 
	 * @return
	 */
	public static String getPhotoPicName(String suffix) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd_HH-mm-ss");

		return sdf.format(System.currentTimeMillis()) + suffix + ".jpg";
	}

	/**
	 * 获取日期文件名
	 * @return
	 */
	public static String getDateName() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd_HH-mm-ss");

		return sdf.format(System.currentTimeMillis());
	}

	/**
	 * 保存bitmap为jpeg格式到指定目录
	 * 
	 * @param fileName
	 * @param bitMap
	 */
	public static String saveBitMap(String fileName, Bitmap bitMap) {
		String result = "";
		Log.d(LOG_TAG, "FileUtil saveBitMap fileName:" + fileName);
		if (bitMap != null && !bitMap.isRecycled()) {
			mkDirs();//防止没有目录
			BufferedOutputStream bos = null;
			try {
				File file = new File(Const.FACES_SAVED_PATH, fileName);
				bos = new BufferedOutputStream(new FileOutputStream(file));
				bitMap.compress(Bitmap.CompressFormat.JPEG, 60, bos);
				bos.flush();
				bos.close();
				result = file.getAbsolutePath();
			} catch (Exception e) {
				Log.e(LOG_TAG, "FileUtil saveBitMap error:" + e.getMessage());
			} finally {
				if (bos != null) {
					try {
						bos.close();
						bos = null;
						bitMap.recycle();
					} catch (Exception e2) {
					}
				}
			}

		}
		return result;
	}

	/**
	 * 保存数据位jpeg图片，路径采用系统定义目录，文件名自定义
	 * 
	 * @param fileName
	 *            文件名
	 * @param data
	 *            数据，一般是bitmap的byte[]
	 */
	public static void saveDataToJpeg(String fileName, byte[] data) {
		try {
			Bitmap bitMap = BitmapFactory.decodeByteArray(data, 0, data.length);
			if (bitMap != null) {
				saveBitMap(fileName, bitMap);
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "FileUtil saveDataToJpeg error:" + e.getMessage());
		}

	}

}
